package com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements;

 import org.influxdb.annotation.Column;
 import org.influxdb.annotation.Measurement;

 import java.time.Instant;


@Measurement(name = "priceTime")
public class PriceTime extends TimeSeriesPoint {


    @Column(name = "price")
    private double price;

    private Instant time;


    public PriceTime(double price,Instant time) {
        this.time = time;
        this.price = price;
    }

    @Override
    public Instant getTime() {
        return time;
    }

    @Override
    public void setTime(Instant time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
