package com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData;

public class OHLCData {

    String symbol;
    String time;
    String open;
    String high;
    String low;
    String close;
    String tick_volume;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getTick_volume() {
        return tick_volume;
    }

    public void setTick_volume(String tick_volume) {
        this.tick_volume = tick_volume;
    }
}
