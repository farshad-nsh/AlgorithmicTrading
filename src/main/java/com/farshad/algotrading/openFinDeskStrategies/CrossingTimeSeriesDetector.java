package com.farshad.algotrading.openFinDeskStrategies;


import org.apache.log4j.Logger;

public class CrossingTimeSeriesDetector {

    final static Logger logger= Logger.getLogger(CrossingTimeSeriesDetector.class);

    private double[] timeSeries1;
    private double[] timeSeries2;

    private int overCount;

    private double slopeAtLatestIndex1;

    private double slopeAtLatestIndex2;

    private int horizen;

    private int lengthOfTimeSeries;

    private String status; //crossUp, crossDown , over , under

    public CrossingTimeSeriesDetector(double[] timeSeries1, double[] timeSeries2) {
        lengthOfTimeSeries = timeSeries1.length;
        this.timeSeries1 = new double[lengthOfTimeSeries];
        this.timeSeries2 = new double[lengthOfTimeSeries];

        for (int i = 0; i < lengthOfTimeSeries; i++) {
            this.timeSeries1[i] = timeSeries1[i];
            this.timeSeries2[i] = timeSeries2[i];
        }


        this.status = "unknown";
        this.slopeAtLatestIndex1 = 0;
        this.slopeAtLatestIndex2 = 0;
    }

    public String detect(int horizen) {

        this.horizen = horizen;
        overCount = 0;

        int fromBar;
        int toBar;

        double timeSeries1ValueAtIndex0 = timeSeries1[0];
        double timeSeries2ValueAtIndex0 = timeSeries2[0];

        double timeSeries1Value;
        double timeSeries2Value;

        fromBar = 0;
        toBar = horizen - 1;

        logger.info("fromBar=" + fromBar + ",toBar=" + toBar);

        for (int i = 0; i <= toBar; i++) {
            timeSeries1Value = timeSeries1[i];
            timeSeries2Value = timeSeries2[i];
            logger.debug("***timeSeries1[" + i + "]=" + timeSeries1Value);
            logger.debug("***timeSeries2[" + i + "]=" + timeSeries2Value);
        }


        for (int i = 0; i <= toBar; i++) {

            timeSeries1Value = timeSeries1[i];
            timeSeries2Value = timeSeries2[i];


            if (timeSeries1ValueAtIndex0 < timeSeries2ValueAtIndex0) {
                //either under or crossUp
                if (timeSeries1Value < timeSeries2Value) {
                    overCount++;
                    if (overCount == lengthOfTimeSeries) {
                        status = "under";
                        break;
                    }
                }else if (timeSeries1Value > timeSeries2Value) {
                    if (overCount < lengthOfTimeSeries) {
                        status = "crossUp";
                        break;
                    }
                }
            }else if(timeSeries1ValueAtIndex0 > timeSeries2ValueAtIndex0) {
                    //either over or crossDown
                    if (timeSeries1Value > timeSeries2Value) {
                        overCount++;
                        if (overCount == lengthOfTimeSeries) {
                            status = "over";
                            break;
                        }
                    }else if (timeSeries1Value < timeSeries2Value) {
                        if (overCount < lengthOfTimeSeries) {
                            status = "crossDown";
                            break;
                        }
                    }
            }else{
                    status = "crossingAtThisMoment";
                    logger.debug("timeSeries1ValueAtIndex0=" + timeSeries1ValueAtIndex0);
                    logger.debug("timeSeries2ValueAtIndex0=" + timeSeries2ValueAtIndex0);
                    break;
            }

        }


     return status;
    }


        public int getCrossPoint () {
            return overCount;
        }

        public double getSlopeAtLatestIndex1 () {
            slopeAtLatestIndex1 = timeSeries1[lengthOfTimeSeries - 1] - timeSeries1[lengthOfTimeSeries - 2];
            return slopeAtLatestIndex1;
        }

        public double getSlopeAtLatestIndex2 () {
            slopeAtLatestIndex2 = timeSeries2[lengthOfTimeSeries - 1] - timeSeries2[lengthOfTimeSeries - 2];
            return slopeAtLatestIndex2;
        }


}
