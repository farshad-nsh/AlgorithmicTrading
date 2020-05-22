package com.farshad.algotrading.core.containerCore;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;

import java.util.ArrayList;
import java.util.List;

abstract public class ChanceNode{

    public int nodeId;

    public ChanceNode chanceNode;

    public OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder("newSymbol");

    public List<OpenFinDeskOrder> featureVector=new ArrayList<>();

     public boolean decisionIsMade;

    abstract public void executeCurrentChanceNode();

    public ChanceNode() {
        decisionIsMade=false;
    }

    public ChanceNode getChanceNode() {
        return chanceNode;
    }

    public OpenFinDeskOrder getOpenFinDeskOrder() {
        return openFinDeskOrder;
    }

    public void setOpenFinDeskOrder(OpenFinDeskOrder openFinDeskOrder) {
        this.openFinDeskOrder = openFinDeskOrder;
    }

    public void setFeatureVector(List<OpenFinDeskOrder> featureVector) {
        this.featureVector = featureVector;
    }
}
