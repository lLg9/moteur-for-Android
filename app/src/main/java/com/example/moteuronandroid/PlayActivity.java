package com.example.moteuronandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moteur.Moteur;


import com.example.moteur.WordStruct;
import com.example.moteur.iMoteur;

import java.io.File;

public class PlayActivity extends AppCompatActivity {

    public static final String __NUM__ = "NUMBER_OF_WORD_TO_LOAD";
    public static final String __MODE__ = "PLAY_MODE";
    public static final String __PACK_NUM__ = "PACKAGE_NUMBER";

    public static final int MODE_RANDOM = 0;
    public static final int MODE_WORD = 1;
    public static final int MODE_TRANSL = 2;
    public static final int NUM_TWENTY = 20;
    public static final int NUM_ALL = 60;
    public static final int NUM_PM = 8;


    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 777;

    private String path;
    private int pack;
    private int mode;
    private int num;

    private WordStruct ws;
    iMoteur moteur;

    Button b_show;
    Button b_no;
    Button b_yes;
    TextView t_rem;
    TextView t_word;
    TextView t_transl;
    TextView t_example;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        pack = getIntent().getIntExtra(__PACK_NUM__, 1);
        mode = getIntent().getIntExtra(__MODE__, MODE_RANDOM);
        num = getIntent().getIntExtra(__NUM__, NUM_TWENTY);

        path = Environment.getExternalStorageDirectory() + File.separator + "MOTEUR_CONTENTS" + File.separator + "Deutsch" + File.separator + pack;
        //this line is to be changed only, to change the implementation of Moteur
        moteur = new Moteur(path, pack, num);

        //request permissions if not have them already

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        else {
            Toast.makeText(this, "has permission", Toast.LENGTH_SHORT).show();
        }

        ws = moteur.getNext();

        b_show = (Button) findViewById(R.id.b_show);
        b_no = (Button) findViewById(R.id.b_no);
        b_yes = (Button) findViewById(R.id.b_yes);
        t_rem = (TextView) findViewById(R.id.t_rem);
        t_word = (TextView) findViewById(R.id.t_word);
        t_transl = (TextView) findViewById(R.id.t_translation);
        t_example = (TextView) findViewById(R.id.t_example);

        t_rem.setText(Integer.toString(ws.remain));
        switch (mode){
            case MODE_WORD:
                t_word.setText(ws.word);
                break;
            case MODE_TRANSL:
                t_word.setText(ws.transl);
                break;
            default:
                if (ws.remain % 6 < 3) {
                    t_word.setText(ws.word);
                }
                else {
                    t_word.setText(ws.transl);
                }
        }
    }

    public void onShowClicked(View view) {
        b_show.setVisibility(View.INVISIBLE);
        b_no.setVisibility(View.VISIBLE);
        b_yes.setVisibility(View.VISIBLE);

        switch (mode){
            case MODE_WORD:
                t_transl.setText(ws.transl);
                break;
            case MODE_TRANSL:
                t_transl.setText(ws.word);
                break;
            default:
                if (ws.remain % 6 < 3) {
                    t_transl.setText(ws.transl);
                }
                else {
                    t_transl.setText(ws.word);
                }
        }
        t_example.setText(ws.example);
    }

    public void onYesClicked(View view) {
        moteur.giveFeedback(Boolean.TRUE);
        loadNextWord();
    }

    public void onNoClicked(View view) {
        moteur.giveFeedback(Boolean.FALSE);
        loadNextWord();
    }

    private void loadNextWord(){



        b_show.setVisibility(View.VISIBLE);
        b_no.setVisibility(View.INVISIBLE);
        b_yes.setVisibility(View.INVISIBLE);

        ws = moteur.getNext();

        t_rem.setText(Integer.toString(ws.remain));

        switch (mode){
            case MODE_WORD:
                t_word.setText(ws.word);
                t_transl.setText("---");
                break;
            case MODE_TRANSL:
                t_word.setText(ws.transl);
                t_transl.setText("---");
                break;
            default:
                if (ws.remain % 6 < 3) {
                    t_word.setText(ws.word);
                    t_transl.setText("---");
                }
                else {
                    t_word.setText(ws.transl);
                    t_transl.setText("---");
                }
        }
        t_example.setText("---");
    }
}
