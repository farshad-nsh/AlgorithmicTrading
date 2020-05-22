package com.farshad.algotrading.backtesting;

import com.farshad.algotrading.core.sockets.SocketUtil;
import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.OHLCData;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Farshad Noravesh
 */
public abstract class BackTest {

        private ServerSocket ss;
        private SocketUtil socketUtil;
        private List<OHLCData> ohlcDataList;

        protected BackTest() throws IOException {
              //  ss=new ServerSocket(5555);
              //  socketUtil=new SocketUtil(ss,new Socket());
                 ohlcDataList=new ArrayList<>();

        }

        public abstract void begin() throws IOException;

        public List<OHLCData> fetchDataUsingMetaTrader5(String symbolName,String timeFrame,int fromCandle,int numberOfCandles) {
                ohlcDataList.clear();
                try {
                         String message="";
                                message="fetchCandles"+","+symbolName+","+timeFrame+","+fromCandle+","+numberOfCandles;
                                socketUtil.sendMessage(message);
                        ohlcDataList=socketUtil.getOhlcDataList();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }

                return ohlcDataList;
        }

        public List<OHLCData> getOhlcDataList() {
                return ohlcDataList;
        }
}
