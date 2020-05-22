package com.farshad.algotrading.backtesting;


import com.farshad.algotrading.backtesting.influxDbCRUD.InfluxDbManager;
import com.farshad.algotrading.backtesting.measurements.AUDUSDCandlePoint;
import com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements.PriceTime;
import com.farshad.algotrading.core.AlternatingTrendSmoothing.ATS;
import com.farshad.algotrading.core.trendRangeAndWave.Trend;
import com.farshad.algotrading.core.trendRangeAndWave.TrendGenerator;
import com.farshad.algotrading.core.trendRangeAndWave.Wave;
import com.farshad.algotrading.core.trendRangeAndWave.WaveGenerator;
import com.farshad.algotrading.core.fibonacciCore.Fibonacci;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Farshad Noravesh
 */
public class FibonacciBackTest extends BackTest {


    final static Logger logger= Logger.getLogger(FibonacciBackTest.class);


    public FibonacciBackTest() throws IOException {
    }

    @Override
    public void begin() {
        logger.info("---com.farshad.algotrading.backtesting fibonacci---");
        /*
        List<OHLCData> allOhlcDataList=new ArrayList<>();
        int batchSize=6;
        int howManyBatches=2;
        int totalCandles=howManyBatches*batchSize;
        for(int i=1;i<=totalCandles;i=i+batchSize) {
            fetchDataUsingMetaTrader5("EURUSD", "PERIOD_H4", i-1, batchSize);
            allOhlcDataList.addAll(getOhlcDataList());
        }
        allOhlcDataList.stream().forEach(candle-> System.out.println("candle.getClose())="+candle.getClose()));
        */
        InfluxDbManager influxDbManager=new InfluxDbManager<AUDUSDCandlePoint>("H4","EURUSD");
        //influxDbManager.writeCandles(allOhlcDataList);

        String query="SELECT * FROM "+influxDbManager.getMeasurement();
        List<AUDUSDCandlePoint> candlePointList=influxDbManager.executeSomeQuery(query, AUDUSDCandlePoint.class);

        candlePointList.stream().forEach(candle->
                {
                  //  System.out.println("candle.getClose():" + candle.getClose());
                  //  System.out.println("candle.getTime().getEpochSecond():" + candle.getTime().getEpochSecond());
                }
        );
        influxDbManager.close();



        List<PriceTime> priceTimeList=new ArrayList<>();

        for(int i=candlePointList.size()-30;i<candlePointList.size();i++){
            priceTimeList.add(new PriceTime(candlePointList.get(i).getClose(),candlePointList.get(i).getTime()));
        }


        ATS ats=new ATS(6,priceTimeList);
        ats.findPriceChangePoints();

        List<PriceTime> priceTimeChangePointList=ats.getPriceChangeList();

        influxDbManager=new InfluxDbManager<PriceTime>("H4","trendPointsEURUSD");
        influxDbManager.executeSomeQuery("DROP MEASUREMENT \"trendPointsEURUSD\"",PriceTime.class);
        influxDbManager.close();

        influxDbManager=new InfluxDbManager<PriceTime>("H4","trendPointsEURUSD");
        try {
            influxDbManager.writeTimeSeries(priceTimeChangePointList,"price");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        influxDbManager.close();

        TrendGenerator trendGenerator=new TrendGenerator(priceTimeChangePointList);
        List<Trend> trendList=trendGenerator.generate();

        WaveGenerator waveGenerator=new WaveGenerator(trendList,trendGenerator.getStartingIndex());
        List<Wave> waveList=waveGenerator.generate();


        Fibonacci fibonacci=new Fibonacci();
        fibonacci.addWaves(waveList);
        if(trendList.size()%2==1){
            fibonacci.addTrend(trendList.get(trendList.size()-1));
        }

        fibonacci.calculateTypeOfGlobalRegime();
        logger.info("fibonacci.getTypeOfGlobalRegime()="+fibonacci.getTypeOfGlobalRegime());

        fibonacci.calculateInternalRetracement();
        fibonacci.calculateGlobalStrength();
        double strength=fibonacci.getStrength();


        /*
         FiboClusterManager fiboClusterManager=new FiboClusterManager();
        waveList.forEach(w-> {
            fibonacci.setWave(w);
            fiboClusterManager.add(fibonacci);
        });
        fiboClusterManager.manage();
        */


    }




}
