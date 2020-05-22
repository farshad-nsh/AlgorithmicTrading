/*
package com.farshad.algotrading.openFinDeskStrategies.statisticalArbitrageBasedOnKalmanFilter;

import org.apache.log4j.Logger;
import org.ta4j.com.farshad.algotrading.containerCore.TimeSeriesPoint;
import org.ta4j.com.farshad.algotrading.containerCore.TradingRecord;
import com.farshad.algotrading.containerCore.Symbol;

import java.io.IOException;

public class Process {
    private static Logger LOGGER= Logger.getLogger(Process.class);

    boolean mReinvest = false;

    String mX, mY;
    //TradingContext mContext;
    Cointegration mCoint;

    TimeSeriesPoint mAlpha;
    TimeSeriesPoint mBeta;
    TimeSeriesPoint mXs;
    TimeSeriesPoint mYs;
    TimeSeriesPoint mError;
    TimeSeriesPoint mVariance;
    TimeSeriesPoint mModel;

    TimeSeriesPoint series;
    TradingRecord tradingRecord;

    public Process(TimeSeriesPoint series, TradingRecord tradingRecord) {
        this.series = series;
        this.tradingRecord = tradingRecord;
    }

    public void initialize() throws IOException {
        //mContext = context;

        mCoint = new Cointegration(1e-10, 1e-7);
        mAlpha=new Symbol("alpha").getSeriesFromFile("/src/main/java/files/sarood-M1.csv");
        mBeta=new Symbol("beta").getSeriesFromFile("/src/main/java/files/sarood-M1.csv");
        mXs=new Symbol("mXs").getSeriesFromFile("/src/main/java/files/sarood-M1.csv");
        mYs=new Symbol("mYs").getSeriesFromFile("/src/main/java/files/sarood-M1.csv");
        mError=new Symbol("mError").getSeriesFromFile("/src/main/java/files/sarood-M1.csv");
        mVariance=new Symbol("mVariance").getSeriesFromFile("/src/main/java/files/sarood-M1.csv");
        mModel=new Symbol("mModel").getSeriesFromFile("/src/main/java/files/sarood-M1.csv");

        // mAlpha = new DoubleSeries("alpha");
        //mBeta = new DoubleSeries("beta");
        //mXs = new DoubleSeries("x");
        //mYs = new DoubleSeries("y");
        //mError = new DoubleSeries("error");
        //mVariance = new DoubleSeries("variance");
        //mModel = new DoubleSeries("model");
    }

    public void calculate(){
        //mContext=context;
        double x = mXs.getLastBar().getClosePrice().doubleValue();
        double y = mYs.getLastBar().getClosePrice().doubleValue();
        double alpha = mCoint.getAlpha();
        double beta = mCoint.getBeta();

        mCoint.step(x, y);
        mAlpha.add(alpha, mContext.getTime());
        mBeta.add(beta, mContext.getTime());
        mXs.add(x, mContext.getTime());
        mYs.add(y, mContext.getTime());
        mError.add(mCoint.getError(), mContext.getTime());
        mVariance.add(mCoint.getVariance(), mContext.getTime());

        double error = mCoint.getError();

        mModel.add(beta * x + alpha, mContext.getTime());
        LOGGER.info("beta * x + alpha="+beta * x + alpha+" time="+mContext.getTime() );
    }

}
*/
