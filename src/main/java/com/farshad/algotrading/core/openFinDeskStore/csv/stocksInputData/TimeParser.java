package com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData;

import java.util.regex.Pattern;

public class TimeParser {


    private int year,month,dates,hour,minute;

    private String stringToParse;

    public TimeParser(String s) {
        this.stringToParse=s;
        parse();
    }


    public void parse(){
         String myDate=stringToParse.split(" ")[0];
         String myTime=stringToParse.split(" ")[1];

         hour=Integer.parseInt(myTime.split(":")[0]);
         minute=Integer.parseInt(myTime.split(":")[1]);

         year=Integer.parseInt(myDate.split(Pattern.quote("."))[0].replaceAll("^[0]+", ""));
         month=Integer.parseInt(myDate.split(Pattern.quote("."))[1].replaceAll("^[0]+", ""));
         dates=Integer.parseInt(myDate.split(Pattern.quote("."))[2].replaceAll("^[0]+", ""));

    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDates() {
        return dates;
    }

    public void setDates(int dates) {
        this.dates = dates;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
