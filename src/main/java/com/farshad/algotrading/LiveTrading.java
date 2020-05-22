package com.farshad.algotrading;

import com.farshad.algotrading.OpenFinDesklogLevels.LiveTradingLogLevel;
import com.farshad.algotrading.core.OpenFinDeskExitRule.QuantizedTpAndSlExitRule;
import com.farshad.algotrading.core.OpenFinDeskExitRule.OpenFinDeskExitRule;
import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import com.farshad.algotrading.core.openFinDeskStore.influxdb.InfluxDB;
import com.farshad.algotrading.core.sockets.SocketUtil;
import com.farshad.algotrading.openFinDeskAnnotations.StrategyContainerHandler;
import org.apache.log4j.Logger;

import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.OpenFinDeskMessage;
import com.farshad.algotrading.core.Symbol;

import java.io.File;
import java.net.ServerSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Farshad Noravesh
 */
public class LiveTrading {

    final static Logger logger= Logger.getLogger(LiveTrading.class);
    private  ServerSocket ss;
    static int loop=0;
    static int symbolId=0;
    private StrategyContainerHandler strategyContainerHandler;
    public LiveTrading() throws IOException {
        String directory="openFinDeskLogs";
        new File(directory).mkdirs();
        System.setProperty("java.net.preferIPv4Stack" , "true");
        ss = new ServerSocket(5555);
    }



    public void beginLiveTrading() throws IOException, InterruptedException {

        logger.log(LiveTradingLogLevel.PROCESS,"Live Algorithmic Trading Started");
        logger.log(LiveTradingLogLevel.SIGNALS, "Trading Signals using OpenFinDesk,analyzing Forex...");

        SocketUtil socketUtil=null;

        InfluxDB influxDB=new InfluxDB();
        Symbol<InfluxDB> eurusdSymbol = new Symbol<InfluxDB>(influxDB,"EURUSD", new String[]{"PERIOD_H1","PERIOD_D1"}, "forex",ss);
        Symbol<InfluxDB> audusdSymbol = new Symbol<InfluxDB>(influxDB,"AUDUSD", new String[]{"PERIOD_H1","PERIOD_D1"}, "forex",ss);
        Symbol<InfluxDB> gbpusdSymbol = new Symbol<InfluxDB>(influxDB,"GBPUSD", new String[]{"PERIOD_H1","PERIOD_D1"}, "forex",ss);
        Symbol<InfluxDB> euraudSymbol = new Symbol<InfluxDB>(influxDB,"EURAUD", new String[]{"PERIOD_H1","PERIOD_D1"}, "forex",ss);

        Map<Integer,Symbol> idToSymbol=new HashMap<>();
        idToSymbol.put(0,eurusdSymbol);
        idToSymbol.put(1,audusdSymbol);
        idToSymbol.put(2,gbpusdSymbol);
        idToSymbol.put(3,euraudSymbol);

        OpenFinDeskExitRule quantizedTpAndSlExitRule=new QuantizedTpAndSlExitRule();

        while(true) {
            loop++;
            symbolId = loop % 4;
            System.out.println("loop=" + loop);
            strategyContainerHandler=new StrategyContainerHandler();
            strategyContainerHandler.setNumberOfContainers(6);
            idToSymbol.get(symbolId).fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(25);

            socketUtil = idToSymbol.get(symbolId).getSocketUtil();
            strategyContainerHandler.setTimeSeriesForAllTimeFrames(idToSymbol.get(symbolId).getSeries());
            strategyContainerHandler.handle();

            List<OpenFinDeskOrder> openFinDeskOrders = strategyContainerHandler.getOpenFinDeskOrders();
            openFinDeskOrders.stream().forEach(op->logger.info("op="+op.getStrategyName()));

            for (int i = 0; i < openFinDeskOrders.size(); i++) {

                if (strategyContainerHandler.getContainers().get(i).isDisabled()){
                    continue;
                }

            logger.log(LiveTradingLogLevel.PROCESS, "oh,let me see if there is a signal for " + strategyContainerHandler.getOpenFinDeskOrders().get(i).getSymbol()+" in strategy container "+i);
                quantizedTpAndSlExitRule.setSeries(idToSymbol.get(symbolId).getSeries()[0]);
                quantizedTpAndSlExitRule.setOpenFinDeskOrder(openFinDeskOrders.get(i));
                quantizedTpAndSlExitRule.applyExitRule();
                openFinDeskOrders.set(i, quantizedTpAndSlExitRule.getOpenFinDeskOrder());
            if (!openFinDeskOrders.get(i).getStrategyName().equals("noStrategy")) {
                if((openFinDeskOrders.get(i).getOrderType().equals("openBuy"))||(openFinDeskOrders.get(i).getOrderType().equals("openSell"))){
                logger.log(LiveTradingLogLevel.SIGNALS, "strategy:" + openFinDeskOrders.get(i).getStrategyName() + " ,symbol:" + openFinDeskOrders.get(i).getSymbol());
                logger.log(LiveTradingLogLevel.SIGNALS, "orderType:" + openFinDeskOrders.get(i).getOrderType() + " ,profitMargin:" + openFinDeskOrders.get(i).getTakeProfit() + " ,lossMargin:" + openFinDeskOrders.get(i).getStopLoss());
                }
            }
            logger.info("openFinDeskOrder.getOrderType()=" + openFinDeskOrders.get(i).getOrderType());
            logger.debug("---------end of processing in loop:" + loop + "-----------");


            if (!(openFinDeskOrders.get(i).getOrderType().equals("noOrder"))) {
                 String findAllTicketNumbersCommand = "findAllTicketNumbers" + "," + idToSymbol.get(symbolId).getSymbolName() + "," + "end";
                 OpenFinDeskMessage findAllTicketNumbersMessage = socketUtil.sendMessage(findAllTicketNumbersCommand);
                 idToSymbol.get(symbolId).setTicketNumbers(findAllTicketNumbersMessage);
                 String order = idToSymbol.get(symbolId).stringifyOrder(openFinDeskOrders.get(i));
                 logger.info("order=" + order);
                 socketUtil.sendMessage(order);
            }
        }

            String getProfitForTheCurrentSymbolCommand="getProfitFromHistory"+","+ idToSymbol.get(symbolId).getSymbolName()+","+"end";
            OpenFinDeskMessage profitForCurrentSymbolMessage=socketUtil.sendMessage(getProfitForTheCurrentSymbolCommand);
            if(!profitForCurrentSymbolMessage.getPayload().equals("")){
                double reward= Double.parseDouble(profitForCurrentSymbolMessage.getPayload());
                idToSymbol.get(symbolId).setReward(reward);
            }
        }
    }



}
