package com.farshad.algotrading.openFinDeskAnnotations;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import com.farshad.algotrading.core.containerCore.StrategyContainer;
import com.farshad.algotrading.core.containerCore.ChanceNode;
import com.farshad.algotrading.core.containerCore.DecisionTree;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.ta4j.core.TimeSeries;

import java.io.IOException;
import java.util.*;

public class StrategyContainerHandler {


    final static Logger logger= Logger.getLogger(StrategyContainerHandler.class);

    private TimeSeries[] timeSeriesForAllTimeFrames;

    private int numberOfContainers;

    Map<Integer, StrategyContainer> containers;


    Map<Integer,OpenFinDeskOrder> openFinDeskOrders;

    List<OpenFinDeskOrder> orderList;

    Map<Integer,List<ChanceNode>> chanceNodesMap;

    int containerId = 0;
    int nodeId = 0;
    Map<Integer,Boolean> disabled=null;
    Map<Integer,Class[]> strategies = null;
    Map<Integer,String[]> timeFrames =null;

    public StrategyContainerHandler() {
        strategies=new HashMap<>();
        disabled=new HashMap<>();
        List<ChanceNode> cc=new ArrayList<>();

        chanceNodesMap=new HashMap<>();
        for(int i=0;i<30;i++){
            chanceNodesMap.put(i,cc);
        }

        containers=new HashMap<>();
        openFinDeskOrders=new HashMap<>();

        orderList=new ArrayList<>();
        timeFrames=new HashMap<>();

    }



    public void handle(){
        Reflections reflections = new Reflections("com.farshad.algotrading.strategyContainer", new TypeAnnotationsScanner());

        Set<Class<?>> classesAnnotatedWithOpenFinDeskChanceNode = reflections.getTypesAnnotatedWith(OpenFinDeskChanceNode.class, true);
        Object instance = null;

        for (Class<?> clazz : classesAnnotatedWithOpenFinDeskChanceNode) {
            logger.info("annotated clazz=" + clazz.getSimpleName());
            try {
                containerId = clazz.getAnnotation(OpenFinDeskChanceNode.class).containerId();
                logger.info("containerId=" + containerId);
                nodeId = clazz.getAnnotation(OpenFinDeskChanceNode.class).nodeId();
                disabled.put(containerId, clazz.getAnnotation(OpenFinDeskChanceNode.class).disabled());
                logger.info("container "+ containerId +"  disabled:"+disabled.get(containerId));
                logger.info("nodeId=" + nodeId);
                strategies.put(containerId, clazz.getAnnotation(OpenFinDeskChanceNode.class).openfindeskStrategies());
                           for(int i=0;i<strategies.get(containerId).length;i++){
                                 logger.info("strategies.get("+containerId+")["+i+"]="+strategies.get(containerId)[i] );
                           }


                timeFrames.put(containerId, clazz.getAnnotation(OpenFinDeskChanceNode.class).timeFrames());
                //logger.info(timeFrames[0] + " ," + timeFrames[1]);
                instance = Class.forName(clazz.getCanonicalName()).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

           // List<ChanceNode> chanceNodes = chanceNodesMap.get(containerId);
            List<ChanceNode> chanceNodes=new ArrayList<>();
            ChanceNode chanceNode=(ChanceNode) instance;
            chanceNode.nodeId=nodeId;
            chanceNodes.add(chanceNode);
            chanceNodesMap.put(containerId, chanceNodes);

        }


        for(int i=0;i<numberOfContainers;i++) {
                prepareContainer(i);
        }

        runAndMakeDecision();


    }

    private void prepareContainer(int containerId) {

        StrategyContainer strategyContainer = new StrategyContainer(new DecisionTree(chanceNodesMap.get(containerId)));
        strategyContainer.setDisabled(disabled.get(containerId));

        try {
            for(int j=0;j<strategies.get(containerId).length;j++){
                strategyContainer.addOpenFinDeskStrategy((OpenFinDeskStrategy) Class.forName(strategies.get(containerId)[j].getCanonicalName()).newInstance());
                if(timeFrames.get(containerId)[j].equals("PERIOD_H1")){
                    strategyContainer.getOpenFinDeskStrategyList().get(j).setSeries(timeSeriesForAllTimeFrames[0]);
                }else if(timeFrames.get(containerId)[j].equals("PERIOD_D1")){
                    strategyContainer.getOpenFinDeskStrategyList().get(j).setSeries(timeSeriesForAllTimeFrames[1]);
                }
                logger.info("containerId="+containerId+" ,adding strategy:"+strategyContainer.getOpenFinDeskStrategyList().get(j));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        containers.put(containerId,strategyContainer);
    }


    private void runAndMakeDecision(){


        for(int i=0;i<numberOfContainers;i++) {

            try {
                if(!containers.get(i).isDisabled()) {
                    logger.info("running " + i + "th container");
                    containers.get(i).run();
                }
                containers.get(i).makeDecision();
                openFinDeskOrders.put(i,containers.get(i).getFinalOrder());


            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }




    }


    public void setTimeSeriesForAllTimeFrames(TimeSeries[] timeSeriesForAllTimeFrames) {
        this.timeSeriesForAllTimeFrames = timeSeriesForAllTimeFrames;
    }

    public Map<Integer, StrategyContainer> getContainers() {
        return containers;
    }

    public List<OpenFinDeskOrder> getOpenFinDeskOrders() {

        List<OpenFinDeskOrder> openFinDeskOrderList=new ArrayList<>();
        for(int i=0;i<numberOfContainers;i++){
            openFinDeskOrderList.add(openFinDeskOrders.get(i));
        }
        return openFinDeskOrderList;
    }


    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }
}
