package com.farshad.algotrading.openFinDeskStrategies.fibonacciBased;

import com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements.PriceTime;
import com.farshad.algotrading.core.AlternatingTrendSmoothing.ATS;
import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.core.fibonacciCore.FibonacciHelper;
import com.farshad.algotrading.core.trendRangeAndWave.Trend;
import com.farshad.algotrading.core.trendRangeAndWave.TrendGenerator;
import com.farshad.algotrading.core.trendRangeAndWave.Wave;
import com.farshad.algotrading.core.trendRangeAndWave.WaveGenerator;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TrendStrengthStrategyBasedOnInternalRetracementSequences extends OpenFinDeskStrategy {
    final static Logger logger= Logger.getLogger(TrendStrengthStrategyBasedOnInternalRetracementSequences.class);


    @Override
    public Callable<OpenFinDeskOrder> define() throws IOException {
        int index=series.getBarCount();

        List<PriceTime> priceTimeList=new ArrayList<>();

        for(int i=index-25;i<index;i++){
            priceTimeList.add(new PriceTime(series.getBar(i).getClosePrice().doubleValue(),series.getBar(i).getEndTime().toInstant()));
        }

        ATS ats=new ATS(3,priceTimeList);
        ats.findPriceChangePoints();

        List<PriceTime> priceTimeChangePointList=ats.getPriceChangeList();

        TrendGenerator trendGenerator=new TrendGenerator(priceTimeChangePointList);
        List<Trend> trendList=new ArrayList<>();
        if (priceTimeChangePointList.size()>1){
            trendList=trendGenerator.generate();
        }


        WaveGenerator waveGenerator=new WaveGenerator(trendList,trendGenerator.getStartingIndex());
        List<Wave> waveList=waveGenerator.generate();
        FibonacciHelper fibonacciHelper=new FibonacciHelper(waveList,trendList.get(trendList.size()-1));
        fibonacciHelper.startProcedure();


        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());
        openFinDeskOrder.setSymbol(series.getName());
        openFinDeskOrder.setStrategyName("TrendStrengthStrategyBasedOnInternalRetracementSequences");
        openFinDeskOrder.setVolume("0.01");
        openFinDeskOrder.setPrice(0.5*(series.getBar(index-1).getClosePrice().doubleValue()+series.getBar(index-1).getOpenPrice().doubleValue()));
        openFinDeskOrder.setParameter(String.valueOf(fibonacciHelper.getStrength()));
        if((fibonacciHelper.getFibonacci().getTypeOfGlobalRegime().equals("increasing"))){
             openFinDeskOrder.setPosition("buy");
        }else if((fibonacciHelper.getFibonacci().getTypeOfGlobalRegime().equals("decreasing"))){
             openFinDeskOrder.setPosition("sell");
        }else{
            openFinDeskOrder.setPosition("*");
        }


        OpenFinDeskOrder finalOpenFinDeskOrder = openFinDeskOrder;
        return () -> {
            logger.debug("Observable thread: " + Thread.currentThread().getName());
            return openFinDeskOrder;
        };
    }
}
