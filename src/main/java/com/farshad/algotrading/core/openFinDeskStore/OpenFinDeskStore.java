package com.farshad.algotrading.core.openFinDeskStore;

 import com.farshad.algotrading.core.sockets.SocketUtil;
import org.ta4j.core.TimeSeries;


public abstract class OpenFinDeskStore {

    public abstract void initialize(String name,String timeFrame,String financialMarket,SocketUtil socketUtil);

    public abstract void fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(String symbolName,String timeFrame,int numberOfRequiredCandles,int count);

    public abstract TimeSeries getSeries(String symbolName,String timeFrame,int numberOfLinesToread);

    public abstract String findLatestTimeDownloaded(String symbolName,String timeFrame);



}
