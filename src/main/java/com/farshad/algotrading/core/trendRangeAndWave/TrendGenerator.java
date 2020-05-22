package com.farshad.algotrading.core.trendRangeAndWave;

import com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements.PriceTime;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TrendGenerator {
    final static Logger logger= Logger.getLogger(TrendGenerator.class);

    private int startingIndex;
    private List<PriceTime> priceTimeChangePointList;

    private List<Trend> trendList=new ArrayList<>();


    public TrendGenerator(List<PriceTime> priceTimeChangePointList) {
       this.priceTimeChangePointList=priceTimeChangePointList;
    }

    public List<Trend> generate(){
        priceTimeChangePointList.stream().forEach(priceTime -> {
            logger.info("priceTimeChangePointList:"+priceTime.getPrice()+" ,"+priceTime.getTime());
        });

        double difference=0;

        for(int i=0;i<priceTimeChangePointList.size()-1;i++){
            List<PriceTime> priceTimes=new ArrayList<>();

            PriceTime currentPriceTime=priceTimeChangePointList.get(i);
            PriceTime nextPriceTime=priceTimeChangePointList.get(i+1);
            priceTimes.add(currentPriceTime);
            priceTimes.add(nextPriceTime);
            difference=nextPriceTime.getPrice()-currentPriceTime.getPrice();
            if((difference>0)&&(nextPriceTime.getTime().getEpochSecond()>currentPriceTime.getTime().getEpochSecond())){
                Trend trend=new Trend("upward",priceTimes);
                trendList.add(trend);
            }else if((difference<0)&&(nextPriceTime.getTime().getEpochSecond()>currentPriceTime.getTime().getEpochSecond())){
                Trend trend=new Trend("downward",priceTimes);
                trendList.add(trend);
            }

        }

        startingIndex=0;

        if (trendList.size()!=1) {
            if (trendList.get(0).getTrendType().equals("downward")) {
                if (trendList.get(1).getPriceTimePoints().get(1).getPrice() > trendList.get(0).getPriceTimePoints().get(0).getPrice()) {
                    startingIndex = 1;
                }
            } else {
                //upward
                if (trendList.get(0).getPriceTimePoints().get(0).getPrice() > trendList.get(1).getPriceTimePoints().get(1).getPrice()) {
                    startingIndex = 1;
                }
            }
        }

        trendList.stream().forEach(trend -> {
            logger.info("trend.getTrendType()="+trend.getTrendType());
            logger.info("trend.point1="+trend.getPriceTimePoints().get(0).getPrice());
            logger.info("trend.point2="+trend.getPriceTimePoints().get(1).getPrice());

        });
        return trendList;
    }


    public int getStartingIndex() {
        return startingIndex;
    }
}
