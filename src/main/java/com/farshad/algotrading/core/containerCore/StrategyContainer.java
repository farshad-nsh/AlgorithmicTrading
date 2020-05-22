package com.farshad.algotrading.core.containerCore;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.apache.log4j.Logger;
import rx.Observable;
import rx.functions.Action1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author farshad noravesh
 * @since version 1.0.0
 */
 public class StrategyContainer {

    final static Logger logger= Logger.getLogger(StrategyContainer.class);

    private  boolean disabled;

    //parentNode
    private DecisionTree decisionTree;

    public List<OpenFinDeskStrategy> openFinDeskStrategyList=new ArrayList<>();

    public final List<OpenFinDeskOrder> orderList=new ArrayList<>();

    public OpenFinDeskOrder finalOrder;


    public void addOpenFinDeskStrategy(OpenFinDeskStrategy openFinDeskStrategy) {
        openFinDeskStrategyList.add(openFinDeskStrategy);
    }

    public StrategyContainer(DecisionTree decisionTree) {
        this.decisionTree=decisionTree;
    }

    public void run() throws IOException, InterruptedException {
       //if(!disabled) {
           for (OpenFinDeskStrategy s : openFinDeskStrategyList
           ) {
               Observable.fromCallable(s.define())
                       // .subscribeOn(Schedulers.newThread())
                       .subscribe(printResult());
           }
           Thread.sleep(200);
      // }
    }

    public  OpenFinDeskOrder makeDecision(){
        if(!disabled) {
            decisionTree.setFeatureVector(orderList);
            finalOrder = decisionTree.makeDecision();
        } else{
            finalOrder=new OpenFinDeskOrder("disabled");
            finalOrder.setAction("disabled");
            finalOrder.setPosition("disabled");
            finalOrder.setStrategyName("strategy is disabled");
        }

         return finalOrder;
    }

    private Action1<OpenFinDeskOrder> printResult() {
        return order -> {
            //logger.info("Subscriber thread: " + Thread.currentThread().getName());
            logger.info("new suggested Order:");
            logger.info("openFinDeskOrder.getPosition()="+order.getPosition());
            logger.info("openFinDeskOrder.getSymbol()="+order.getSymbol());
            logger.info("openFinDeskOrder.getStrategyName()="+order.getStrategyName());
            //order.setOpenFinDeskOrderNumber(getRandomNumberInRange(0,10000000));
            order.setOpenFinDeskOrderNumber(1111111);
            orderList.add(order);
        };
    }

    private  int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public List<OpenFinDeskOrder> getOrderList() {
        orderList.stream().forEach(order-> logger.info("suggestion="+order.getPosition()+"strategyName="+order.getStrategyName()));
        return orderList;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public List<OpenFinDeskStrategy> getOpenFinDeskStrategyList() {
        return openFinDeskStrategyList;
    }

    public OpenFinDeskOrder getFinalOrder() {
        return finalOrder;
    }


    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
