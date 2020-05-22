package com.farshad.algotrading.openFinDeskStrategies.candlestickDriven;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Callable;

public class PinBarStrategy extends OpenFinDeskStrategy {
    final static Logger logger= Logger.getLogger(PinBarStrategy.class);

    @Override
    public Callable<OpenFinDeskOrder> define() throws IOException {
        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());
        openFinDeskOrder.setSymbol(series.getName());
        openFinDeskOrder.setStrategyName("PinBarStrategy");
        openFinDeskOrder.setPosition("*");

        int index=series.getBarCount();
        double bodyLength=series.getBar(index-2).getClosePrice().doubleValue()-series.getBar(index-2).getOpenPrice().doubleValue();
        double highMinusLow=series.getBar(index-2).getMaxPrice().doubleValue()-series.getBar(index-2).getMinPrice().doubleValue();
        openFinDeskOrder.setParameter(String.valueOf(bodyLength));

        double shadowLength=0;
        if(bodyLength>0){
            shadowLength=series.getBar(index-1).getMaxPrice().doubleValue()-series.getBar(index-1).getClosePrice().doubleValue();
        }else{
            shadowLength=series.getBar(index-1).getMaxPrice().doubleValue()-series.getBar(index-1).getOpenPrice().doubleValue();
        }

        if(highMinusLow>(3*Math.abs(bodyLength))){
              if(shadowLength>3*bodyLength) {
                  if (series.getBar(index - 1).getClosePrice().doubleValue() < series.getBar(index - 2).getClosePrice().doubleValue()) {
                      openFinDeskOrder.setPosition("bearishPinBar");
                  }
              }else {
                  if (series.getBar(index - 1).getClosePrice().doubleValue() > series.getBar(index - 2).getClosePrice().doubleValue()) {
                      openFinDeskOrder.setPosition("bullishPinBar");
                  }
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
