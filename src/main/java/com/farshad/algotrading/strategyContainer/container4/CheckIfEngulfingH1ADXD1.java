package com.farshad.algotrading.strategyContainer.container4;


import com.farshad.algotrading.core.containerCore.ChanceNode;
import com.farshad.algotrading.openFinDeskAnnotations.OpenFinDeskChanceNode;
import com.farshad.algotrading.openFinDeskStrategies.candlestickDriven.EngulfingPatternStrategy;
import com.farshad.algotrading.openFinDeskStrategies.indicatorBased.ADXBasedTrendDetection;
import org.apache.log4j.Logger;

@OpenFinDeskChanceNode(containerId=4,nodeId=0
        ,openfindeskStrategies={EngulfingPatternStrategy.class, ADXBasedTrendDetection.class}
        ,timeFrames={"PERIOD_H1","PERIOD_D1"})
public class CheckIfEngulfingH1ADXD1 extends ChanceNode {
    final static Logger logger= Logger.getLogger(CheckIfEngulfingH1ADXD1.class);


    @Override
    public void executeCurrentChanceNode() {
        this.openFinDeskOrder.setSymbol(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPosition("*");
        this.openFinDeskOrder.setOrderType("noOrder");
        this.openFinDeskOrder.setStrategyName("no signal from container 4");
        this.openFinDeskOrder.setAction("doNothingAndWait");
        this.openFinDeskOrder.setVolume("0.01");
        logger.info("featureVector.get(0).getPosition()="+featureVector.get(0).getPosition());
        logger.info("featureVector.get(1).getPosition()="+featureVector.get(1).getPosition());

        if ((featureVector.get(0).getPosition().equals("bullishEngulfingCandleStick")) && (featureVector.get(1).getPosition().equals("buy"))) {
            this.openFinDeskOrder.setOrderType("openBuy");
            this.openFinDeskOrder.setAction("buyUsingSecondQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("Container4");
        }else if ((featureVector.get(0).getPosition().equals("bearishEngulfingCandleStick")) && (featureVector.get(1).getPosition().equals("sell"))) {
            this.openFinDeskOrder.setOrderType("openSell");
            this.openFinDeskOrder.setAction("sellUsingSecondQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("Container4");
        }else {
            this.openFinDeskOrder.setOrderType("noOrder");
            this.openFinDeskOrder.setAction("doNothingAndWait");
        }
        decisionIsMade=true;
    }
}
