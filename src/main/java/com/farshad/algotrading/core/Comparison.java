package com.farshad.algotrading.core;

import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.TimeParser;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author farshad noravesh
 * @since version 1.0.0
 */
public class Comparison {

    final static Logger logger= Logger.getLogger(Comparison.class);


    private long difference;

    public int compareTwoTimes(String time1, String time2){

        TimeParser timeParser1=new TimeParser(time1);
        LocalDateTime ldt1 = LocalDateTime.of(timeParser1.getYear(), Month.of(timeParser1.getMonth()), timeParser1.getDates(), timeParser1.getHour(), timeParser1.getMinute());
        ZonedDateTime tehranDateTime1 ;
        tehranDateTime1=ldt1.atZone(ZoneId.of("Asia/Tehran"));
        logger.info("time1="+time1);
        logger.info("Tehran Date Time1="+tehranDateTime1);

        TimeParser timeParser2=new TimeParser(time2);
        LocalDateTime ldt2 = LocalDateTime.of(timeParser2.getYear(), Month.of(timeParser2.getMonth()), timeParser2.getDates(), timeParser2.getHour(), timeParser2.getMinute());
        ZonedDateTime tehranDateTime2 ;
        tehranDateTime2=ldt2.atZone(ZoneId.of("Asia/Tehran"));
        logger.info("time2="+time2);
        logger.info("Tehran Date Time2="+tehranDateTime2);
        difference=zonedDateTimeDifference(tehranDateTime1,tehranDateTime2,ChronoUnit.MINUTES);
        return tehranDateTime1.toInstant().compareTo(tehranDateTime2.toInstant());
    }


    public int compareTwoTimesWithFormat(String time1, String time2) {
        difference=zonedDateTimeDifference(ZonedDateTime.parse(time1),ZonedDateTime.parse(time2),ChronoUnit.MINUTES);
        return ZonedDateTime.parse(time1).toInstant().compareTo(ZonedDateTime.parse(time2).toInstant());

    }


    static long zonedDateTimeDifference(ZonedDateTime d1, ZonedDateTime d2, ChronoUnit unit){
        return unit.between(d2, d1);
    }


    public long getDifference() {
        return difference;
    }
}
