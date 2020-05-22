package com.farshad.algotrading.core.containerCore;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import org.apache.log4j.Logger;

import java.util.*;

public class DecisionTree {
    final static Logger logger= Logger.getLogger(DecisionTree.class);

    private List<ChanceNode> chanceNodes;

    private List<OpenFinDeskOrder> featureVector=new ArrayList<>();


   boolean decisionIsMade;

    public DecisionTree(List<ChanceNode> chanceNodes) {
        this.chanceNodes = chanceNodes;
        sortChanceNodes();
    }

    private void sortChanceNodes() {
        List<ChanceNode> sortedChanceNodes=new ArrayList<>();
        for(int i=0;i<chanceNodes.size();i++){
            sortedChanceNodes.add(chanceNodes.get(i));
        }

        for(int i=0;i<chanceNodes.size();i++){
             sortedChanceNodes.set(chanceNodes.get(i).nodeId,chanceNodes.get(i));
        }
        chanceNodes=sortedChanceNodes;
    }



    public void setFeatureVector(List<OpenFinDeskOrder> featureVector) {
        this.featureVector=featureVector;
    }

    public OpenFinDeskOrder makeDecision(){
        decisionIsMade=false;
        int index=0;

            for(int i=0;i<chanceNodes.size();i++) {
                chanceNodes.get(i).setFeatureVector(featureVector);
            }

            for(int i=0;i<chanceNodes.size();i++){
                chanceNodes.get(i).executeCurrentChanceNode();
                decisionIsMade=chanceNodes.get(i).decisionIsMade;
                index=i;
                if(decisionIsMade) break;
            }
        logger.info("decision is made at node:"+chanceNodes.get(index).nodeId+" size of features="+chanceNodes.get(index).featureVector.size());
        logger.info("final decision="+chanceNodes.get(index).getOpenFinDeskOrder().getAction());
        return  chanceNodes.get(index).getOpenFinDeskOrder();
    }
}
