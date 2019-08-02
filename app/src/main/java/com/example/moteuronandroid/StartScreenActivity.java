package com.example.moteuronandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void onNextClicked(View view) {
        Intent intent = new Intent(this, ChoosePackageActivity.class);
        startActivity(intent);
    }
}
