package com.farshad.algotrading;

import com.farshad.algotrading.core.Comparison;
import org.apache.log4j.Logger;

public class TestComparison {

    final static Logger logger= Logger.getLogger(TestComparison.class);

    public static void main(String[] args) {
        Comparison comparison=new Comparison();
      //  int b1=comparison.compareTwoTimes("2019.09.17 12:11","2019.09.17 12:03");
        int b1=comparison.compareTwoTimesWithFormat("2019-09-16T11:58+04:30[Asia/Tehran]","2019-09-16T11:54+04:30[Asia/Tehran]");

        //   2019-09-16T11:54+04:30[Asia/Tehran]
        logger.info("b1="+b1);
        long diff=comparison.getDifference();
        logger.info("difference="+diff);
        //compareTwoTimes(t1,t2)
        //-1 means t1<t2
        //0 means t1=t2
        //1 means t1>t2
    }
}
