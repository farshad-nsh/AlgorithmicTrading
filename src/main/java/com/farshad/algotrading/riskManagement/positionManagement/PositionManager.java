package com.farshad.algotrading.riskManagement.positionManagement;


import com.farshad.algotrading.OpenFinDesklogLevels.LiveTradingLogLevel;
import com.farshad.algotrading.core.openFinDeskOrder.OpenFinDeskOrder;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PositionManager {

    final static Logger logger= Logger.getLogger(PositionManager.class);


    private double positionGap; //gap between different positions

    private boolean goodToGo;

    boolean found=false;

    double difference;

    private List<Position> listOfAllPositions = new ArrayList<>();

    private Position position = new Position();

    public void setPositionGap(double positionGap) {
        this.difference=0;
        this.positionGap = positionGap;
    }



    public OpenFinDeskOrder filterOrder(OpenFinDeskOrder openFinDeskOrder) {

        logger.debug("listOfAllPositions.size()="+listOfAllPositions.size());
        goodToGo = true;

        for (int i = 0; i < listOfAllPositions.size(); i++) {
            difference=Math.abs(listOfAllPositions.get(i).getPrice() - openFinDeskOrder.getPrice());
            logger.info("difference="+difference);

            if (difference> positionGap) {
                     goodToGo = true;
            } else {
                goodToGo = false;
                logger.log(LiveTradingLogLevel.SIGNALS,"sorry,positionManager in openFinDesk Hedge Fund refused this order due to gap" +
                        " policies!");
                break;
            }
        }

        if (!goodToGo) {
            openFinDeskOrder.setOrderType("noOrder");
        }

        return openFinDeskOrder;
    }




    public void updatePositions(String payload) {
        listOfAllPositions.clear();
        if(!payload.equals("")) {
            if(!payload.equals("noPayload")){
            String[] ticketsAndOpenPrices = payload.split(",");
            found = false;
            for (int i = 0; i < ticketsAndOpenPrices.length; i++) {
                if ((i % 2) == 0) {
                    logger.info("tickets[" + i + "]=" + ticketsAndOpenPrices[i]);
                    position.setTicketNumber(Integer.parseInt(ticketsAndOpenPrices[i]));
                } else {
                    logger.info("openPrices[" + i + "]=" + ticketsAndOpenPrices[i]);
                    position.setPrice(Double.valueOf(ticketsAndOpenPrices[i]));
                }

                if (i % 2 > 0) {
                    listOfAllPositions.add(position);
                }
            }
        }
      }
     }




    public int getNumberOfOpenPositions(){
         return listOfAllPositions.size();
    }


}

