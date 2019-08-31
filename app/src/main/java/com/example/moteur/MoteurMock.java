package com.example.moteur;

import java.util.ArrayList;

public class MoteurMock implements iMoteur {

    //mock class to be able to develop a basic, fully functioning version of the app

    private static String word1 = "WORD1";
    private static String word2 = "WORD2";
    private static String word3 = "WORD3";

    private static String transl1 = "TRANSL1";
    private static String transl2 = "TRANSL2";
    private static String transl3 = "TRANSL3";

    private static String example1 = "EXAMPLE1";
    private static String example2 = "EXAMPLE2";
    private static String example3 = "EXAMPLE3";

    private int turn = 1;


    public MoteurMock(int packageNum, int numToLoad) {

    }

    @Override
    public WordStruct getNext() {
        WordStruct ws = new WordStruct();
        if (turn == 1){
            ws.remain = 3;
            ws.word = word1;
            ws.transl = transl1;
            ws.example = example1;
            return ws;
        }
        else if (turn == 2){
            ws.remain = 2;
            ws.word = word2;
            ws.transl = transl2;
            ws.example = example2;
            return ws;
        }
        else if (turn == 3){
            ws.remain = 1;
            ws.word = word3;
            ws.transl = transl3;
            ws.example = example3;
            return ws;
        }
        else return ws;
    }

    @Override
    public void giveFeedback(Boolean succ) {
        turn = (turn % 3) + 1;
    }

    @Override
    public String recapMissed() {
        return null;
    }
}
