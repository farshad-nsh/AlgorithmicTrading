package com.farshad.algotrading.riskManagement.moneymanagement;


import org.apache.log4j.Logger;

public class MoneyManager {

    final static Logger logger= Logger.getLogger(MoneyManager.class);


    private int numberOfAllowedOrders;

    private int numberOfOpenOrders;


    public MoneyManager() {
     }


    public int getNumberOfAllowedOrders() {
        return numberOfAllowedOrders;
    }

    public int findNumberOfOpenOrders(String symbol){
        return numberOfOpenOrders;
    }



    public void setNumberOfAllowedOrders(int numberOfAllowedOrders) {
        this.numberOfAllowedOrders = numberOfAllowedOrders;
    }

    public void setNumberOfOpenOrders(int numberOfOpenOrders) {
        this.numberOfOpenOrders = numberOfOpenOrders;
        logger.info("numberOfOpenOrders updated to:"+numberOfOpenOrders);
    }



}
