package com.bruinmon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PreBattleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_battle);
    }

    /** Called when the user touches the Battle vs AI button **/
    public void battleAI(View view) {
        Intent intent = new Intent(this, BattleActivity.class);
        startActivity(intent);
    }

    /** Called when the user touches the Battle vs Player button **/
    public void battlePlayer(View view) {
        // TODO : Use Bluetooth to do a player vs. player battle
    }
}
