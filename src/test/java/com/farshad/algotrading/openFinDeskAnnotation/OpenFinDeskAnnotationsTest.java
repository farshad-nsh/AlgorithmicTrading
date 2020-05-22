package com.farshad.algotrading.openFinDeskAnnotation;


import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskAnnotations.OpenFinDeskChanceNode;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import com.farshad.algotrading.core.containerCore.StrategyContainer;
import com.farshad.algotrading.core.containerCore.ChanceNode;
import com.farshad.algotrading.core.containerCore.DecisionTree;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("testing all annotations")
public class OpenFinDeskAnnotationsTest {

    final static Logger logger= Logger.getLogger(OpenFinDeskAnnotationsTest.class);
    Map<Integer,StrategyContainer> containers;

    DecisionTree decisionTree;
    OpenFinDeskOrder openFinDeskOrder;
    List<OpenFinDeskOrder> orderList;
    TimeSeries timeSeries1;
    TimeSeries timeSeries2;
    Map<Integer,List<ChanceNode>> chanceNodesMap;

    @BeforeEach
    void init() {
        List<ChanceNode> cc=new ArrayList<>();


        chanceNodesMap=new HashMap<>();
        for(int i=0;i<30;i++){
            chanceNodesMap.put(i,cc);
        }

        containers=new HashMap<>();
        openFinDeskOrder=new OpenFinDeskOrder("EURUSD");
        orderList=new ArrayList<>();
        timeSeries1=new BaseTimeSeries.SeriesBuilder().withName("EURUSD").build();
        timeSeries2=new BaseTimeSeries.SeriesBuilder().withName("EURUSD").build();
        ZonedDateTime endTime = ZonedDateTime.now();
        timeSeries1.addBar(endTime, 105.42, 112.99, 104.01, 111.42, 1337);
        timeSeries1.addBar(endTime.plusDays(1), 111.43, 112.83, 107.77, 187.99, 1234);
        timeSeries1.addBar(endTime.plusDays(2), 107.90, 117.50, 107.90, 175.42, 4242);
        timeSeries1.addBar(endTime.plusDays(3), 147.90, 117.50, 107.90, 145.42, 4242);
        timeSeries1.addBar(endTime.plusDays(4), 157.90, 117.50, 107.90, 145.42, 4242);
        timeSeries1.addBar(endTime.plusDays(5), 167.90, 117.50, 107.90, 195.42, 4242);
        timeSeries1.addBar(endTime.plusDays(6), 177.90, 117.50, 107.90, 185.42, 4242);
        timeSeries1.addBar(endTime.plusDays(7), 107.90, 117.50, 107.90, 175.42, 4242);
        timeSeries1.addBar(endTime.plusDays(8), 107.90, 117.50, 107.90, 165.42, 4242);
        timeSeries1.addBar(endTime.plusDays(9), 107.90, 117.50, 107.90, 155.42, 4242);
        timeSeries1.addBar(endTime.plusDays(10), 107.90, 117.50, 107.90, 145.42, 4242);
        timeSeries1.addBar(endTime.plusDays(11), 107.90, 117.50, 107.90, 135.42, 4242);
        timeSeries1.addBar(endTime.plusDays(12), 107.90, 117.50, 107.90, 125.42, 4242);
        timeSeries1.addBar(endTime.plusDays(13), 107.90, 117.50, 107.90, 105.42, 4242);
        timeSeries1.addBar(endTime.plusDays(14), 107.90, 117.50, 107.90, 125.42, 4242);
        timeSeries1.addBar(endTime.plusDays(15), 107.90, 117.50, 107.90, 138.42, 4242);
        timeSeries1.addBar(endTime.plusDays(16), 107.90, 117.50, 107.90, 136.42, 4242);
        timeSeries1.addBar(endTime.plusDays(17), 107.90, 117.50, 107.90, 133.42, 4242);
        timeSeries1.addBar(endTime.plusDays(18), 107.90, 117.50, 107.90, 132.42, 4242);
        timeSeries1.addBar(endTime.plusDays(19), 107.90, 117.50, 107.90, 138.42, 4242);
        timeSeries1.addBar(endTime.plusDays(20), 107.90, 117.50, 107.90, 137.42, 4242);
        timeSeries1.addBar(endTime.plusDays(21), 107.90, 117.50, 107.90, 136.42, 4242);
        timeSeries1.addBar(endTime.plusDays(22), 107.90, 117.50, 107.90, 135.42, 4242);
        timeSeries1.addBar(endTime.plusDays(23), 107.90, 117.50, 107.90, 134.42, 4242);
        timeSeries1.addBar(endTime.plusDays(24), 107.90, 117.50, 107.90, 133.42, 4242);
        timeSeries1.addBar(endTime.plusDays(25), 107.90, 117.50, 107.90, 132.42, 4242);


        timeSeries2.addBar(endTime, 105.42, 112.99, 104.01, 111.42, 1337);
        timeSeries2.addBar(endTime.plusDays(1), 111.43, 112.83, 107.77, 187.99, 1234);
        timeSeries2.addBar(endTime.plusDays(2), 107.90, 117.50, 107.90, 175.42, 4242);
        timeSeries2.addBar(endTime.plusDays(3), 147.90, 117.50, 107.90, 145.42, 4242);
        timeSeries2.addBar(endTime.plusDays(4), 157.90, 117.50, 107.90, 145.42, 4242);
        timeSeries2.addBar(endTime.plusDays(5), 167.90, 117.50, 107.90, 195.42, 4242);
        timeSeries2.addBar(endTime.plusDays(6), 177.90, 117.50, 107.90, 185.42, 4242);
        timeSeries2.addBar(endTime.plusDays(7), 107.90, 117.50, 107.90, 175.42, 4242);
        timeSeries2.addBar(endTime.plusDays(8), 107.90, 117.50, 107.90, 165.42, 4242);
        timeSeries2.addBar(endTime.plusDays(9), 107.90, 117.50, 107.90, 155.42, 4242);
        timeSeries2.addBar(endTime.plusDays(10), 107.90, 117.50, 107.90, 145.42, 4242);
        timeSeries2.addBar(endTime.plusDays(11), 107.90, 117.50, 107.90, 135.42, 4242);
        timeSeries2.addBar(endTime.plusDays(12), 107.90, 117.50, 107.90, 125.42, 4242);
        timeSeries2.addBar(endTime.plusDays(13), 107.90, 117.50, 107.90, 105.42, 4242);
        timeSeries2.addBar(endTime.plusDays(14), 107.90, 117.50, 107.90, 125.42, 4242);
        timeSeries2.addBar(endTime.plusDays(15), 107.90, 117.50, 107.90, 135.42, 4242);
        timeSeries2.addBar(endTime.plusDays(16), 107.90, 117.50, 107.90, 136.42, 4242);
        timeSeries2.addBar(endTime.plusDays(17), 107.90, 117.50, 107.90, 133.42, 4242);
        timeSeries2.addBar(endTime.plusDays(18), 107.90, 117.50, 107.90, 132.42, 4242);
        timeSeries2.addBar(endTime.plusDays(19), 107.90, 117.50, 107.90, 138.42, 4242);
        timeSeries2.addBar(endTime.plusDays(20), 107.90, 117.50, 107.90, 137.42, 4242);
        timeSeries2.addBar(endTime.plusDays(21), 107.90, 117.50, 107.90, 136.42, 4242);
        timeSeries2.addBar(endTime.plusDays(22), 107.90, 117.50, 107.90, 135.42, 4242);
        timeSeries2.addBar(endTime.plusDays(23), 107.90, 117.50, 107.90, 134.42, 4242);
        timeSeries2.addBar(endTime.plusDays(24), 107.90, 117.50, 107.90, 133.42, 4242);
        timeSeries2.addBar(endTime.plusDays(25), 107.90, 117.50, 107.90, 132.42, 4242);

        logger.info("@BeforeEach - executes before each test method in this class");
    }


    @DisplayName("find annotations and run containers")
    @Test
    void findAnnotations() {

        Reflections reflections = new Reflections("com.farshad.algotrading.strategyContainer", new TypeAnnotationsScanner());

        Set<Class<?>> classesAnnotatedWithOpenFinDeskChanceNode = reflections.getTypesAnnotatedWith(OpenFinDeskChanceNode.class, true);
        Object instance = null;
        int containerId = 0;
        int nodeId = 0;
        Class[] strategies = null;
         String[] timeFrames =null;
        for (Class<?> clazz : classesAnnotatedWithOpenFinDeskChanceNode) {
            logger.info("annotated clazz=" + clazz.getSimpleName());
            try {
                containerId = clazz.getAnnotation(OpenFinDeskChanceNode.class).containerId();
                logger.info("containerId=" + containerId);
                nodeId = clazz.getAnnotation(OpenFinDeskChanceNode.class).nodeId();
                logger.info("nodeId=" + nodeId);
                strategies = clazz.getAnnotation(OpenFinDeskChanceNode.class).openfindeskStrategies();
                logger.info(strategies[0] + " ," + strategies[1]);

                timeFrames = clazz.getAnnotation(OpenFinDeskChanceNode.class).timeFrames();
                logger.info(timeFrames[0] + " ," + timeFrames[1]);
                instance = Class.forName(clazz.getCanonicalName()).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            List<ChanceNode> chanceNodes = chanceNodesMap.get(containerId);
            ChanceNode chanceNode=(ChanceNode) instance;
            chanceNode.nodeId=nodeId;
            chanceNodes.add(chanceNode);
            chanceNodesMap.put(containerId, chanceNodes);
            decisionTree = new DecisionTree(chanceNodesMap.get(containerId));
            StrategyContainer strategyContainer = new StrategyContainer(decisionTree);
            try {
                for(int j=0;j<strategies.length;j++){
                    strategyContainer.addOpenFinDeskStrategy((OpenFinDeskStrategy) Class.forName(strategies[j].getCanonicalName()).newInstance());
                    if(timeFrames[j].equals("PERIOD_H1")){
                        strategyContainer.getOpenFinDeskStrategyList().get(j).setSeries(timeSeries1);
                    }else if(timeFrames[j].equals("PERIOD_D1")){
                        strategyContainer.getOpenFinDeskStrategyList().get(j).setSeries(timeSeries2);
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            containers.put(containerId,strategyContainer);
            try {
                containers.get(containerId).run();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            openFinDeskOrder = containers.get(containerId).makeDecision();
        }
/*
        assertAll("openFinDeskOrder",
                () -> assertEquals("*", openFinDeskOrder.getPosition()),
                () -> assertEquals("0.01", openFinDeskOrder.getVolume()),
                () -> assertEquals("noOrder", openFinDeskOrder.getOrderType())
        );
  */
     }
}
