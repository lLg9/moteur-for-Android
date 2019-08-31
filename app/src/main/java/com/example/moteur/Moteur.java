package com.example.moteur;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Moteur implements iMoteur {

    //state fields
    private int remaining;
    private Boolean waitingForAnswer;

    //path
    private final String FOLDER_PATH;


    //data & rank fields
    private final int PACK_NUM;
    private final int NUM_LOAD;

    private ArrayList<WordData> wDataArray;
    private WordRanks wRanks;

    private LinkedList<Integer> wPracticeList;
    private String wMissedString = "";


    //constructor
    public Moteur(String folderPath, int packageNum, int numToLoad) {
        this.FOLDER_PATH = folderPath;
        //this.FOLDER_PATH = Environment.getExternalStorageDirectory() + File.separator + "__moteur__";
        this.PACK_NUM = packageNum;
        this.NUM_LOAD = numToLoad;
        this.waitingForAnswer = false;

        Parser pars = new Parser(FOLDER_PATH);
        wDataArray = pars.parseData();
        wRanks = pars.parseRanks();

        loadPracticeList();
        this.remaining = wPracticeList.size();
    }

    //public methods

    @Override
    public WordStruct getNext() {
        WordStruct ws = new WordStruct();

        if (remaining > 0 && !waitingForAnswer){
            ws.remain = remaining;
            ws.word = wDataArray.get(wPracticeList.get(0)).word.trim();
            ws.transl = wDataArray.get(wPracticeList.get(0)).transl.trim();
            ws.example = wDataArray.get(wPracticeList.get(0)).example.trim();

            remaining--;
            waitingForAnswer = true;
            return ws;
        }

        else if (remaining == 0){
            ws.remain = 888;
            ws.word = "good: " + Integer.toString(wRanks.good.size());
            ws.transl = "medium: " + Integer.toString(wRanks.medium.size());
            ws.example = "poor: " + Integer.toString(wRanks.poor.size());

            Parser pars = new Parser(FOLDER_PATH);
            pars.exportData(wDataArray);
            pars.exportRanks(wRanks);

            return ws;
        }
        else {
            ws.remain = 777;
            ws.word = "wroooooooooong";
            if (remaining < 0)
                ws.transl = "less_than_0";
            ws.transl = "wroooooooong";
            if (waitingForAnswer)
                ws.example = "waiting_for_answer";
            ws.example = "wrooooooooooooong";

            return ws;
        }

    }

    @Override
    public void giveFeedback(Boolean succ) {

        //TBD: add missed ones to wMmissedList
        if (remaining >= 0 && waitingForAnswer) {
            int current = wPracticeList.removeFirst();

            if (!succ){
                WordStruct ws = new WordStruct();
                ws.word = wDataArray.get(current).word;
                ws.transl = wDataArray.get(current).transl;
                ws.example = wDataArray.get(current).example;
                wMissedString +=
                                "w: " + ws.word + "\n" +
                                "t: " + ws.transl + "\n" +
                                "e: " + ws.example + "\n" +
                                "----------\n";
            }

            int newrank = wDataArray.get(current).updateRank(succ);

            if ((newrank <= 6 && !wDataArray.get(current).last3Q() && !wDataArray.get(current).last5Q()) || !wDataArray.get(current).lastQ()) {
                wRanks.poor.add(current);
            } else if ((newrank > 6 && newrank <= 8 && !wDataArray.get(current).last5Q()) || (newrank <= 6 && wDataArray.get(current).last3Q() && !wDataArray.get(current).last5Q())) {
                wRanks.medium.add(current);

            } else if (newrank >= 9 || wDataArray.get(current).last5Q()) {
                wRanks.good.add(current);
            }

            waitingForAnswer = false;
        }

    }

    @Override
    public String recapMissed() {
        if (remaining == 0){
            return wMissedString;
        }
        else{
            return "ERR: recapMissed() called before session end";
        }
    }

    //private methods

    private void loadPracticeList(){
        if (NUM_LOAD==20){
            load20();
        }
        else if (NUM_LOAD==60){
            loadAll();
        }
        else if (NUM_LOAD==8){
            loadPoorMedium();
        }
    }

    private void load20(){
        wPracticeList = new LinkedList<>();
        int counter = 20;

        for (int i = 0; i < 12; i++){
            if (wRanks.poor.size() != 0){
                wPracticeList.add(wRanks.poor.removeFirst());
                counter--;
            }
            else break;
        }
        for (int i = 0; i < 6; i++){
            if (wRanks.medium.size() != 0){
                wPracticeList.add(wRanks.medium.removeFirst());
                counter--;
            }
            else break;
        }
        while (counter != 0 && wRanks.good.size() != 0){
            wPracticeList.add(wRanks.good.removeFirst());
            counter--;
        }

        Collections.shuffle(wPracticeList);
    }

    private void loadAll(){
        wPracticeList = new LinkedList<>();
        for (int i : wRanks.poor)
            wPracticeList.add(i);
        for (int i : wRanks.medium)
            wPracticeList.add(i);
        for (int i : wRanks.good)
            wPracticeList.add(i);
        wRanks.good.clear();
        wRanks.medium.clear();
        wRanks.poor.clear();
        Collections.shuffle(wPracticeList);
    }

    private void loadPoorMedium(){
        wPracticeList = new LinkedList<>();
        for (int i : wRanks.poor)
            wPracticeList.add(i);
        for (int i : wRanks.medium)
            wPracticeList.add(i);
        wRanks.medium.clear();
        wRanks.poor.clear();
        Collections.shuffle(wPracticeList);
    }
}
