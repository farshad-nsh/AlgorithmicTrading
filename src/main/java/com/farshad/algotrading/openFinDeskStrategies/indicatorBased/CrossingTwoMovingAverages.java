package com.farshad.algotrading.openFinDeskStrategies.indicatorBased;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.CrossingTimeSeriesDetector;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.apache.log4j.Logger;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import java.io.IOException;
import java.util.concurrent.Callable;

public class CrossingTwoMovingAverages extends OpenFinDeskStrategy {


    final static Logger logger= Logger.getLogger(CrossingTwoMovingAverages.class);


    @Override
    public Callable<OpenFinDeskOrder> define() throws IOException {
        int index=series.getBarCount();

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        int big=14;
        EMAIndicator shortEma = new EMAIndicator(closePrice, 5);
        EMAIndicator longEma = new EMAIndicator(closePrice, big);

        for(int i=index-1;i>index-2;i--){
            logger.info("CrossingTwoMovingAverages:latest time :"+series.getBar(i).getEndTime()+" closePrice:"+series.getBar(i).getClosePrice());
            logger.info("symbol:"+series.getName()+",5-ticks-EMA value at the "+i+"th index: " + shortEma.getValue(i).doubleValue());
            logger.info("symbol:"+series.getName()+","+big+"-ticks-EMA value at the "+i+"th index: " + longEma.getValue(i).doubleValue());
        }
        int horizen=5;

        double[] shortEmaArray = new double[horizen];
        double[] longEmaArray = new double[horizen];
        int j=0;
        for(int i=index-horizen;i<index;i++){
            shortEmaArray[j]= shortEma.getValue(i).doubleValue();
            longEmaArray[j]=  longEma.getValue(i).doubleValue();
            j++;
        }

        CrossingTimeSeriesDetector crossingTimeSeriesDetector=new CrossingTimeSeriesDetector(shortEmaArray,longEmaArray);
        String status=crossingTimeSeriesDetector.detect(horizen);
        logger.info("status in CrossingTwoMovingAverages:"+status+" ,crossPoint="+crossingTimeSeriesDetector.getCrossPoint());


        start();

        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());
        openFinDeskOrder.setSymbol(series.getName());
        openFinDeskOrder.setStrategyName("CrossingTwoMovingAverages");
        openFinDeskOrder.setPosition("*");
        openFinDeskOrder.setVolume("0.01");
        openFinDeskOrder.setPrice(0.5*(series.getBar(index-1).getClosePrice().doubleValue()+
                series.getBar(index-1).getOpenPrice().doubleValue()));

        if ((status.equals("crossUp"))&&(crossingTimeSeriesDetector.getSlopeAtLatestIndex1()>0)){
            if(series.getBar(index-1).getClosePrice().doubleValue()>shortEma.getValue(index-1).doubleValue())
            {
                openFinDeskOrder.setPosition("buy");
                openFinDeskOrder.setOrderType("openBuy");
            }
        }else if ((status.equals("crossDown"))&&((crossingTimeSeriesDetector.getSlopeAtLatestIndex2()<0))){
            if(series.getBar(index-1).getClosePrice().doubleValue()<longEma.getValue(index-1).doubleValue())
            {
                 openFinDeskOrder.setPosition("sell");
                openFinDeskOrder.setOrderType("openSell");
            }
        }

        OpenFinDeskOrder finalOpenFinDeskOrder = openFinDeskOrder;
        return () -> {
            logger.debug("Observable thread: " + Thread.currentThread().getName());
            return openFinDeskOrder;
        };
    }


}
