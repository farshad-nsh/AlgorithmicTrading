package com.farshad.algotrading.core;

import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class CurrentState {

    final static Logger logger= Logger.getLogger(CurrentState.class);

    private int ticketNumber;
    private String position;
    private double price;
    private double openingPrice;
    private String volume;
    private String symbol;
    private double takeProfit;
    private double stopLoss;
    private List<OpenFinDeskOrder> featureVector;
    private double reward;
    private String currentStateInString;

    public CurrentState() {
        ticketNumber=2222222;
        position="range";
        price=0;
        volume="0.01";
        symbol="notSet";
        reward=0;
        currentStateInString="";
        featureVector=new ArrayList<>();
    }

    public List<OpenFinDeskOrder> getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(List<OpenFinDeskOrder> featureVector) {
        this.featureVector = featureVector;
    }

    public String getCurrentStateInString() {
        return currentStateInString;
    }

    public void setCurrentStateInString(String currentStateInString) {
        this.currentStateInString = currentStateInString;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }


    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(double openingPrice) {
        this.openingPrice = openingPrice;
    }
}
