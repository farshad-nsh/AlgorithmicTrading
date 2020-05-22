package com.farshad.algotrading.backtesting.ExpectationMaximizationAlgorithm;

import org.apache.log4j.Logger;

public class ExpectationMaximization {

    final static Logger logger= Logger.getLogger(ExpectationMaximization.class);


    private String probabilityType;

    private double meanOfb;
    private double meanOfa;

    private double varianceOfb;
    private double varianceOfa;


    private double[] probabilityOfxConditionedOnb;

    private double[] probabilityOfbConditionedOnx;

    private double[] probabilityOfaConditionedOnx;

    private double[] probabilityOfxConditionedOna;

    private int batchSizeOfx;



    private double probabilityOfb;

    private double probabilityOfa;

    public ExpectationMaximization(String probabilityType, int batchSize) {
        batchSizeOfx=batchSize;
        this.probabilityType=probabilityType;
        probabilityOfxConditionedOnb=new double[batchSizeOfx];
        probabilityOfxConditionedOna=new double[batchSizeOfx];
        probabilityOfbConditionedOnx=new double[batchSizeOfx];
        probabilityOfaConditionedOnx=new double[batchSizeOfx];
    }

    public void initializeMeanAndVariance() {
        probabilityOfb=0.5;
        probabilityOfa=1-probabilityOfb;
        meanOfb=1.2;
        meanOfa=1.4;
        varianceOfb=0.1;
        varianceOfa=0.1;
    }

    public void findMeanAndVariance(double[] batchedx) {
         expectationStep(batchedx);
        maximizationStep(batchedx);
    }


    private void expectationStep(double[] batchedx) {
        for(int i=0;i<batchSizeOfx;i++){
            probabilityOfxConditionedOna[i]= (1 / Math.sqrt(2*Math.PI*varianceOfa)) * Math.exp(-Math.pow(batchedx[i] - meanOfa, 2) / (2 * varianceOfa));
            probabilityOfxConditionedOnb[i] = (1 / Math.sqrt(2*Math.PI*varianceOfb)) * Math.exp(-Math.pow(batchedx[i] - meanOfb, 2) / (2 * varianceOfb));

            probabilityOfbConditionedOnx[i] = probabilityOfxConditionedOnb[i]*probabilityOfb/(probabilityOfxConditionedOnb[i]*probabilityOfb+probabilityOfxConditionedOna[i]*probabilityOfa);
            probabilityOfaConditionedOnx[i]= 1-probabilityOfbConditionedOnx[i];
        }
    }

    private void maximizationStep(double[] batchedx) {
        double weightedSumofb=0;
        double weightedSumofa=0;
        double squaredWeightedSumOfb=0;
        double squaredWeightedSumOfa=0;
        double sumOfb=0;
        double sumOfa=0;

        for(int i=0;i<batchSizeOfx;i++) {
            weightedSumofb=weightedSumofb+probabilityOfbConditionedOnx[i]*batchedx[i];
            weightedSumofa=weightedSumofa+probabilityOfaConditionedOnx[i]*batchedx[i];
            sumOfb=sumOfb+probabilityOfbConditionedOnx[i];
            sumOfa=sumOfa+probabilityOfaConditionedOnx[i];
            squaredWeightedSumOfb=squaredWeightedSumOfb+probabilityOfbConditionedOnx[i]*Math.pow((batchedx[i]-meanOfb),2);
            squaredWeightedSumOfa=squaredWeightedSumOfa+probabilityOfaConditionedOnx[i]*Math.pow((batchedx[i]-meanOfa),2);

        }

            meanOfb=weightedSumofb/sumOfb;
            varianceOfb=squaredWeightedSumOfb/sumOfb;
            meanOfa=weightedSumofa/sumOfa;
            varianceOfa=squaredWeightedSumOfa/sumOfa;
        logger.info("meanOfb="+meanOfb);
        logger.info("varianceOfb="+varianceOfb);
        logger.info("meanOfa="+meanOfa);
        logger.info("varianceOfa="+varianceOfa);
    }


    public double getMeanOfb() {
        return meanOfb;
    }

    public double getVarianceOfb() {
        return varianceOfb;
    }


    public double getMeanOfa() {
        return meanOfa;
    }

    public double getVarianceOfa() {
        return varianceOfa;
    }
}
