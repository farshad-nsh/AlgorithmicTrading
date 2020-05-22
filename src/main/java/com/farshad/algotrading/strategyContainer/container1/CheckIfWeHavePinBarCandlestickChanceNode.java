package com.farshad.algotrading.strategyContainer.container1;

import com.farshad.algotrading.openFinDeskAnnotations.OpenFinDeskChanceNode;
import com.farshad.algotrading.openFinDeskStrategies.candlestickDriven.PinBarStrategy;
import com.farshad.algotrading.openFinDeskStrategies.fibonacciBased.TrendStrengthStrategyBasedOnInternalRetracementSequences;
import org.apache.log4j.Logger;
import com.farshad.algotrading.core.containerCore.ChanceNode;

@OpenFinDeskChanceNode(containerId=1,nodeId=0
,openfindeskStrategies={PinBarStrategy.class,TrendStrengthStrategyBasedOnInternalRetracementSequences.class}
,timeFrames={"PERIOD_H1","PERIOD_D1"},disabled=true)
public class CheckIfWeHavePinBarCandlestickChanceNode extends ChanceNode {
    final static Logger logger= Logger.getLogger(CheckIfWeHavePinBarCandlestickChanceNode.class);

    @Override
    public void executeCurrentChanceNode() {
        this.openFinDeskOrder.setSymbol(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPosition("*");
        this.openFinDeskOrder.setOrderType("noOrder");
        this.openFinDeskOrder.setStrategyName("no signal from container 1");
        this.openFinDeskOrder.setAction("doNothingAndWait");
        this.openFinDeskOrder.setVolume("0.01");
        logger.info("featureVector.get(0).getPosition()="+featureVector.get(0).getPosition());
        logger.info("featureVector.get(1).getPosition()="+featureVector.get(1).getPosition());
        if ((featureVector.get(0).getPosition().equals("bearishPinBar"))&&(featureVector.get(1).getPosition().equals("sell"))){
            this.openFinDeskOrder.setOrderType("openSell");
            this.openFinDeskOrder.setAction("sellUsingScalping");
            this.openFinDeskOrder.setStrategyName("container1");
        }else if((featureVector.get(0).getPosition().equals("bullishPinBar"))&&(featureVector.get(1).getPosition().equals("buy"))){
            this.openFinDeskOrder.setOrderType("openBuy");
            this.openFinDeskOrder.setAction("buyUsingScalping");
            this.openFinDeskOrder.setStrategyName("container1");
        }
        decisionIsMade=true;

    }


}
