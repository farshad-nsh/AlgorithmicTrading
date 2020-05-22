package com.farshad.algotrading.riskManagement;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import org.apache.log4j.Logger;
import com.farshad.algotrading.riskManagement.moneymanagement.MoneyManager;

public class ActionPerformer {
    final static Logger logger= Logger.getLogger(ActionPerformer.class);

    private MoneyManager moneyManager;


    public ActionPerformer(MoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    private int findNumberOfOpenOrders(String symbol){
        return moneyManager.findNumberOfOpenOrders(symbol);
    }


    public String stringifyOrder(OpenFinDeskOrder openFinDeskOrder) {

        String orderType="n";
        orderType = openFinDeskOrder.getOrderType();



        if (moneyManager.findNumberOfOpenOrders(openFinDeskOrder.getSymbol())<moneyManager.getNumberOfAllowedOrders()){


            if (orderType.equals("openBuy")) {
                orderType = "ORDER_TYPE_BUY";
            } else if (orderType.equals("openSell")) {
                orderType = "ORDER_TYPE_SELL";
            } else if (orderType.equals("noOrder")) {

            }

        }else{
            logger.debug("more than number of allowed orders");
        }


        String order="noOrder";

                if(orderType.equals("modify")) {
                    order = "modifyPosition" + "," + openFinDeskOrder.getSymbol() + "," + orderType + "," + "instant" + ","
                            + openFinDeskOrder.getTakeProfit() + ","
                            + openFinDeskOrder.getStopLoss() + "," +
                            openFinDeskOrder.getVolume() + "," + openFinDeskOrder.getOpenFinDeskOrderNumber();
                }else if((orderType.equals("ORDER_TYPE_BUY"))||(orderType.equals("ORDER_TYPE_SELL"))) {
                    if (findNumberOfOpenOrders(openFinDeskOrder.getSymbol()) < moneyManager.getNumberOfAllowedOrders()) {
                        order = "order" + "," + openFinDeskOrder.getSymbol() + "," + orderType + "," + "instant" + ","
                                + openFinDeskOrder.getTakeProfit() + ","
                                + openFinDeskOrder.getStopLoss() + "," +
                                openFinDeskOrder.getVolume();
                    } else {
                        System.out.println("not allowed by openFinDesk.reason:more than number of allowed orders.");
                    }
                }else{
                    logger.debug("no suggestion from com.farshad.algotrading.AlgoTrader,position:"+openFinDeskOrder.getPosition());
                }

        return order;
    }


    public void setNumberOfAllowedOrders(int numberOfAllowedOrders) {
        this.moneyManager.setNumberOfAllowedOrders(numberOfAllowedOrders);
    }

    public void setNumberOfOpenOrders(int numberOfOpenOrders) {
        this.moneyManager.setNumberOfOpenOrders(numberOfOpenOrders);
     }
}
