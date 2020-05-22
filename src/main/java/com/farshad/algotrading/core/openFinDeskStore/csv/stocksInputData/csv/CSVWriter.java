package com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.csv;


import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVWriter {

    private static Logger LOGGER= Logger.getLogger(CSVWriter.class);
    private FileWriter csvWriter;
    private File file;


    public void setFile(File file){
        this.file=file;
        if (file.exists()){
            LOGGER.info("file exists!");
            try {
                csvWriter=new FileWriter(file, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeHeader(List<String> row) throws IOException {
            //empty the file
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
            csvWriter = new FileWriter(file,true);
            csvWriter.append(String.join(",",  row));
            csvWriter.append("\n");
            csvWriter.flush();
    }

    public void writeRow(List<String> row) throws IOException {
        csvWriter.append(String.join(",", row));
        csvWriter.append("\n");
        csvWriter.flush();
    }



    public void writeOnTop(List<String> row) throws IOException {

        //Read old Data
        CSVReader csvReader=new CSVReader();
        csvReader.setFileName("/csv/"+file.getName());
        String[] strings;
        List<List<String>> listList=new ArrayList<>();
        List<String> stringList=new ArrayList<>();
        int numberOfLines=csvReader.getLines();

            for (int i = 0; i < numberOfLines; i++) {
                strings = csvReader.readRow(numberOfLines-i);
                stringList = Arrays.asList(strings);
                listList.add(stringList);
            }
          csvReader.closeReader();
        //empty the file
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();

        writeRow(row);

        //append old data
        for(int i=0;i<numberOfLines;i++){
           // csvWriter = new FileWriter(file,true);
            csvWriter.append(String.join(",", listList.get(numberOfLines-i-1)));
            csvWriter.append("\n");
        }
        csvWriter.flush();


    }


   public void flush() throws IOException {
        csvWriter.flush();
   }

    public void closeStream() throws IOException {
        csvWriter.close();
    }

}

