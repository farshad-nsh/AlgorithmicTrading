/*
package com.farshad.algotrading.strategyContainer;

import com.farshad.algotrading.containerCore.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import com.farshad.algotrading.openFinDeskStrategies.candlestickDriven.PinBarStrategy;
import com.farshad.algotrading.openFinDeskStrategies.fibonacciBased.TrendStrengthStrategyBasedOnInternalRetracementSequences;
import com.farshad.algotrading.core.containerCore.ChanceNode;
import com.farshad.algotrading.core.containerCore.DecisionTree;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.ta4j.containerCore.BaseTimeSeries;
import org.ta4j.containerCore.TimeSeries;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("testing all containers")
public class StrategyContainerTest {

    final static Logger logger= Logger.getLogger(StrategyContainerTest.class);


    DecisionTree decisionTree;
    OpenFinDeskOrder openFinDeskOrder;
    List<OpenFinDeskOrder> orderList;
    TimeSeries timeSeries1;
    TimeSeries timeSeries2;

    @BeforeAll
    static void setup() {
        logger.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
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

    @DisplayName("testing make decision for container0 which is based on fibonacci trend detection")
    @Test
    void testMakeDecisionForContainer1() {
        //first container
        OpenFinDeskStrategy pinBarStrategyForH1=new PinBarStrategy();
        OpenFinDeskStrategy advancedTrendAnalysisForD1=new TrendStrengthStrategyBasedOnInternalRetracementSequences();

        pinBarStrategyForH1.setSeries(timeSeries1);
        advancedTrendAnalysisForD1.setSeries(timeSeries2);

        ChanceNode chanceNode1=new CheckIfHigherTimeFrameIsTrendingChanceNode();
        ChanceNode chanceNode2=new CheckIfBothTimeFramesAgreeChanceNode();
        List<ChanceNode> chanceNodes=new ArrayList<>();
        chanceNodes.add(chanceNode1);
        chanceNodes.add(chanceNode2);
        decisionTree=new DecisionTree(chanceNodes);
        StrategyContainer strategyContainer=new StrategyContainer(decisionTree);
        strategyContainer.addOpenFinDeskStrategy(pinBarStrategyForH1);
        strategyContainer.addOpenFinDeskStrategy(advancedTrendAnalysisForD1);

        try {
            strategyContainer.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        openFinDeskOrder=strategyContainer.makeDecision();
        //assertEquals(5 + 2, openFinDeskOrder.getPosition());
        assertAll("openFinDeskOrder",
                () -> assertEquals("*", openFinDeskOrder.getPosition()),
                () -> assertEquals("0.01", openFinDeskOrder.getVolume()),
                () -> assertEquals("noOrder", openFinDeskOrder.getOrderType())
        );

        logger.info("Success");
    }

    @Test
    @Disabled("Not implemented yet")
    void testShowSomething() {
    }
}
*/
