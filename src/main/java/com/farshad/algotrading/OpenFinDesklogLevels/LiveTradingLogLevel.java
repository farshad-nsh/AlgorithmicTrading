package com.farshad.algotrading.OpenFinDesklogLevels;


import org.apache.log4j.Level;



public class LiveTradingLogLevel  extends Level {

    public static final int PROCESS_INT = DEBUG_INT -10;
    public static final int SOCKETUTIL_INT = INFO_INT -11;
    public static final int SIGNALS_INT = WARN_INT +12;


    public static final Level PROCESS = new LiveTradingLogLevel(PROCESS_INT, "PROCESS", 1);
    public static final Level SOCKETUTIL = new LiveTradingLogLevel(SOCKETUTIL_INT, "SOCKETUTIL", 2);
    public static final Level SIGNALS = new LiveTradingLogLevel(SIGNALS_INT, "SIGNALS", 3);



    public LiveTradingLogLevel(int level, String name, int value) {
        super(level,name, value);
    }



}
