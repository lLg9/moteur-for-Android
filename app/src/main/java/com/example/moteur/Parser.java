package com.example.moteur;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Parser {

    private final String PATH;

    //constructor
    public Parser(String dirPath){
        this.PATH =  dirPath;
    }

    //public methods

    public ArrayList<WordData> parseData(){
        if (isExternalStorageWritable()){
            ArrayList<WordData> result = new ArrayList<>();

            try{
                File inf = new File(PATH, "#data.fgnt");
                FileInputStream fis = new FileInputStream(inf);

                if (fis != null){
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader buff = new BufferedReader(isr);

                    String line;
                    while((line = buff.readLine()) != null){

                        String [] strArr = line.split(";");
                        //for (String e : strArr)
                            //Log.d("gggggggggg", "|" + e + "|");
                        //Log.d("gggggggggg", "----------");
                        WordData wd = new WordData(
                                Integer.parseInt(strArr[0].trim()),
                                Integer.parseInt(strArr[1].trim()),
                                parseIntArray(strArr[2]),
                                strArr[3],
                                strArr[4],
                                strArr[5]
                        );
                        result.add(wd);
                    }
                    fis.close();
                }
                return result;

            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
        else return null;

    }

    public WordRanks parseRanks(){

        if (isExternalStorageWritable()){
            WordRanks result = new WordRanks();

            try{
                File inf = new File(PATH, "#ranks.fgnt");
                FileInputStream fis = new FileInputStream(inf);

                if (fis != null){

                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader buff = new BufferedReader(isr);

                    String line;
                    for(int i=0; i < 3; i++){
                        LinkedList<Integer> temp = new LinkedList<>();
                        line = buff.readLine();
                        line = line.trim();

                        if (!line.equals("")){
                            String [] strArr = line.split(" ");
                            for (String e : strArr){
                                temp.add(Integer.parseInt(e.trim()));
                            }
                        }

                        switch (i){
                            case 0:
                                result.good = temp;
                                break;
                            case 1:
                                result.medium = temp;
                                break;
                            case 2:
                                result.poor = temp;
                                break;
                        }
                    }
                    fis.close();
                }
                return result;

            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
        else return null;

    }


    public Boolean exportData(ArrayList<WordData> data){
        if (isExternalStorageWritable()){

            try{
                File of = new File(PATH, "#data.fgnt");

                if(of.exists()){
                    of.delete();
                    try {
                        of.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                FileOutputStream fos = new FileOutputStream(of);

                for (WordData d : data){
                    StringBuilder s = new StringBuilder();
                    s
                    .append(d.key).append(" ; ")
                    .append(d.week).append(" ; ")
                    .append(exportIntArray(d.hits)).append(" ;")
                    .append(d.word).append(";")
                    .append(d.transl).append(";")
                    .append(d.example).append("\n");
                    fos.write(s.toString().getBytes());
                }
                fos.close();

                return true;

            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
        else return false;
    }

    public Boolean exportRanks(WordRanks ranks){
        if (isExternalStorageWritable()){

            try{
                File of = new File(PATH, "#ranks.fgnt");

                if(of.exists()){
                    of.delete();
                    try {
                        of.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                FileOutputStream fops = new FileOutputStream(of);

                for (Integer e : ranks.good){
                    fops.write(e.toString().getBytes());
                    fops.write(" ".getBytes());
                }
                fops.write("\n".getBytes());

                for (Integer e : ranks.medium){
                    fops.write(e.toString().getBytes());
                    fops.write(" ".getBytes());
                }
                fops.write("\n".getBytes());

                for (Integer e : ranks.poor){
                    fops.write(e.toString().getBytes());
                    fops.write(" ".getBytes());
                }
                fops.write("\n".getBytes());

                fops.close();

                return true;
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
        else return false;

    }



    //private methods

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
            return true;
        return false;
    }

    private int[] parseIntArray(String inputString){
        String[] elements =  inputString.trim().replace("[", "").replace("]", "").split(",");
        int[] result = new int[elements.length];
        for (int i = 0; i<elements.length; i++){
            result[i] = Integer.parseInt(elements[i].trim());
        }
        return result;
    }

    private String exportIntArray(int[] inputArray){
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i<inputArray.length-1; i++){
            result.append(Integer.toString(inputArray[i]));
            result.append(", ");
        }
        if (inputArray.length != 0){
            result.append(inputArray[inputArray.length-1]);
        }
        result.append("]");
        return result.toString();
    }

}
