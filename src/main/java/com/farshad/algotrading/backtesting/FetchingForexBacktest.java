package com.farshad.algotrading.backtesting;

import com.farshad.algotrading.core.Symbol;
import com.farshad.algotrading.core.openFinDeskStore.influxdb.InfluxDB;
import org.apache.log4j.Logger;
import org.ta4j.core.TimeSeries;

import java.io.IOException;
import java.net.ServerSocket;
/**
 * @author Farshad Noravesh
 */
public class FetchingForexBacktest extends BackTest {
    final static Logger logger= Logger.getLogger(FetchingForexBacktest.class);

    private ServerSocket ss;

    public FetchingForexBacktest() throws IOException {
    }

    @Override
    public void begin() throws IOException {
        ss = new ServerSocket(5555);

        InfluxDB influxDB=new InfluxDB();
        Symbol<InfluxDB> eurusdSymbol = new Symbol<InfluxDB>(influxDB,"EURUSD", new String[]{"PERIOD_H1","PERIOD_D1"}, "forex",ss);
        Symbol<InfluxDB> audusdSymbol = new Symbol<InfluxDB>(influxDB,"AUDUSD", new String[]{"PERIOD_H1","PERIOD_D1"}, "forex",ss);
        Symbol<InfluxDB> gbpusdSymbol = new Symbol<InfluxDB>(influxDB,"GBPUSD", new String[]{"PERIOD_H1","PERIOD_D1"}, "forex",ss);
        Symbol<InfluxDB> euraudSymbol = new Symbol<InfluxDB>(influxDB,"EURAUD", new String[]{"PERIOD_H1","PERIOD_D1"}, "forex",ss);


      //  eurusdSymbol.fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(100);
       // audusdSymbol.fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(100);
      //  gbpusdSymbol.fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(100);
        euraudSymbol.fetchCandlesFromMT5AndWriteItToOpenFinDeskStore(100);

     //   TimeSeries series=audusdSymbol.getSeries()[0];

    }
}
