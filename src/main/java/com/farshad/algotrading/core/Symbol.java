package com.farshad.algotrading.core;

 import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
 import com.farshad.algotrading.core.openFinDeskStore.OpenFinDeskStore;
 import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.OHLCData;
 import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.OpenFinDeskMessage;
 import com.farshad.algotrading.core.sockets.SocketUtil;
 import org.apache.log4j.Logger;
 import org.ta4j.core.BaseTimeSeries;
 import org.ta4j.core.TimeSeries;
import org.ta4j.core.num.Num;
 import com.farshad.algotrading.riskManagement.ActionPerformer;
 import com.farshad.algotrading.riskManagement.moneymanagement.MoneyManager;
 import com.farshad.algotrading.riskManagement.positionManagement.PositionManager;

 import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
import java.util.regex.Pattern;

public class Symbol<T extends OpenFinDeskStore> {
    final static Logger logger= Logger.getLogger(Symbol.class);

    private T openFinDeskStore;
    private String financialMarket;

    private String[] timeFrames;
    private String latestTimeDownloaded;
    private int numberOfMissedMonth;
    private int numberOfMissedDates;
    private int numberOfMissedHours;
    private int numberOfMissedMinutes;
    private int[] numberOfRequiredCandles=new int[]{0,0};
    private String[] latestTimeFromMarket=new String[]{"",""};;

    private String symbolName;
    private List<OHLCData> ohlcDataList;
    private int[] numberOfPointsToBeAddedToTimeSeries;
    private int[] count;

    public ServerSocket ss;
    public Socket client;

    public SocketUtil socketUtil;

    public TimeSeries[] series;

    private String newDate = "";
    private String newTime = "";

    private String oldDate;
    private String oldTime;


    private MoneyManager moneyManager;
    private PositionManager positionManager;
    private ActionPerformer actionPerformer;
    private double reward;

    public Symbol(T openFinDeskStore, String symbolName, String[] timeFrames, String financialMarket ,ServerSocket ss) throws IOException {
         this.openFinDeskStore=openFinDeskStore;
        logger.debug("initialized:"+openFinDeskStore.getClass().getCanonicalName());
        this.symbolName=symbolName;
        this.timeFrames=timeFrames;
        this.financialMarket=financialMarket;
        ohlcDataList=new ArrayList<>();
        this.ss=ss;
        this.client=new Socket();
        socketUtil=new SocketUtil(this.ss,client);
        initialize(timeFrames);
        count=new int[timeFrames.length];
        Arrays.fill(count,0);
        numberOfPointsToBeAddedToTimeSeries=new int[timeFrames.length];
        Arrays.fill(numberOfPointsToBeAddedToTimeSeries,0);
        positionManager.setPositionGap(0.0050); //2$
        moneyManager.setNumberOfAllowedOrders(2);
        moneyManager.setNumberOfOpenOrders(0);
        actionPerformer=new ActionPerformer(moneyManager);
        series= new TimeSeries[timeFrames.length];
        Arrays.fill(series,new BaseTimeSeries.SeriesBuilder().withName(symbolName).build());
    }

    public void initialize(String[] timeFrames){
        for(int i=0;i<timeFrames.length;i++) {
            openFinDeskStore.initialize(symbolName,timeFrames[i],financialMarket,socketUtil);
        }
        moneyManager=new MoneyManager();
        positionManager=new PositionManager();
        actionPerformer=new ActionPerformer(moneyManager);
    }

      public void fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(int howMany) throws IOException {
        for(int i=0;i<timeFrames.length;i++) {
            count[i]++;

            logger.info("fetching for timeframe:"+timeFrames[i]+ " count:" + count[i]);
            if (count[i] == 1) {
                numberOfRequiredCandles[i] = howMany;
                numberOfPointsToBeAddedToTimeSeries[i] = numberOfRequiredCandles[i];
            } else {
                try {
                    numberOfRequiredCandles[i] = getNumberOfRequiredCandles(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            logger.debug("numberOfRequiredCandles["+i+"]=" + numberOfRequiredCandles[i]);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            openFinDeskStore.fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(symbolName, timeFrames[i], numberOfRequiredCandles[i], count[i]);

            getSeriesFromstore(i);
        }

    }

    public TimeSeries[] getSeriesFromstore(int whichTimeFrame) throws IOException {
        series[whichTimeFrame]=openFinDeskStore.getSeries(symbolName,timeFrames[whichTimeFrame],numberOfPointsToBeAddedToTimeSeries[whichTimeFrame]);
         return series;
    }

    public int getNumberOfRequiredCandles(int whichTimeFrame) throws IOException {
        findLatestMarketData(whichTimeFrame);
        findLatestTimeDownloaded(whichTimeFrame);
        findMissedTimes(whichTimeFrame);
        if(timeFrames[whichTimeFrame].equals("PERIOD_M1")){
            numberOfRequiredCandles[whichTimeFrame]= numberOfMissedMinutes;
        }else if(timeFrames[whichTimeFrame].equals("PERIOD_H1")){
            numberOfRequiredCandles[whichTimeFrame]=numberOfMissedHours;
        }else if(timeFrames[whichTimeFrame].equals("PERIOD_H4")){
            numberOfRequiredCandles[whichTimeFrame]=numberOfMissedHours/4;
        }else if(timeFrames[whichTimeFrame].equals("PERIOD_D1")){
            numberOfRequiredCandles[whichTimeFrame]=numberOfMissedHours/24;
        }else{
            numberOfRequiredCandles[whichTimeFrame]=1;
        }
        logger.debug(symbolName+" "+timeFrames[whichTimeFrame]+"->after calculating numberOfRequiredCandles="+numberOfRequiredCandles[whichTimeFrame]);
        return numberOfRequiredCandles[whichTimeFrame];
    }



    public TimeSeries[] getSeries(){
        return series;
    }


    public T getOpenFinDeskStore() {
        return openFinDeskStore;
    }


    private void findLatestTimeDownloaded(int whichTimeFrame) throws IOException {
            latestTimeDownloaded=openFinDeskStore.findLatestTimeDownloaded(symbolName,timeFrames[whichTimeFrame]);
    }

    private void convertZoneDateTimeInStringToNormalFormat(String zoneDateTimeInString) {
        String newDateWithDash=zoneDateTimeInString.split("T")[0];
        String[] newDateArray=newDateWithDash.split("-");
        latestTimeDownloaded=newDateArray[0]+"."+newDateArray[1]+"."+newDateArray[2]+" "+zoneDateTimeInString.split("T")[1];
        latestTimeDownloaded=latestTimeDownloaded.split("Z")[0];
        logger.debug("latestTimeDownloaded="+latestTimeDownloaded);
    }

    private void findMissedTimes(int whichTimeFrame){
        //System.out.println("openFinDeskStore.getClass().getCanonicalName()="+openFinDeskStore.getClass().getCanonicalName());
        if(openFinDeskStore.getClass().getCanonicalName().equals("com.farshad.algotrading.core.openFinDeskStore.influxdb.InfluxDB")){
            String zoneDateTimeInString=latestTimeDownloaded;
            logger.debug("zoneDateTimeInString="+zoneDateTimeInString);
            convertZoneDateTimeInStringToNormalFormat(zoneDateTimeInString);
        }else if(openFinDeskStore.getClass().getCanonicalName().equals("com.farshad.algotrading.core.openFinDeskStore.csv.CSV")) {
            logger.debug("no need to convert");
        }
             oldDate = latestTimeDownloaded.split(" ")[0];
            logger.debug("old date=" + oldDate);
             oldTime = latestTimeDownloaded.split(" ")[1];
            logger.debug("old time=" + oldTime);
            newDate = latestTimeFromMarket[whichTimeFrame].split(" ")[0];
                logger.debug("new date=" + newDate);
                newTime = latestTimeFromMarket[whichTimeFrame].split(" ")[1];
               logger.debug("new time=" + newTime);


            numberOfMissedMonth = Integer.parseInt(newDate.split(Pattern.quote("."))[1].replaceAll("^[0]+", "")) -
                    Integer.parseInt(oldDate.split(Pattern.quote("."))[1].replaceAll("^[0]+", ""));
            logger.debug("numberOfMissedMonth=" + numberOfMissedMonth);


            numberOfMissedDates = Integer.parseInt(newDate.split(Pattern.quote("."))[2].replaceAll("^[0]+", "")) -
                    Integer.parseInt(oldDate.split(Pattern.quote("."))[2].replaceAll("^[0]+", ""));
            logger.debug("numberOfMissedDates=" + numberOfMissedDates);
             if (Integer.parseInt(newTime.split(":")[1]) >= Integer.parseInt(oldTime.split(":")[1])) {

                    numberOfMissedHours = Integer.parseInt(newTime.split(":")[0]) -
                            Integer.parseInt(oldTime.split(":")[0]) + numberOfMissedDates * 24;
                    numberOfMissedMinutes = 60 * numberOfMissedHours + Integer.parseInt(newTime.split(":")[1]) -
                            Integer.parseInt(oldTime.split(":")[1]);
                } else {
                    numberOfMissedHours = numberOfMissedDates * 24;
                    if (numberOfMissedHours > 0) {
                        numberOfMissedMinutes = 60 * numberOfMissedHours - (Integer.parseInt(oldTime.split(":")[1]) -
                                Integer.parseInt(newTime.split(":")[1]));

                    } else {
                        numberOfMissedMinutes = 60 - Integer.parseInt(oldTime.split(":")[1]) + Integer.parseInt(newTime.split(":")[1]);

                    }

                }
            logger.debug("numberOfMissedHours=" + numberOfMissedHours);
            logger.debug("numberOfMissedMinutes=" + numberOfMissedMinutes);
    }





    private void findLatestMarketData(int  whichTimeFrame) throws IOException {
        List<OHLCData> ohlcDataList=new ArrayList<>();
        SocketUtil socketUtil=new SocketUtil(ss,client);
        String message="fetchCandles"+","+symbolName+","+timeFrames[whichTimeFrame]+","+0+","+1;
        try {
            socketUtil.sendMessage(message);
        } catch (InterruptedException e) {
            logger.error("error in symbol,message:"+e.getMessage());
            e.printStackTrace();
        }
        ohlcDataList=socketUtil.getOhlcDataList();
        latestTimeFromMarket[whichTimeFrame]=ohlcDataList.get(0).getTime();
    }

    public String getLatestTimeFromMarket(int whichTimeFrame) {
        return latestTimeFromMarket[whichTimeFrame];
    }

    public void setLatestTimeFromMarket(int whichTimeFrame,String latestTimeFromMarket) {
        this.latestTimeFromMarket[whichTimeFrame] = latestTimeFromMarket;
    }


    public ServerSocket getSs() {
        return ss;
    }


    public SocketUtil getSocketUtil() {
        return socketUtil;
    }

    public void closeSocket() throws IOException {
        ss.close();
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void show(int whichTimeFrame) {
        Num firstClosePrice = series[whichTimeFrame].getBar(0).getClosePrice();
        Num secondClosePrice = series[whichTimeFrame].getBar(1).getClosePrice();
        logger.debug("first close time:"+series[whichTimeFrame].getBar(0).getEndTime());
        logger.debug(firstClosePrice.isEqual(secondClosePrice)); // equal to firstClosePrice
        logger.debug("First close price: " + firstClosePrice.doubleValue());
        logger.debug("Second close price: " + secondClosePrice.doubleValue());
    }

    public void setTicketNumbers(OpenFinDeskMessage findAllTicketNumbersMessage) {
        positionManager.updatePositions(findAllTicketNumbersMessage.getPayload());
        actionPerformer.setNumberOfOpenOrders(positionManager.getNumberOfOpenPositions());
    }

    public String stringifyOrder(OpenFinDeskOrder openFinDeskOrder) {
        return  actionPerformer.stringifyOrder(positionManager.filterOrder(openFinDeskOrder));
    }

    public void setReward(double reward) {
        this.reward=reward;
    }


}
