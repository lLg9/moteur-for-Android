package com.example.moteuronandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class ChoosePackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_package);
    }

    public void onPlayClicked(View view) {
        Intent intent = new Intent(this, PlayActivity.class);

        Spinner sp_choosePack = (Spinner) findViewById(R.id.sp_choose_pack);
        int pack = Integer.parseInt((String) sp_choosePack.getSelectedItem());
        intent.putExtra(PlayActivity.__PACK_NUM__, pack);

        RadioGroup rg_mode = (RadioGroup) findViewById(R.id.rg_mode);
        int rgid_mode = rg_mode.getCheckedRadioButtonId();
        switch (rgid_mode){
            case R.id.rb_w:
                intent.putExtra(PlayActivity.__MODE__, PlayActivity.MODE_WORD);
                break;
            case R.id.rb_t:
                intent.putExtra(PlayActivity.__MODE__, PlayActivity.MODE_TRANSL);
                break;
            case R.id.rb_r:
                intent.putExtra(PlayActivity.__MODE__, PlayActivity.MODE_RANDOM);
                break;
        }

        RadioGroup rg_num = (RadioGroup) findViewById(R.id.rg_num);
        int rgid_num = rg_num.getCheckedRadioButtonId();
        switch (rgid_num){
            case R.id.rb_20:
                intent.putExtra(PlayActivity.__NUM__, PlayActivity.NUM_TWENTY);
                break;
            case R.id.rb_60:
                intent.putExtra(PlayActivity.__NUM__, PlayActivity.NUM_ALL);
                break;
            case R.id.rb_pm:
                intent.putExtra(PlayActivity.__NUM__, PlayActivity.NUM_PM);
                break;
        }

        startActivity(intent);
    }
}
