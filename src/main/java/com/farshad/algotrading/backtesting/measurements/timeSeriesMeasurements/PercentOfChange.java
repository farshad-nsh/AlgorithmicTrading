package com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements;


import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "EURUSDChangeInPercent")
public class PercentOfChange extends TimeSeriesPoint {


    @Column(name = "percent")
    private double percent;


    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
