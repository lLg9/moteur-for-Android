package com.example.moteur;

import java.util.ArrayList;

public interface iMoteur {

    WordStruct getNext();
    void giveFeedback(Boolean succ);
    String recapMissed();

}
