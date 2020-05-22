package com.farshad.algotrading.core.OpenFinDeskExitRule;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import org.ta4j.core.TimeSeries;

public abstract class OpenFinDeskExitRule {

    public TimeSeries series;


    private OpenFinDeskOrder openFinDeskOrder;

    public abstract void applyExitRule();

    public  void setOpenFinDeskOrder(OpenFinDeskOrder openFinDeskOrder){
        this.openFinDeskOrder =openFinDeskOrder;

    }


    public TimeSeries setSeries(TimeSeries series) {
        this.series=series;
        return series;
    }

    public OpenFinDeskOrder getOpenFinDeskOrder() {
        return openFinDeskOrder;
    }
}
