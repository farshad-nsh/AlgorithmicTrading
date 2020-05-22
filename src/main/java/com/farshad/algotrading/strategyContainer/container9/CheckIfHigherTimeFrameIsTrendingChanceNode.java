/*
package com.farshad.algotrading.strategyContainer.container9;

import com.farshad.algotrading.containerCore.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskAnnotations.OpenFinDeskChanceNode;
import com.farshad.algotrading.openFinDeskStrategies.fibonacciBased.TrendStrengthStrategyBasedOnInternalRetracementSequences;
import org.apache.log4j.Logger;
import com.farshad.algotrading.core.containerCore.ChanceNode;

@OpenFinDeskChanceNode(containerId=9,nodeId=0
,openfindeskStrategies={TrendStrengthStrategyBasedOnInternalRetracementSequences.class, TrendStrengthStrategyBasedOnInternalRetracementSequences.class}
,timeFrames={"PERIOD_H1","PERIOD_D1"})
public class CheckIfHigherTimeFrameIsTrendingChanceNode extends ChanceNode  {
    final static Logger logger= Logger.getLogger(CheckIfHigherTimeFrameIsTrendingChanceNode.class);

    @Override
    public void executeCurrentChanceNode() {
        openFinDeskOrder=new OpenFinDeskOrder(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPrice(featureVector.get(0).getPrice());
        this.openFinDeskOrder.setOpenFinDeskOrderNumber(featureVector.get(0).getOpenFinDeskOrderNumber());
        logger.info("symbol="+featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setSymbol(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPosition("*");
        this.openFinDeskOrder.setOrderType("noOrder");
        this.openFinDeskOrder.setStrategyName("no signal from container 9");
        this.openFinDeskOrder.setAction("doNothingAndWait");
        this.openFinDeskOrder.setVolume("0.01");

        if ((featureVector.get(0).getPosition().equals("*"))||(featureVector.get(1).getPosition().equals("*"))){
           // logger.info("decision="+openFinDeskOrder.getAction());
            decisionIsMade=true;
        }
    }




}
*/
