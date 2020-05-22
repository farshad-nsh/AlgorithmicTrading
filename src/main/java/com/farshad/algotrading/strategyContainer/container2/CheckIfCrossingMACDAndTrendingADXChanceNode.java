package com.farshad.algotrading.strategyContainer.container2;

import com.farshad.algotrading.openFinDeskAnnotations.OpenFinDeskChanceNode;
import com.farshad.algotrading.openFinDeskStrategies.indicatorBased.ADXBasedTrendDetection;
import com.farshad.algotrading.openFinDeskStrategies.indicatorBased.MACDbasedCrossingStrategy;
import com.farshad.algotrading.core.containerCore.ChanceNode;
import org.apache.log4j.Logger;

@OpenFinDeskChanceNode(containerId=2,nodeId=0
        ,openfindeskStrategies={MACDbasedCrossingStrategy.class, ADXBasedTrendDetection.class}
        ,timeFrames={"PERIOD_H1","PERIOD_D1"})
public class CheckIfCrossingMACDAndTrendingADXChanceNode extends ChanceNode {
    final static Logger logger= Logger.getLogger(CheckIfCrossingMACDAndTrendingADXChanceNode.class);

    @Override
    public void executeCurrentChanceNode() {
        this.openFinDeskOrder.setSymbol(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPosition("*");
        this.openFinDeskOrder.setOrderType("noOrder");
        this.openFinDeskOrder.setStrategyName("no signal from container 2");
        this.openFinDeskOrder.setAction("doNothingAndWait");
        this.openFinDeskOrder.setVolume("0.01");
        logger.info("featureVector.get(0).getPosition()="+featureVector.get(0).getPosition());
        logger.info("featureVector.get(1).getPosition()="+featureVector.get(1).getPosition());

        if ((featureVector.get(0).getPosition().equals("buy")) && (featureVector.get(1).getPosition().equals("buy"))) {
            this.openFinDeskOrder.setOrderType("openBuy");
            this.openFinDeskOrder.setAction("buyUsingSecondQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("Container2");

        }else if ((featureVector.get(0).getPosition().equals("sell")) && (featureVector.get(1).getPosition().equals("sell"))) {
            this.openFinDeskOrder.setOrderType("openSell");
            this.openFinDeskOrder.setAction("sellUsingSecondQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("Container2");
        }else {
            this.openFinDeskOrder.setOrderType("noOrder");
            this.openFinDeskOrder.setAction("doNothingAndWait");
        }
        decisionIsMade=true;

    }
}
