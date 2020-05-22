package com.farshad.algotrading;

import com.farshad.algotrading.backtesting.ATSBackTest;
import com.farshad.algotrading.backtesting.BackTest;
import com.farshad.algotrading.backtesting.FetchingForexBacktest;
import org.apache.log4j.Logger;

import java.io.IOException;


/**
 * @author Farshad Noravesh
 */
public class AlgoTrader {

    final static Logger logger= Logger.getLogger(AlgoTrader.class);

    public static void main(String[] args) throws IOException, InterruptedException {

         String mode="live";
        //String mode="backTest";

        if (mode.equals("backTest")){
            //BackTest backTest=new HMMBackTest();
           // BackTest backTest=new FibonacciBackTest();
            //BackTest backTest=new ATSBackTest();
            BackTest backTest=new FetchingForexBacktest();
            backTest.begin();
        }else if(mode.equals("live")){
            LiveTrading liveTrading=new LiveTrading();
            liveTrading.beginLiveTrading();
        }


    }
}
