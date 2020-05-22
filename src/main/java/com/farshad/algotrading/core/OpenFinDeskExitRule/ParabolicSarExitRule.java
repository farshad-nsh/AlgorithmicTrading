package com.farshad.algotrading.core.OpenFinDeskExitRule;

import org.ta4j.core.indicators.ParabolicSarIndicator;

import static org.apache.commons.math3.util.Precision.round;

public class ParabolicSarExitRule extends OpenFinDeskExitRule {

    @Override
    public void applyExitRule() {
        int lastIndex=series.getBarCount()-1;
         ParabolicSarIndicator parabolicSarIndicator=new ParabolicSarIndicator(series);
        getOpenFinDeskOrder().setPrice(series.getBar(lastIndex).getClosePrice().doubleValue());
        getOpenFinDeskOrder().setStopLoss(parabolicSarIndicator.getValue(series.getBarCount()-1).doubleValue());
        if(getOpenFinDeskOrder().getOrderType().equals("openBuy")){
            getOpenFinDeskOrder().setTakeProfit(round(series.getBar(lastIndex).getClosePrice().doubleValue()+0.0015,5));
         }else if(getOpenFinDeskOrder().getOrderType().equals("openSell")){
             getOpenFinDeskOrder().setTakeProfit(round(series.getBar(lastIndex).getClosePrice().doubleValue()-0.0015,5));
        }

    }

}
