package com.farshad.algotrading.core.trendRangeAndWave;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WaveGenerator {

    final static Logger logger= Logger.getLogger(WaveGenerator.class);

    private List<Trend> trendList;

    private int startingIndex;

    public WaveGenerator(List<Trend> trendList,int startingIndex) {
        this.trendList=trendList;
        this.startingIndex=startingIndex;
        logger.info("size of trendList="+trendList.size());
    }

    public List<Wave> generate(){
        List<Wave> waveList=new ArrayList<>();
        for(int i=startingIndex;i<trendList.size()-1;i=i+2){
            Wave wave=new Wave(trendList.get(i),trendList.get(i+1));
            waveList.add(wave);
        }
        return waveList;
    }
}
