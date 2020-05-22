package com.farshad.algotrading.openFinDeskStrategies.indicatorBased;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.CrossingTimeSeriesDetector;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.io.IOException;
import java.util.concurrent.Callable;

public class MACDbasedCrossingStrategy extends OpenFinDeskStrategy {




    @Override
    public Callable<OpenFinDeskOrder> define() throws IOException {
        int index=series.getBarCount();

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        MACDIndicator macd = new MACDIndicator(closePrice, 9, 26);
        EMAIndicator emaMacd = new EMAIndicator(macd, 18);


        int horizen=5;

        double[] macdArray = new double[horizen];
        double[] emaMacdArray = new double[horizen];
        int j=0;
        for(int i=index-horizen;i<index;i++){
            macdArray[j]= macd.getValue(i).doubleValue();
            emaMacdArray[j]=  emaMacd.getValue(i).doubleValue();
            j++;
        }

        CrossingTimeSeriesDetector crossUpTimeSeriesDetector=new CrossingTimeSeriesDetector(macdArray,emaMacdArray);
        String crossUpStatus=crossUpTimeSeriesDetector.detect(horizen);
        System.out.println("crossUpDetector:status in MACDbasedCrossingStrategy:"+crossUpStatus+" ,crossPoint="+crossUpTimeSeriesDetector.getCrossPoint());


        start();

        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());
        openFinDeskOrder.setSymbol(series.getName());
        openFinDeskOrder.setStrategyName("MACDbasedCrossingStrategy");
        openFinDeskOrder.setPosition("*");
        openFinDeskOrder.setVolume("0.01");
        openFinDeskOrder.setPrice(0.5*(series.getBar(index-1).getClosePrice().doubleValue()+
                series.getBar(index-1).getOpenPrice().doubleValue()));

        if ((crossUpStatus.equals("crossUp"))&&(crossUpTimeSeriesDetector.getSlopeAtLatestIndex1()>0)){
                openFinDeskOrder.setPosition("buy");
                openFinDeskOrder.setOrderType("openBuy");
                openFinDeskOrder.setStopLoss(series.getBar(index-1).getClosePrice().doubleValue() - 0.01);
                openFinDeskOrder.setTakeProfit(series.getBar(index-1).getClosePrice().doubleValue() + 0.01);
        }else if ((crossUpStatus.equals("crossDown"))&&((crossUpTimeSeriesDetector.getSlopeAtLatestIndex2()<0))){
                openFinDeskOrder.setPosition("sell");
                openFinDeskOrder.setOrderType("openSell");
                openFinDeskOrder.setStopLoss(series.getBar(index-1).getClosePrice().doubleValue() + 0.01);
                openFinDeskOrder.setTakeProfit(series.getBar(index-1).getClosePrice().doubleValue() - 0.01);
        }

        OpenFinDeskOrder finalOpenFinDeskOrder = openFinDeskOrder;
        return () -> {
            System.out.println("Observable thread: " + Thread.currentThread().getName());
            return openFinDeskOrder;
        };
    }


}
