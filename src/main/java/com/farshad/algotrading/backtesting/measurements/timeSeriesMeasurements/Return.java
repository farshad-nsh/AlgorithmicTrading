package com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;


@Measurement(name = "EURUSDReturn")
public class Return extends TimeSeriesPoint{

    @Column(name = "returnValue")
    private double returnValue;

    public double getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(double returnValue) {
        this.returnValue = returnValue;
    }
}
