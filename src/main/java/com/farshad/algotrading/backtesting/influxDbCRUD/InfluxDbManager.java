package com.farshad.algotrading.backtesting.influxDbCRUD;

 import com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements.TimeSeriesPoint;
import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.OHLCData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


 import org.apache.log4j.Logger;
 import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.concurrent.TimeUnit;

public class InfluxDbManager<T extends TimeSeriesPoint > {

    final static Logger logger= Logger.getLogger(InfluxDbManager.class);


    private List<T> timeSeries=new ArrayList<>();

    private InfluxDB influxDB;

    private String database;

    private String measurement;

    public InfluxDbManager(String database, String measurement) {
        this.database = database;
        this.measurement=measurement;
        influxDB = InfluxDBFactory.connect("http://34.151.185.43:8086", "farshad", "yourpassword");
     }

    public void writeCandles(List<OHLCData> ohlcDataList) {

        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            logger.error("Error pinging server");
            return;
        }else{
            logger.info("response.getVersion()="+response.getVersion());
        }

        BatchPoints batchPoints = BatchPoints.database(this.database).tag("async", "true").build();

        influxDB.enableBatch();
        for (int i = 0; i < ohlcDataList.size(); i++) {
            String dateInForex=ohlcDataList.get(i).getTime().split(" ")[0];
            String timeInForex=ohlcDataList.get(i).getTime().split(" ")[1];
            ZoneId zoneId =ZoneId.of("UTC");
            LocalDate localdate =LocalDate.parse(
                    dateInForex.split("\\.")[0]+"-"
                            + dateInForex.split("\\.")[1]+"-"
                            + dateInForex.split("\\.")[2]
            );
            LocalTime localtime = LocalTime.parse(timeInForex+":00");
            ZonedDateTime zt = ZonedDateTime.of(localdate, localtime, zoneId);
            Point point = Point.measurement(this.measurement)
                    .time(zt.toEpochSecond(), TimeUnit.SECONDS)
                    .addField("open", Double.parseDouble(ohlcDataList.get(i).getOpen()))
                    .addField("high", Double.parseDouble(ohlcDataList.get(i).getHigh()))
                    .addField("low", Double.parseDouble((ohlcDataList.get(i).getLow())))
                    .addField("close", Double.parseDouble((ohlcDataList.get(i).getClose())))
                    .addField("volume", Integer.parseInt(ohlcDataList.get(i).getTick_volume()))
                    .build();
            batchPoints.point(point);

        }
         influxDB.write(batchPoints);
    }

    public void writeTimeSeries(List<T> timeSeries,String fieldString) throws NoSuchFieldException, IllegalAccessException {
        this.timeSeries=timeSeries;
        BatchPoints batchPoints = BatchPoints.database(this.database).tag("async", "true").build();
        influxDB.enableBatch();
        for (int i = 0; i < timeSeries.size(); i++) {
            Field field = timeSeries.get(i).getClass().getDeclaredField(fieldString);
            field.setAccessible(true);
            Object value = field.get(timeSeries.get(i));
            Point point = Point.measurement(this.measurement)
                    .time(timeSeries.get(i).getTime().getEpochSecond(), TimeUnit.SECONDS)
                    .addField(fieldString, (Double) value)
                    .build();
            batchPoints.point(point);
        }
        influxDB.write(batchPoints);
    }

    public List<T> executeSomeQuery(String query,Class<T> clazz) {
        Query influxDbQuery = new Query(query, this.database);
        QueryResult queryResult = influxDB.query(influxDbQuery);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<T> resultList = resultMapper
                .toPOJO(queryResult,clazz);
        return resultList;
    }

    public void close() {
        influxDB.close();
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }


}
