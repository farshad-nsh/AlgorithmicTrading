package com.farshad.algotrading.strategyContainer.container0;

import com.farshad.algotrading.openFinDeskAnnotations.OpenFinDeskChanceNode;
import com.farshad.algotrading.openFinDeskStrategies.fibonacciBased.TrendStrengthStrategyBasedOnInternalRetracementSequences;
import com.farshad.algotrading.openFinDeskStrategies.indicatorBased.ADXBasedTrendDetection;
import com.farshad.algotrading.core.containerCore.ChanceNode;
import org.apache.log4j.Logger;

@OpenFinDeskChanceNode(containerId=0,nodeId=0
,openfindeskStrategies={ADXBasedTrendDetection.class, TrendStrengthStrategyBasedOnInternalRetracementSequences.class}
,timeFrames={"PERIOD_H1","PERIOD_D1"})
public class CheckIfADXIsOkChanceNode extends ChanceNode {
    final static Logger logger= Logger.getLogger(CheckIfADXIsOkChanceNode.class);

    @Override
    public void executeCurrentChanceNode() {
        this.openFinDeskOrder.setSymbol(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPosition("*");
        this.openFinDeskOrder.setOrderType("noOrder");
        this.openFinDeskOrder.setStrategyName("no signal from container 0");
        this.openFinDeskOrder.setAction("doNothingAndWait");
        this.openFinDeskOrder.setVolume("0.01");
        logger.info("featureVector.get(0).getPosition()="+featureVector.get(0).getPosition());
        logger.info("featureVector.get(1).getPosition()="+featureVector.get(1).getPosition());

        if ((featureVector.get(0).getPosition().equals("buy")) && (featureVector.get(1).getPosition().equals("buy"))) {
            this.openFinDeskOrder.setOrderType("openBuy");
            this.openFinDeskOrder.setAction("buyUsingSecondQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("container0");
        }else if ((featureVector.get(0).getPosition().equals("sell")) && (featureVector.get(1).getPosition().equals("sell"))) {
            this.openFinDeskOrder.setOrderType("openSell");
            this.openFinDeskOrder.setAction("sellUsingSecondQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("container0");
        }else {
            this.openFinDeskOrder.setOrderType("noOrder");
            this.openFinDeskOrder.setAction("doNothingAndWait");
        }
        decisionIsMade=true;

    }
}
