package com.example.moteur;

public class WordData {

    //fields
    public final int key;
    public final int week;
    public int hits[];
    public final String word;
    public final String transl;
    public final String example;

    //constructor
    public WordData(int key, int week, int[] hits, String word, String transl, String example){
        this.key = key;
        this.week = week;
        this.hits = hits;
        this.word = word;
        this.transl = transl;
        this.example = example;
    }


    //methods
    public int sumRank(){
        int result = 0;
        for (int e : hits){
            result += e;
        }
        return result;
    }

    public int updateRank(Boolean hit){
        for (int i = 0; i < 9; i++){
            hits[i] = hits[i+1];
        }
        if (hit)
            hits[9] = 1;
        else
            hits[9] = 0;

        return sumRank();
    }

    public Boolean lastQ(){
        if (hits[9] == 1)
            return true;
        else
            return false;
    }

    public Boolean last3Q(){
        for (int i = 7; i<10; i++) {
            if (hits[i] == 0)
                return false;
        }
        return true;
    }

    public Boolean last5Q(){
        for (int i = 5; i<10; i++) {
            if (hits[i] == 0)
                return false;
        }
        return true;
    }
}
