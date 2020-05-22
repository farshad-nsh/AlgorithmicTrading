package com.farshad.algotrading.core.openFinDeskStore.influxdb;

import com.farshad.algotrading.backtesting.influxDbCRUD.InfluxDbManager;
import com.farshad.algotrading.backtesting.measurements.AUDUSDCandlePoint;
import com.farshad.algotrading.backtesting.measurements.EURAUDCandlePoint;
import com.farshad.algotrading.backtesting.measurements.EURUSDCandlePoint;
import com.farshad.algotrading.backtesting.measurements.GBPUSDCandlePoint;
import com.farshad.algotrading.core.openFinDeskStore.OpenFinDeskStore;
import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.OHLCData;
import com.farshad.algotrading.core.sockets.SocketUtil;
import org.apache.log4j.Logger;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class InfluxDB extends OpenFinDeskStore {

    final static Logger logger= Logger.getLogger(InfluxDB.class);


    private SocketUtil socketUtil;



    @Override
    public void initialize(String name, String timeFrame, String financialMarket, SocketUtil socketUtil) {
        this.socketUtil=socketUtil;
    }

    @Override
    public void fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(String symbolName,String timeFrame,int numberOfRequiredCandles,int count) {
        socketUtil.getOhlcDataList().clear();


        String message="fetchCandles"+","+symbolName+","+timeFrame+","+0+","+numberOfRequiredCandles;
        try {
            try {
                socketUtil.sendMessage(message);
            } catch (IOException e) {
                logger.error("error for influxDb databases="+e.getMessage());
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            logger.error("error for influxDb databases="+e.getMessage());
            e.printStackTrace();
        }
        List<OHLCData> ohlcDataList=new ArrayList<>();
             ohlcDataList = socketUtil.getOhlcDataList();
            logger.debug("ohlcDataList.size****=" + ohlcDataList.size());
            //System.out.println("time*="+ohlcDataList.get(0).getTime());
            InfluxDbManager influxDbManager = null;
            if (symbolName.equals("EURUSD")) {
                influxDbManager = new InfluxDbManager<EURUSDCandlePoint>(timeFrame, symbolName);
                influxDbManager.writeCandles(ohlcDataList);
            } else if (symbolName.equals("AUDUSD")) {
                influxDbManager = new InfluxDbManager<AUDUSDCandlePoint>(timeFrame, symbolName);
                influxDbManager.writeCandles(ohlcDataList);
            } else if (symbolName.equals("GBPUSD")) {
                influxDbManager = new InfluxDbManager<GBPUSDCandlePoint>(timeFrame, symbolName);
                influxDbManager.writeCandles(ohlcDataList);
            }else if (symbolName.equals("EURAUD")) {
                influxDbManager = new InfluxDbManager<EURAUDCandlePoint>(timeFrame, symbolName);
                influxDbManager.writeCandles(ohlcDataList);
            }
            influxDbManager.close();


    }


    @Override
    public TimeSeries getSeries(String symbolName,String timeFrame,int numberOfLinesToread) {

        InfluxDbManager influxDbManager=null;
        String query=null;
        List<EURUSDCandlePoint> candlePointListEURUSD=null;
        List<GBPUSDCandlePoint> candlePointListGBPUSD=null;
        List<AUDUSDCandlePoint> candlePointListAUDUSD=null;
        List<EURAUDCandlePoint> candlePointListEURAUD=null;

        TimeSeries series = new BaseTimeSeries.SeriesBuilder().withName(symbolName).build();

        if(symbolName.equals("EURUSD")){
            influxDbManager=new InfluxDbManager<EURUSDCandlePoint>(timeFrame,symbolName);
             query="SELECT * FROM "+influxDbManager.getMeasurement()
                     +" ORDER BY time DESC LIMIT "+numberOfLinesToread;
            candlePointListEURUSD=influxDbManager.executeSomeQuery(query, EURUSDCandlePoint.class);
            for(int i=numberOfLinesToread-1;i>=0;i--){
                series.addBar(ZonedDateTime.ofInstant(candlePointListEURUSD.get(i).getTime(),ZoneId.of("UTC")),
                        Double.valueOf(candlePointListEURUSD.get(i).getOpen()),
                        Double.valueOf(candlePointListEURUSD.get(i).getHigh()),
                        Double.valueOf(candlePointListEURUSD.get(i).getLow()),
                        Double.valueOf(candlePointListEURUSD.get(i).getClose()) ,
                        Double.valueOf(candlePointListEURUSD.get(i).getVolume()));
            }
        }else if(symbolName.equals("GBPUSD")){
            influxDbManager=new InfluxDbManager<GBPUSDCandlePoint>(timeFrame,symbolName);
             query="SELECT * FROM "+influxDbManager.getMeasurement()
                     +"  ORDER BY time DESC LIMIT "+numberOfLinesToread;
            candlePointListGBPUSD=influxDbManager.executeSomeQuery(query, GBPUSDCandlePoint.class);
            for(int i=numberOfLinesToread-1;i>=0;i--){
                series.addBar(ZonedDateTime.ofInstant(candlePointListGBPUSD.get(i).getTime(),ZoneId.of("UTC")),
                        Double.valueOf(candlePointListGBPUSD.get(i).getOpen()),
                        Double.valueOf(candlePointListGBPUSD.get(i).getHigh()),
                        Double.valueOf(candlePointListGBPUSD.get(i).getLow()),
                        Double.valueOf(candlePointListGBPUSD.get(i).getClose()) ,
                        Double.valueOf(candlePointListGBPUSD.get(i).getVolume()));
            }
        }else if(symbolName.equals("AUDUSD")){
            influxDbManager=new InfluxDbManager<AUDUSDCandlePoint>(timeFrame,symbolName);
             query="SELECT * FROM "+influxDbManager.getMeasurement()
                     +" ORDER BY time DESC LIMIT "+numberOfLinesToread;
           candlePointListAUDUSD=influxDbManager.executeSomeQuery(query, AUDUSDCandlePoint.class);
            for(int i=numberOfLinesToread-1;i>=0;i--){
                series.addBar(ZonedDateTime.ofInstant(candlePointListAUDUSD.get(i).getTime(),ZoneId.of("UTC")),
                        Double.valueOf(candlePointListAUDUSD.get(i).getOpen()),
                        Double.valueOf(candlePointListAUDUSD.get(i).getHigh()),
                        Double.valueOf(candlePointListAUDUSD.get(i).getLow()),
                        Double.valueOf(candlePointListAUDUSD.get(i).getClose()) ,
                        Double.valueOf(candlePointListAUDUSD.get(i).getVolume()));
            }
         }else if(symbolName.equals("EURAUD")){
            influxDbManager=new InfluxDbManager<EURAUDCandlePoint>(timeFrame,symbolName);
            query="SELECT * FROM "+influxDbManager.getMeasurement()
                    +" ORDER BY time DESC LIMIT "+numberOfLinesToread;
            candlePointListEURAUD=influxDbManager.executeSomeQuery(query, EURAUDCandlePoint.class);
            for(int i=numberOfLinesToread-1;i>=0;i--){
                series.addBar(ZonedDateTime.ofInstant(candlePointListEURAUD.get(i).getTime(),ZoneId.of("UTC")),
                        Double.valueOf(candlePointListEURAUD.get(i).getOpen()),
                        Double.valueOf(candlePointListEURAUD.get(i).getHigh()),
                        Double.valueOf(candlePointListEURAUD.get(i).getLow()),
                        Double.valueOf(candlePointListEURAUD.get(i).getClose()) ,
                        Double.valueOf(candlePointListEURAUD.get(i).getVolume()));
            }
        }

         logger.debug("query in getSeries="+query+" ,and timeFrame="+timeFrame);
        influxDbManager.close();
       return series;
    }

    @Override
    public String findLatestTimeDownloaded(String symbolName,String timeFrame) {
        String latestTimeDownloaded="";
        InfluxDbManager influxDbManager=null;
        if(symbolName.equals("EURUSD")) {
             influxDbManager = new InfluxDbManager<EURUSDCandlePoint>(timeFrame, symbolName);
            String query = "SELECT time,last(close) FROM " + influxDbManager.getMeasurement();
            List<EURUSDCandlePoint> candlePointList = influxDbManager.executeSomeQuery(query, EURUSDCandlePoint.class);
            int size = candlePointList.size();
            logger.debug("latest time downloaded:" + candlePointList.get(size - 1).getTime());
            latestTimeDownloaded=String.valueOf(candlePointList.get(size-1).getTime().atZone(ZoneId.of("UTC")));
        }else if(symbolName.equals("AUDUSD")) {
            influxDbManager = new InfluxDbManager<AUDUSDCandlePoint>(timeFrame, symbolName);
            String query = "SELECT time,last(close) FROM " + influxDbManager.getMeasurement();
            List<AUDUSDCandlePoint> candlePointList = influxDbManager.executeSomeQuery(query, AUDUSDCandlePoint.class);
            int size = candlePointList.size();
            logger.debug("latest time downloaded:" + candlePointList.get(size - 1).getTime());
            latestTimeDownloaded=String.valueOf(candlePointList.get(size-1).getTime().atZone(ZoneId.of("UTC")));
        }else if(symbolName.equals("GBPUSD")) {
            influxDbManager = new InfluxDbManager<GBPUSDCandlePoint>(timeFrame, symbolName);
            String query = "SELECT time,last(close) FROM " + influxDbManager.getMeasurement();
            List<GBPUSDCandlePoint> candlePointList = influxDbManager.executeSomeQuery(query, GBPUSDCandlePoint.class);
            int size = candlePointList.size();
            logger.debug("latest time downloaded:" + candlePointList.get(size - 1).getTime());
            latestTimeDownloaded=String.valueOf(candlePointList.get(size-1).getTime().atZone(ZoneId.of("UTC")));
        }else if(symbolName.equals("EURAUD")) {
            influxDbManager = new InfluxDbManager<EURAUDCandlePoint>(timeFrame, symbolName);
            String query = "SELECT time,last(close) FROM " + influxDbManager.getMeasurement();
            List<EURAUDCandlePoint> candlePointList = influxDbManager.executeSomeQuery(query, EURAUDCandlePoint.class);
            int size = candlePointList.size();
            logger.debug("latest time downloaded:" + candlePointList.get(size - 1).getTime());
            latestTimeDownloaded=String.valueOf(candlePointList.get(size-1).getTime().atZone(ZoneId.of("UTC")));
        }
        influxDbManager.close();

        return latestTimeDownloaded;
    }


}
