package com.farshad.algotrading.openFinDeskStrategies.pivotPointBased;

import com.farshad.algotrading.core.customizedPivotPointMethods.CamarillaPivotPointApproach;
import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Callable;

public class CamarillaStrategy extends OpenFinDeskStrategy {
    final static Logger logger= Logger.getLogger(CamarillaStrategy.class);

    @Override
    public Callable<OpenFinDeskOrder> define() throws IOException {
        OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());
        openFinDeskOrder.setSymbol(series.getName());
        openFinDeskOrder.setStrategyName("CamarillaStrategy");
        openFinDeskOrder.setPosition("*");
        int lastBarIndex=series.getBarCount()-1;
        CamarillaPivotPointApproach camarillaPivotPointApproach=new CamarillaPivotPointApproach(series);
        double pp=camarillaPivotPointApproach.getPp();
        double[] r=camarillaPivotPointApproach.getR();
        double[] s=camarillaPivotPointApproach.getS();

        double supportMargin0=series.getBar(lastBarIndex).getClosePrice().doubleValue()-s[0];
        double resistanceMargin0=series.getBar(lastBarIndex).getClosePrice().doubleValue()-r[0];

        double supportMargin1=series.getBar(lastBarIndex).getClosePrice().doubleValue()-s[1];
        double resistanceMargin1=series.getBar(lastBarIndex).getClosePrice().doubleValue()-r[1];


        double supportMargin2=series.getBar(lastBarIndex).getClosePrice().doubleValue()-s[2];
        double resistanceMargin2=series.getBar(lastBarIndex).getClosePrice().doubleValue()-r[2];


        double supportMargin3=series.getBar(lastBarIndex).getClosePrice().doubleValue()-s[3];
        double resistanceMargin3=series.getBar(lastBarIndex).getClosePrice().doubleValue()-r[3];


        if (Math.abs(supportMargin0)<0.0001){
            openFinDeskOrder.setPosition("buy");
        }else if(Math.abs(resistanceMargin0)<0.0001){
            openFinDeskOrder.setPosition("sell");
        }


        if (Math.abs(supportMargin1)<0.0001){
            openFinDeskOrder.setPosition("buy");
        }else if(Math.abs(resistanceMargin1)<0.0001){
            openFinDeskOrder.setPosition("sell");
        }

        if (Math.abs(supportMargin2)<0.0001){
            openFinDeskOrder.setPosition("buy");
        }else if(Math.abs(resistanceMargin2)<0.0001){
            openFinDeskOrder.setPosition("sell");
        }

        if (Math.abs(supportMargin3)<0.0001){
            openFinDeskOrder.setPosition("buy");
        }else if(Math.abs(resistanceMargin3)<0.0001){
            openFinDeskOrder.setPosition("sell");
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
