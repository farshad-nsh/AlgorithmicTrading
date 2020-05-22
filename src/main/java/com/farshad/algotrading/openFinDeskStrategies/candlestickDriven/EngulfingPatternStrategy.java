package com.farshad.algotrading.openFinDeskStrategies.candlestickDriven;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Callable;

public class EngulfingPatternStrategy  extends OpenFinDeskStrategy {
    final static Logger logger= Logger.getLogger(EngulfingPatternStrategy.class);

    @Override
    public Callable<OpenFinDeskOrder> define() throws IOException {
        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());
        openFinDeskOrder.setSymbol(series.getName());
        openFinDeskOrder.setStrategyName("EngulfingPatternStrategy");
        openFinDeskOrder.setPosition("*");
        int index=series.getBarCount();
        double previousBarOpenPrice=series.getBar(index-3).getOpenPrice().doubleValue();
        double currentBarOpenPrice=series.getBar(index-2).getOpenPrice().doubleValue();
        double nextBarOpenPrice=series.getBar(index-1).getOpenPrice().doubleValue();

        double previousBarClosePrice=series.getBar(index-3).getClosePrice().doubleValue();
        double currentBarClosePrice=series.getBar(index-2).getClosePrice().doubleValue();
        double nextBarClosePrice=series.getBar(index-1).getClosePrice().doubleValue();

        if((currentBarClosePrice>previousBarOpenPrice)&&(currentBarOpenPrice<previousBarClosePrice)){
            if(nextBarOpenPrice>currentBarOpenPrice) {
                openFinDeskOrder.setPosition("bullishEngulfingCandleStick");
            }
        }else if((currentBarOpenPrice>previousBarClosePrice)&&(currentBarClosePrice<previousBarOpenPrice)){
           if(nextBarOpenPrice<currentBarOpenPrice){
               openFinDeskOrder.setPosition("bearishEngulfingCandleStick");
           }
        }


        logger.info("symbol="+openFinDeskOrder.getSymbol());
        logger.info("position="+openFinDeskOrder.getPosition());
        OpenFinDeskOrder finalOpenFinDeskOrder = openFinDeskOrder;
        return () -> {
            logger.debug("Observable thread: " + Thread.currentThread().getName());
            return openFinDeskOrder;
        };
    }
}
