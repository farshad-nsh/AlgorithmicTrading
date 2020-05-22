package com.farshad.algotrading.core.OpenFinDeskExitRule;


import org.apache.log4j.Logger;

import static org.apache.commons.math3.util.Precision.round;

public class QuantizedTpAndSlExitRule extends OpenFinDeskExitRule {
    final static Logger logger= Logger.getLogger(QuantizedTpAndSlExitRule.class);

    private String quantizedExitRuleAction;

    @Override
    public void applyExitRule() {
        int lastIndex=series.getBarCount()-1;
        double price=0.5*(series.getBar(lastIndex).getClosePrice().doubleValue()+series.getBar(lastIndex).getOpenPrice().doubleValue());
        logger.info("symbol="+ getOpenFinDeskOrder().getSymbol());
        logger.info("price="+price);
        logger.info("orderType="+ getOpenFinDeskOrder().getOrderType());
        getOpenFinDeskOrder().setPrice(price);
        logger.info("strategy="+getOpenFinDeskOrder().getStrategyName());
        logger.info("action="+ getOpenFinDeskOrder().getAction());
        setQuantizedExitRuleAction(getOpenFinDeskOrder().getAction());
        logger.info("quantizedExitRuleAction="+quantizedExitRuleAction);



        switch(quantizedExitRuleAction) {

            case "buyUsingScalping":
                getOpenFinDeskOrder().setStopLoss(0.005);
                getOpenFinDeskOrder().setTakeProfit(round(0.0004,5));
                break;

            case "buyUsingFirstQuantizedLevel":
                getOpenFinDeskOrder().setStopLoss(0.005);
                getOpenFinDeskOrder().setTakeProfit(round(0.0007,5));
                break;
            case "buyUsingSecondQuantizedLevel":
                getOpenFinDeskOrder().setStopLoss(0.008);
                getOpenFinDeskOrder().setTakeProfit(round(0.0015,5));
                break;
            case "buyUsingThirdQuantizedLevel":
                getOpenFinDeskOrder().setStopLoss(0.01);
                getOpenFinDeskOrder().setTakeProfit(round(0.0025,5));
                break;
            case "sellUsingFirstQuantizedLevel":
                getOpenFinDeskOrder().setStopLoss(0.005);
                getOpenFinDeskOrder().setTakeProfit(round(0.0007,5));
                break;
            case "sellUsingSecondQuantizedLevel":
                getOpenFinDeskOrder().setStopLoss(0.008);
                getOpenFinDeskOrder().setTakeProfit(round(0.0015,5));
                break;
            case "sellUsingThirdQuantizedLevel":
                getOpenFinDeskOrder().setStopLoss(0.01);
                getOpenFinDeskOrder().setTakeProfit(round(0.0025,5));
                break;
            case "sellUsingScalping":
                getOpenFinDeskOrder().setStopLoss(0.005);
                getOpenFinDeskOrder().setTakeProfit(round(0.0004,5));
                break;
        }



    }



    private void setQuantizedExitRuleAction(String quantizedExitRuleAction) {
        this.quantizedExitRuleAction = quantizedExitRuleAction;
    }
}
