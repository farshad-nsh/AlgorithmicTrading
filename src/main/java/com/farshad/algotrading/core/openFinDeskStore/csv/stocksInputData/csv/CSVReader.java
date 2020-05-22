package com.farshad.algotrading.core.openFinDeskStore.csv.stocksInputData.csv;


import java.io.*;



public class CSVReader {

    private BufferedReader csvReader;
    private BufferedReader csvReader2;

    private int lines;

    private String absolutePath;

     public void setFileName(String fileName){
         String filePath = new File("").getAbsolutePath();
         absolutePath=filePath+fileName;
         try {
             csvReader = new BufferedReader(new FileReader(absolutePath));
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
     }

    public int getLines() throws IOException {

        try {
            csvReader2 = new BufferedReader(new FileReader(absolutePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lines = 0;
        while (csvReader2.readLine() != null) {
            lines++;
        }

        return lines;
    }

    public String[] readRow(int lineNumber) throws IOException {
        csvReader = new BufferedReader(new FileReader(absolutePath));

        String row=null;
        for(int i=0;i<lineNumber;i++){
            row = csvReader.readLine();
        }

        String[] data=null;
        if(row!=null){
            data = row.split(",");
        }else{
            System.out.println("data is null in csvreader");
        }

        csvReader.close();
        return data;
    }

    public String[] readRow() throws IOException {

        String row1=csvReader.readLine();
        String[] data = row1.split(",");
        for (String d:data
        ) {
          //  System.out.println("d="+d);
        }

        return data;
    }

    public void closeReader() throws IOException {
        csvReader.close();

    }


}
