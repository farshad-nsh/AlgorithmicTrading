/*
package com.farshad.algotrading.openFinDeskStrategies.fibonacciBased;

import com.farshad.algotrading.containerCore.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.containerCore.trendRangeAndWave.Trend;
 import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import com.farshad.algotrading.openFinDeskStrategies.fibonacciBased.com.farshad.algotrading.containerCore.FiboClusterManagerOld;

import java.util.List;
import java.util.concurrent.Callable;

public class FibonacciConvergence extends OpenFinDeskStrategy {



    @Override
    public Callable<OpenFinDeskOrder> define() {
        start();

        WaveExtractorOld waveExtractor=new WaveExtractorOld();
        List<Trend> waveList=waveExtractor.findWaves(series, WaveExtractorOld.Method.JOE_DINAPOLI);
        FibonacciOld fibonacci=new FibonacciOld();
        FiboClusterManagerOld fiboClusterManager=new FiboClusterManagerOld();

       waveList.forEach(w-> {
           fibonacci.setWave(w);
           fiboClusterManager.add(fibonacci);
       });
        fiboClusterManager.manage();

        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());

            openFinDeskOrder.setSymbol(series.getName());
            openFinDeskOrder.setStrategyName("FibonacciConvergence");
            openFinDeskOrder.setVolume("0.01");
            openFinDeskOrder.setPosition("buyLimit");
            openFinDeskOrder.setPrice(series.getBar(0).getClosePrice().doubleValue());
            openFinDeskOrder.setStopLoss(fiboClusterManager.getFibonacciList().get(0).getInternalRetracementList().get(1).getPrice());
            openFinDeskOrder.setTakeProfit(fiboClusterManager.getPriceReversalZoneList().get(0).getLowerPrice());


        OpenFinDeskOrder finalOpenFinDeskOrder = openFinDeskOrder;

        return () -> {
            System.out.println("Observable thread: " + Thread.currentThread().getName());
            return finalOpenFinDeskOrder;
        };

    }




}
*/
