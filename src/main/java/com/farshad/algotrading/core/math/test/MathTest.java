package com.farshad.algotrading.core.math.test;


import com.farshad.algotrading.core.math.AlgoRegression;

import java.util.ArrayList;
import java.util.List;

public class MathTest {
    public static void main(String[] args) {
        AlgoRegression algoRegression=new AlgoRegression();
       // double[] gbpusd=new double[]{1,3,5};
       // double[] eurusd=new double[]{2,6.6,9};
        List<Double> gbpusdList=new ArrayList<>();
        List<Double> eurusdList=new ArrayList<>();
        gbpusdList.add(1D);gbpusdList.add(3D);gbpusdList.add(5D);
        eurusdList.add(2D);eurusdList.add(6.6D);eurusdList.add(9D);
        algoRegression.comparePairs(gbpusdList,eurusdList);
    }
}
