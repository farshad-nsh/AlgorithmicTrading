package com.farshad.algotrading.core.math;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.log4j.Logger;

import java.util.List;

public class AlgoRegression {

    final static Logger logger= Logger.getLogger(AlgoRegression.class);


    private double slope;
    private double intercept;


    public void comparePairs(List<Double> asset1, List<Double> asset2){

        SimpleRegression regression = new SimpleRegression();
        for(int i=0;i<asset1.size();i++){
           regression.addData(asset1.get(i),asset2.get(i));
        }
        /*
        regression.addData(1.1,2.2);
        regression.addData(2,4.2);
        regression.addData(3.1,5.95);
         */
        intercept=regression.getIntercept();
        slope=regression.getSlope();
        logger.info("intercept="+intercept);
        logger.info("slope="+slope);
        logger.info("regression.getSumSquaredErrors()="+regression.getSumSquaredErrors());

    }

    public double estimator(double atPoint){
        return slope*atPoint+intercept;
    }


    public double getSlope(){
          return slope;
    }

    public double getIntercept() {
        return intercept;
    }
}
