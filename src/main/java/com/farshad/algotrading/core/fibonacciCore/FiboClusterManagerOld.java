/*
package com.farshad.algotrading.openFinDeskStrategies.fibonacciBased.com.farshad.algotrading.containerCore;

import java.util.ArrayList;
import java.util.List;

public class FiboClusterManagerOld {
    
    private List<PriceReversalZone> priceReversalZoneList=new ArrayList<>();
    private List<TimeReversalZone>  timeReversalZoneList=new ArrayList<>();

    private List<FibonacciOld> fibonacciList=new ArrayList<>();


    public void add(FibonacciOld fibonacci) {

        fibonacci.internalRetracement();
        //manual test
        fibonacci.externalRetracement();
        fibonacci.extension();
        fibonacci.projection();
        fibonacci.expansion();
        fibonacciList.add(fibonacci);
    }


    public void manage() {
        System.out.println("managing fiboclusters!");
        showFibonacciLevels();
          PriceReversalZone priceReversalZone=new PriceReversalZone();

              fibonacciList.forEach(fibonacci -> {
                  if (fibonacci.getWave().getTrendType().equals("upward")){
                      priceReversalZone.setLowerPrice(fibonacci.getInternalRetracementList().get(0).getPrice());
                      priceReversalZone.setUpperPrice(fibonacci.getExtensionList().get(0).getPrice());

                  }else if (fibonacci.getWave().getTrendType().equals("downward")){
                      priceReversalZone.setLowerPrice(fibonacci.getExternalRetracementList().get(0).getPrice());
                      priceReversalZone.setUpperPrice(fibonacci.getExtensionList().get(0).getPrice());

                  }
              });


        priceReversalZoneList.add(priceReversalZone);
    }

    private void showFibonacciLevels() {
        fibonacciList.forEach(i-> {
            System.out.println("i.getInternalRetracementList().get(0).getPrice()="+i.getInternalRetracementList().get(0).getPrice());
            System.out.println("i.getInternalRetracementList().get(0).getTime()="+i.getInternalRetracementList().get(0).getTime());

            System.out.println("i.getExternalRetracementList().get(0).getPrice()="+i.getExternalRetracementList().get(0).getPrice());
            System.out.println("i.getExternalRetracementList().get(0).getTime()="+i.getExternalRetracementList().get(0).getTime());


            System.out.println("i.getExtensionList().get(0).getPrice()="+i.getExtensionList().get(0).getPrice());
            System.out.println("i.getExtensionList().get(0).getTime()="+i.getExtensionList().get(0).getTime());

        });

    }

    public List<PriceReversalZone> getPriceReversalZoneList() {
        return priceReversalZoneList;
    }

    public List<TimeReversalZone> getTimeReversalZoneList() {
        return timeReversalZoneList;
    }

    //getters

    public List<FibonacciOld> getFibonacciList() {
        return fibonacciList;
    }
}
*/
