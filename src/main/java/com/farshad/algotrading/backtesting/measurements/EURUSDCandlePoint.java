package com.farshad.algotrading.backtesting.measurements;


import com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements.TimeSeriesPoint;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "EURUSD")
 public class EURUSDCandlePoint extends TimeSeriesPoint {


    @Column(name = "open")
    private double open;

    @Column(name = "high")
    private double high;

    @Column(name = "low")
    private double low;

    @Column(name = "close")
    private double close;

    @Column(name = "volume")
    private int volume;


    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public int getVolume() {
        return volume;
    }
}



