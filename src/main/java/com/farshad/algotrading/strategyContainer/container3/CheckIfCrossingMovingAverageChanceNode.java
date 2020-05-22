package com.farshad.algotrading.strategyContainer.container3;


import com.farshad.algotrading.openFinDeskAnnotations.OpenFinDeskChanceNode;
import com.farshad.algotrading.openFinDeskStrategies.indicatorBased.ADXBasedTrendDetection;
import com.farshad.algotrading.openFinDeskStrategies.indicatorBased.CrossingTwoMovingAverages;
import com.farshad.algotrading.core.containerCore.ChanceNode;
import org.apache.log4j.Logger;

@OpenFinDeskChanceNode(containerId=3,nodeId=0
        ,openfindeskStrategies={CrossingTwoMovingAverages.class,ADXBasedTrendDetection.class}
        ,timeFrames={"PERIOD_H1","PERIOD_D1"},disabled=true)
public class CheckIfCrossingMovingAverageChanceNode extends ChanceNode {
    final static Logger logger= Logger.getLogger(CheckIfCrossingMovingAverageChanceNode.class);

    @Override
    public void executeCurrentChanceNode() {
        this.openFinDeskOrder.setSymbol(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPosition("*");
        this.openFinDeskOrder.setOrderType("noOrder");
        this.openFinDeskOrder.setStrategyName("no signal from container 3");
        this.openFinDeskOrder.setAction("doNothingAndWait");
        this.openFinDeskOrder.setVolume("0.01");
        logger.info("featureVector.get(0).getPosition()="+featureVector.get(0).getPosition());
        if ((featureVector.get(0).getPosition().equals("buy")) && (featureVector.get(1).getPosition().equals("buy"))) {
            this.openFinDeskOrder.setOrderType("openBuy");
            this.openFinDeskOrder.setAction("buyUsingSecondQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("Container3");

        }else if ((featureVector.get(0).getPosition().equals("sell")) && (featureVector.get(1).getPosition().equals("sell"))) {
            this.openFinDeskOrder.setOrderType("openSell");
            this.openFinDeskOrder.setAction("sellUsingSecondQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("Container3");
        }else {
            this.openFinDeskOrder.setOrderType("noOrder");
            this.openFinDeskOrder.setAction("doNothingAndWait");
        }
        decisionIsMade=true;
    }
}
