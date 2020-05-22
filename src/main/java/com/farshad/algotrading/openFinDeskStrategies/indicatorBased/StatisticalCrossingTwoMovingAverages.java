package com.farshad.algotrading.openFinDeskStrategies.indicatorBased;


import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.openFinDeskStrategies.OpenFinDeskStrategy;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class StatisticalCrossingTwoMovingAverages extends OpenFinDeskStrategy {


    public static double  meanOfProbabilityDistribution;

    public static int  batchSizeToCalculateMeanOfProbabilityDistribution;

    public static int indexBeforeReachingBatchSize;

    public static List<Double> batchList=new ArrayList<>();

    private static StatisticalCrossingTwoMovingAverages statisticalCrossingTwoMovingAverages_instance=null;

    public StatisticalCrossingTwoMovingAverages( ) {
        System.out.println("first and last instance of StatisticalCrossingTwoMovingAverages created!");
        batchSizeToCalculateMeanOfProbabilityDistribution=5;
        indexBeforeReachingBatchSize=0;
    }


    public static StatisticalCrossingTwoMovingAverages getInstance(){
        if (statisticalCrossingTwoMovingAverages_instance == null){
          //  statisticalCrossingTwoMovingAverages_instance = new StatisticalCrossingTwoMovingAverages(eurusdSymbol.getSeriesFromFile(eurusdSymbol.getFileName()));
        }
        return statisticalCrossingTwoMovingAverages_instance;
    }



    @Override
        public Callable<OpenFinDeskOrder> define() {
        int index=series.getBarCount();

        indexBeforeReachingBatchSize++;
            System.out.println("indexBeforeReachingBatchSize="+indexBeforeReachingBatchSize);
           if (indexBeforeReachingBatchSize<batchSizeToCalculateMeanOfProbabilityDistribution){
               batchList.add(series.getBar(index-1).getClosePrice().doubleValue());
           }else if(indexBeforeReachingBatchSize==batchSizeToCalculateMeanOfProbabilityDistribution){
               batchList.add(series.getBar(index-1).getClosePrice().doubleValue());
                double sum = 0;
               //sum=batchList.stream().mapToDouble(p->p).sum();
               sum=batchList.parallelStream().mapToDouble(p->p).sum();
               System.out.println("sum="+sum );
               meanOfProbabilityDistribution= sum /(batchList.size());
               System.out.println("meanOfProbabilityDistribution="+meanOfProbabilityDistribution);
           }else{
               indexBeforeReachingBatchSize=0;
           }

            ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
            int big=20;
            SMAIndicator shortSma = new SMAIndicator(closePrice, 5);
            SMAIndicator longSma = new SMAIndicator(closePrice, big);
            for(int i=index-1;i>index-2;i--){
                System.out.println("5-ticks-SMA value at the "+i+"th index: " + shortSma.getValue(i).doubleValue());
                System.out.println("20-ticks-SMA value at the "+i+"th index: " + longSma.getValue(i).doubleValue());
            }


            start();


            OpenFinDeskOrder openFinDeskOrder=new OpenFinDeskOrder(series.getName());
            System.out.println("symbol name inside CrossingTwoMovingAverages:"+series.getName());
            openFinDeskOrder.setSymbol(series.getName());
            //openFinDeskOrder.setStrategyName("CrossingTwoMovingAverages");
            openFinDeskOrder.setPosition("noPosition");
            if (shortSma.getValue(index-1).doubleValue()>longSma.getValue(index-1).doubleValue()){
                if(series.getBar(index-1).getClosePrice().doubleValue()>shortSma.getValue(index-1).doubleValue())
                {
                    if (series.getBar(index-1).getClosePrice().doubleValue()<meanOfProbabilityDistribution){
                        openFinDeskOrder.setPosition("buy");
                        openFinDeskOrder.setPrice(series.getBar(index-1).getClosePrice().doubleValue());
                        openFinDeskOrder.setStopLoss(series.getBar(index-1).getClosePrice().doubleValue() - 0.03);
                        openFinDeskOrder.setTakeProfit(series.getBar(index-1).getClosePrice().doubleValue() + 0.03);
                        openFinDeskOrder.setVolume("0.01");
                    }
                }
            }else{
                if(series.getBar(index-1).getClosePrice().doubleValue()<longSma.getValue(index-1).doubleValue())
                {
                    if (series.getBar(index-1).getClosePrice().doubleValue()>meanOfProbabilityDistribution) {
                        openFinDeskOrder.setVolume("0.01");
                        openFinDeskOrder.setPosition("sell");
                        openFinDeskOrder.setPrice(series.getBar(index - 1).getClosePrice().doubleValue());
                        openFinDeskOrder.setStopLoss(series.getBar(index - 1).getClosePrice().doubleValue() + 0.03);
                        openFinDeskOrder.setTakeProfit(series.getBar(index - 1).getClosePrice().doubleValue() - 0.03);
                    }
                }
            }

            OpenFinDeskOrder finalOpenFinDeskOrder = openFinDeskOrder;
            return () -> {
                System.out.println("Observable thread: " + Thread.currentThread().getName());
                return openFinDeskOrder;
            };

        }



}
