package com.farshad.algotrading;

import com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.csv.CSVReader;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class TestCSVReader {
    final static Logger logger= Logger.getLogger(TestCSVReader.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        String directory="src/main/java/files";
        new File(directory).mkdirs();
        File idea=new File(directory,"GBPUSD"+"-"+"M1"+".csv");
        logger.info("idea.getPath()="+idea.getPath());
        String fileName="/"+idea.getPath();
        CSVReader csvReader=new CSVReader();
        csvReader.setFileName(fileName);
        logger.info("csvReader.getLines() for GBPUSD="+csvReader.getLines());


        idea=new File(directory,"EURUSD"+"-"+"M1"+".csv");
        logger.info("idea.getPath() for EURUSD="+idea.getPath());
         fileName="/"+idea.getPath();
        CSVReader csvReader2=new CSVReader();
        csvReader.setFileName(fileName);
        logger.info("csvReader.getLines() for EURUSD="+csvReader2.getLines());

    }
}
