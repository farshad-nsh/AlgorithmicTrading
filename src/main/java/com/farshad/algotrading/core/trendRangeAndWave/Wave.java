package com.farshad.algotrading.core.trendRangeAndWave;

import org.apache.log4j.Logger;

public class Wave {

    final static Logger logger= Logger.getLogger(Wave.class);


    private Trend firstTrend;
    private Trend secondTrend;
    private String typeOfFirstTrend;
    private String typeOfSecondTrend;
    private String typeOfWave;

    public Wave(Trend firstTrend,Trend secondTrend) {
        logger.info("adding wave with trend1:"+firstTrend.getTrendType()+" ,adding wave with trend2:"+secondTrend.getTrendType());
        logger.debug("firstTrend.price[0]="+firstTrend.getPriceTimePoints().get(0).getPrice());
        logger.debug("firstTrend.price[1]="+firstTrend.getPriceTimePoints().get(1).getPrice());
        logger.debug("secondTrend.price[0]="+secondTrend.getPriceTimePoints().get(0).getPrice());
        logger.debug("secondTrend.price[1]="+secondTrend.getPriceTimePoints().get(1).getPrice());
        this.typeOfWave="rangingWave";

        this.firstTrend = firstTrend;
        this.secondTrend= secondTrend;
        this.typeOfFirstTrend=firstTrend.getTrendType();
        this.typeOfSecondTrend=secondTrend.getTrendType();
        if(firstTrend.getTrendType().equals("downward")){
           if(firstTrend.getPriceTimePoints().get(0).getPrice()>secondTrend.getPriceTimePoints().get(1).getPrice()){
               typeOfWave="decreasing";
           }
        }else if(firstTrend.getTrendType().equals("upward")){
            if(firstTrend.getPriceTimePoints().get(0).getPrice()<secondTrend.getPriceTimePoints().get(1).getPrice()){
                typeOfWave="increasing";
            }
        }else{
            typeOfWave="rangingWave";
        }
        logger.info("type of wave="+typeOfWave);
    }

    public Trend getFirstTrend() {
        return firstTrend;
    }

    public Trend getSecondTrend() {
        return secondTrend;
    }

    public String getTypeOfWave() {
        return typeOfWave;
    }
}
