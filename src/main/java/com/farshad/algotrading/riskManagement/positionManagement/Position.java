package com.farshad.algotrading.riskManagement.positionManagement;

public class Position {


    private double price;

    private int ticketNumber;



    public Position() {
        ticketNumber=999999;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
