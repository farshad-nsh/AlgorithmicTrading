package com.farshad.algotrading.core.customizedPivotPointMethods;

import org.ta4j.core.TimeSeries;

import java.util.Arrays;

public class CamarillaPivotPointApproach {

    private TimeSeries timeSeries;
    private int numberOfBars;
    private double previousDayLowPrice;
    private double previousDayHighPrice;
    private double previousDayClosePrice;

    private double[] r;
    private double[] s;
    private double pp;

    public CamarillaPivotPointApproach(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
        calculatePivotPointsSuppertsAndResistance();
    }

    private void calculatePivotPointsSuppertsAndResistance() {
        initializeVariables();
        calculateParameters();
    }

    private void initializeVariables() {
        numberOfBars=timeSeries.getBarCount();

        r=new double[4];
        s=new double[4];
        Arrays.fill(r,0);
        Arrays.fill(s,0);
       previousDayClosePrice=timeSeries.getBar(numberOfBars-2).getClosePrice().doubleValue();
       previousDayLowPrice= timeSeries.getBar(numberOfBars-2).getMinPrice().doubleValue();
       previousDayHighPrice= timeSeries.getBar(numberOfBars-2).getMaxPrice().doubleValue();
    }

    private void calculateParameters() {
        pp=(previousDayHighPrice+previousDayLowPrice+previousDayClosePrice)/3;
        r[0]=previousDayClosePrice+((previousDayHighPrice-previousDayLowPrice)*(1.0833));
        r[1]=previousDayClosePrice+((previousDayHighPrice-previousDayLowPrice)*(1.1666));
        r[2]=previousDayClosePrice+((previousDayHighPrice-previousDayLowPrice)*(1.2500));
        r[3]=previousDayClosePrice+((previousDayHighPrice-previousDayLowPrice)*(1.5000));

        s[0]=previousDayClosePrice-((previousDayHighPrice-previousDayLowPrice)*(1.0833));
        s[1]=previousDayClosePrice-((previousDayHighPrice-previousDayLowPrice)*(1.1666));
        s[2]=previousDayClosePrice-((previousDayHighPrice-previousDayLowPrice)*(1.2500));
        s[3]=previousDayClosePrice-((previousDayHighPrice-previousDayLowPrice)*(1.5000));
    }

    public double[] getR() {
        return r;
    }

    public double[] getS() {
        return s;
    }

    public double getPp() {
        return pp;
    }
}
