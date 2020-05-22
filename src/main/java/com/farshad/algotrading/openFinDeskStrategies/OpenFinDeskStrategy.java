package com.farshad.algotrading.openFinDeskStrategies;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import org.ta4j.core.*;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author farshad noravesh
 * @since version 1.0.0
 */
public abstract class OpenFinDeskStrategy {


    public TimeSeries series;

    public abstract Callable<OpenFinDeskOrder> define() throws IOException;

    public OpenFinDeskStrategy() {
    }

    public void start(){
    }

      public TimeSeries setSeries(TimeSeries series) {
        this.series=series;
          return series;
      }



}
