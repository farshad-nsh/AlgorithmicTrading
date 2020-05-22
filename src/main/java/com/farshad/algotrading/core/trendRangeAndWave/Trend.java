package com.farshad.algotrading.core.trendRangeAndWave;

import com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements.PriceTime;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author farshad noravesh
 * @since version 1.0.0
 */
public class Trend {
    final static Logger logger= Logger.getLogger(TrendGenerator.class);

     private String trendType;      //upward or downward

     private List<PriceTime> priceTimePoints;

    public Trend(String trendType, List<PriceTime> priceTimePoints) {
        this.trendType = trendType;
        this.priceTimePoints = priceTimePoints;
        logger.info("adding trend:"+trendType+" ,price1:"+priceTimePoints.get(0).getPrice()+" ,price2:"+priceTimePoints.get(1).getPrice());
    }

    public String getTrendType() {
        return trendType;
    }

    public List<PriceTime> getPriceTimePoints() {
        return priceTimePoints;
    }

    public double getSlope() {
        double priceDifference=priceTimePoints.get(1).getPrice()-priceTimePoints.get(0).getPrice();
        double timeDifference=(priceTimePoints.get(1).getTime().getEpochSecond()-priceTimePoints.get(0).getTime().getEpochSecond());
        double slope=priceDifference/timeDifference;
        logger.info("slope of trend is:"+slope);
        return slope;
    }
}
