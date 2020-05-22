package com.farshad.algotrading.core.sockets;

import com.farshad.algotrading.OpenFinDesklogLevels.LiveTradingLogLevel;
import com.google.gson.Gson;
import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.OHLCData;
import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.OpenFinDeskMessage;
import org.apache.log4j.Logger;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketUtil {

    final static Logger logger= Logger.getLogger(SocketUtil.class);


    private Socket client;

    private ServerSocket ss;

    private OpenFinDeskMessage openFinDeskMessage=new OpenFinDeskMessage();

    private OHLCData ohlcData=new OHLCData();

    private List<OHLCData> ohlcDataList=new ArrayList<>();

    PrintWriter out;

    public SocketUtil(ServerSocket ss,Socket client)
    {
        this.ss = ss;
        this.client=client;

    }

    public void setOpenFinDeskMessage(String context ,String payload) {
        this.openFinDeskMessage.setContext(context);
        this.openFinDeskMessage.setPayload(payload);
    }

    public OpenFinDeskMessage sendMessage(String message) throws IOException, InterruptedException {
        openFinDeskMessage=new OpenFinDeskMessage();
        int numberOfLoops=2;
        if (message.split(",")[0].equals("fetchCandles")){
            numberOfLoops= Integer.parseInt(message.split(",")[4])+1;
        }else{
            numberOfLoops=2;
        }


        outerloop:
        for (int i=0;i<numberOfLoops;i++) {
            client = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            String clientAddress = client.getInetAddress().getHostAddress();
            logger.log(LiveTradingLogLevel.SOCKETUTIL,"\r\nNew connection from i="+i+" is:" + clientAddress+" using SocketUtil");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            Gson gson = new Gson();
            String data = "";
            while ((data = in.readLine()) != null) {
                Thread.sleep(100);
                String delimiter = "\\}";
                String[] sepString = data.split(delimiter);
                String clean = sepString[0] + "}";
                logger.debug("clean="+clean);
                if (clean!=null) {
                    openFinDeskMessage = gson.fromJson(clean, OpenFinDeskMessage.class);
                    logger.info("openFinDeskMessage.getContext() for message " + message + " is:" + openFinDeskMessage.getContext());
                    logger.info("openFinDeskMessage.getPayload() for message " + message + " is:" + openFinDeskMessage.getPayload());

                    if(!openFinDeskMessage.getContext().equals("noContext")){
                        break outerloop;
                    }else{
                        ohlcData = gson.fromJson(clean, OHLCData.class);
                        ohlcDataList.add(ohlcData);
                    }
                }
            }
            logger.debug("sending to client from SocketUtil message:"+message);

            if (!(openFinDeskMessage.getContext().equals("noContext"))
                    || message.split(",")[0].equals("e2")||
                    message.split(",")[0].equals("e1")
            ){
                logger.warn("message has already been sent!");
                break outerloop;
            }


            out = new PrintWriter(client.getOutputStream(), true);
            out.println(message);




            client.close();
        }
        client.close();
        return openFinDeskMessage;

    }


    public List<OHLCData> getOhlcDataList() {
        return ohlcDataList;
    }


}
