package com.farshad.algotrading.customizedPivotPointsMethods;

import com.farshad.algotrading.core.customizedPivotPointMethods.CamarillaPivotPointApproach;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("testing  Camarilla Pivot Point")
public class CamarillaPivotPointTest {
    final static Logger logger= Logger.getLogger(CamarillaPivotPointTest.class);
    TimeSeries timeSeries1;

    @BeforeEach
    void init() {
        timeSeries1=new BaseTimeSeries.SeriesBuilder().withName("EURUSD").build();
         ZonedDateTime endTime = ZonedDateTime.now();
        timeSeries1.addBar(endTime, 105.42, 112.99, 104.01, 111.42, 1337);
        timeSeries1.addBar(endTime.plusDays(1), 111.43, 112.83, 107.77, 187.99, 1234);
        timeSeries1.addBar(endTime.plusDays(2), 1.0790, 1.50, 1.90, 1.42, 200);
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
        timeSeries1.addBar(endTime.plusDays(24), 1.90, 1.50345, 1.90, 1.42, 642);
        timeSeries1.addBar(endTime.plusDays(25), 1.95, 1.53, 1.98, 1.46, 345);
    }

    @DisplayName("find support and resistance and pivot point")
    @Test
    void findPivotPoints() {
      CamarillaPivotPointApproach camarillaPivotPointApproach=new CamarillaPivotPointApproach(timeSeries1);
        double pp=camarillaPivotPointApproach.getPp();
        double r0=camarillaPivotPointApproach.getR()[0];
        double s0=camarillaPivotPointApproach.getS()[0];

        assertAll("pivot points",
                () -> assertEquals(1.6078166666666664, pp),
                () -> assertEquals(0.990417385, r0),
                () -> assertEquals(1.8495826149999999,s0)
        );
    }




}
