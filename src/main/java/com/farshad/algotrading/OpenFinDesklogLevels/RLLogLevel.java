package com.farshad.algotrading.OpenFinDesklogLevels;


import org.apache.log4j.Level;

public class RLLogLevel extends Level {

    public static final int REWARD_INT = INFO_INT -10;
    public static final int CURRENTSTATE_INT = INFO_INT -11;
    public static final int QAction_INT = INFO_INT -12;
    public static final int MAXForQACTION_INT = INFO_INT -13;


    public static final Level REWARD = new RLLogLevel(REWARD_INT, "REWARD", 1);
    public static final Level CURRENTSTATE = new RLLogLevel(CURRENTSTATE_INT, "CURRENTSTATE", 2);
    public static final Level QAction = new RLLogLevel(QAction_INT, "QACTION", 3);
    public static final Level MAXForQACTION = new RLLogLevel(MAXForQACTION_INT, "MaxForQACTION", 4);




    public RLLogLevel(int level, String name, int value) {
        super(level,name, value);
    }
}
