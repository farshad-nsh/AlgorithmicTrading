package com.farshad.algotrading.core.fibonacciCore;

import com.farshad.algotrading.core.trendRangeAndWave.Trend;
import com.farshad.algotrading.core.trendRangeAndWave.Wave;
import org.apache.log4j.Logger;

import java.util.List;

public class FibonacciHelper {

    final static Logger logger= Logger.getLogger(FibonacciHelper.class);

    private Trend lastTrend;
    private List<Wave> waveList;
    private double strength;
    private Fibonacci fibonacci;

    public FibonacciHelper(List<Wave> waveList, Trend lastTrend) {
        this.waveList=waveList;
        this.lastTrend=lastTrend;
    }

    public void startProcedure() {
        fibonacci=new Fibonacci();
        logger.info("wavelist.size="+waveList.size());
        fibonacci.addWaves(waveList);
        if(lastTrend!=null){
            fibonacci.addTrend(lastTrend);
        }
        fibonacci.calculateTypeOfGlobalRegime();
        logger.info("fibonacci.getTypeOfGlobalRegime()="+fibonacci.getTypeOfGlobalRegime());
        fibonacci.calculateInternalRetracement();
        fibonacci.calculateGlobalStrength();
         strength=fibonacci.getStrength();
     }


    public double getStrength() {
        return strength;
    }


    public Fibonacci getFibonacci() {
        return fibonacci;
    }
}
