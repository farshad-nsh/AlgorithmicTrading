package com.farshad.algotrading.openFinDeskStrategies.indicatorBased;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.ta4j.core.indicators.ParabolicSarIndicator;


import java.io.IOException;
import java.util.concurrent.Callable;

public class ParabolicSARIndicatorForStopLoss extends OpenFinDeskStrategy {




    @Override
    public Callable<OpenFinDeskOrder> define() throws IOException {
        int index=series.getBarCount();

         ParabolicSarIndicator parabolicSarIndicator=new ParabolicSarIndicator(series);
        System.out.println("parabolicSarIndicator.getValue(index-1)="+parabolicSarIndicator.getValue(index-1));

        start();

        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());
        System.out.println("symbol name inside ParabolicSARIndicatorForStopLoss:"+series.getName());
        openFinDeskOrder.setSymbol(series.getName());
        openFinDeskOrder.setStrategyName("ParabolicSARIndicatorForStopLoss");
        openFinDeskOrder.setPosition("*");
        openFinDeskOrder.setVolume("0.01");

        if(series.getBar(index-1).getClosePrice().doubleValue()>=parabolicSarIndicator.getValue(index-1).doubleValue()) {
                openFinDeskOrder.setPosition("buy");
                openFinDeskOrder.setOrderType("openBuy");
                openFinDeskOrder.setPrice(series.getBar(index-1).getClosePrice().doubleValue());
                openFinDeskOrder.setStopLoss(parabolicSarIndicator.getValue(index-1).doubleValue());
                openFinDeskOrder.setTakeProfit(series.getBar(index-1).getClosePrice().doubleValue() + 0.03);
        }else{
                 openFinDeskOrder.setPosition("sell");
                 openFinDeskOrder.setOrderType("openSell");
                 openFinDeskOrder.setPrice(series.getBar(index-1).getClosePrice().doubleValue());
                 openFinDeskOrder.setStopLoss(parabolicSarIndicator.getValue(index-1).doubleValue());
                 openFinDeskOrder.setTakeProfit(series.getBar(index-1).getClosePrice().doubleValue() - 0.03);
        }


        OpenFinDeskOrder finalOpenFinDeskOrder = openFinDeskOrder;
        return () -> {
            System.out.println("Observable thread: " + Thread.currentThread().getName());
            return openFinDeskOrder;
        };
    }

}
