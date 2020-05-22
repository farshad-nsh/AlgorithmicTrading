package com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements;

import org.influxdb.annotation.Column;

import java.time.Instant;

 public class TimeSeriesPoint {
    @Column(name = "time")
    private Instant time;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }


    private String symbolName;


     public String getSymbolName() {
         return symbolName;
     }

     public void setSymbolName(String symbolName) {
         this.symbolName = symbolName;
     }
 }
