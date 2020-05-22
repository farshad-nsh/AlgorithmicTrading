/*
package com.farshad.algotrading.openFinDeskStrategies.statisticalArbitrageBasedOnKalmanFilter;

import com.farshad.algotrading.containerCore.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.ta4j.com.farshad.algotrading.containerCore.TimeSeriesPoint;
import org.ta4j.com.farshad.algotrading.containerCore.TradingRecord;

import java.io.IOException;
import java.util.concurrent.Callable;

public class KalmanFilterBasedCointegration extends OpenFinDeskStrategy {

    public KalmanFilterBasedCointegration(TimeSeriesPoint series, TradingRecord tradingRecord) {
        super(series, tradingRecord);
    }

    @Override
    public Callable<OpenFinDeskOrder> define() throws IOException {


        Process process=new Process(series,tradingRecord);
        process.initialize();
        process.calculate();


        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());

        openFinDeskOrder.setSymbol("akhaber");
        openFinDeskOrder.setStrategyName("KalmanFilterBasedCointegration");

            openFinDeskOrder.setPosition("buy");
            openFinDeskOrder.setPrice(series.getBar(0).getClosePrice().doubleValue());
            openFinDeskOrder.setStopLoss(3);
            openFinDeskOrder.setTakeProfit(8);
            openFinDeskOrder.setVolume(300);


        OpenFinDeskOrder finalOpenFinDeskOrder = openFinDeskOrder;
        return () -> {
            System.out.println("Observable thread: " + Thread.currentThread().getName());
            return openFinDeskOrder;
        };
    }
}
*/
