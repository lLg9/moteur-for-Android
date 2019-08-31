package com.example.moteuronandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RecapMissedActivity extends AppCompatActivity {

    public static final String __MISSED__ = "WORDS_MISSED";

    private String wMissed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_missed);

        wMissed = getIntent().getStringExtra(__MISSED__);
        TextView t_missed = (TextView) findViewById(R.id.t_missed);
        t_missed.setText(wMissed);
    }
}
