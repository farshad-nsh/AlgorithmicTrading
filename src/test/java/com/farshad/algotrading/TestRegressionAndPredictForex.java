package com.farshad.algotrading;

import org.apache.log4j.Logger;

public class TestRegressionAndPredictForex {
    final static Logger logger= Logger.getLogger(TestRegressionAndPredictForex.class);

    public static void main(String[] args) {
        double EURUSD=1.10813;
        double GBPUSD=0.02773169770280957*EURUSD+1.071629011176105;
        logger.info("prediction for GBPUSD="+GBPUSD);

    }

}
