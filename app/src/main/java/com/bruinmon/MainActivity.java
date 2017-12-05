package com.bruinmon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private BruinListAdapter nearbyBruinmon;
    public static MoveDBOperater bruinDB;

    private Handler handler = new Handler();
    private Runnable nearbyBruinmonUpdate = new Runnable() {
        @Override
        public void run() {
            // Call the update function about every 5 seconds
            handler.postDelayed(nearbyBruinmonUpdate, 20000);

            // TODO : List what specific bruinmon are nearby (right now it just lists all of them)
            nearbyBruinmon.clear();
            nearbyBruinmon.addAll(Bruinmon.getAll());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bruinDB = new MoveDBOperater(this);
        bruinDB.open();

        listView = findViewById(R.id.bruins_nearby);
        nearbyBruinmon = new BruinListAdapter(new ArrayList<Bruinmon>(), getApplicationContext());
        listView.setAdapter(nearbyBruinmon);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bruinmon bruinmon = nearbyBruinmon.getItem(position);
                if (Bruinmon.captureBruinmon(bruinmon, bruinDB)) {
                    Toast.makeText(getApplicationContext(), bruinmon.getName() + " captured!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "You already own " + bruinmon.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        handler.post(nearbyBruinmonUpdate);
    }

    /** Called when the user touches the about icon in the top right of the main menu **/
    public void showInfoBox(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("About Bruinmon");
        dialog.setMessage(
                "A fun game made for CS M117 at UCLA, in order to help us learn about GPS, Bluetooth, and general networking technologies\n" +
                "\n" +
                "Authors\n" +
                "    Uday Alla\n" +
                "    Trey Crossley\n" +
                "    Brandon Haffen\n" +
                "    Nicholas Turk"
        );
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        dialog.show();
    }

    /** Called when the user touches the My Bruins button **/
    public void navigateMyBruins(View view) {
        Intent intent = new Intent(this, MyBruinsActivity.class);
        startActivity(intent);
    }

    /** Called when the user touches the Bruindex button **/
    public void navigateBruindex(View view) {
        Intent intent = new Intent(this, BruindexActivity.class);
        startActivity(intent);
    }

    /** Called when the user touches the Battle button **/
    public void navigateBattle(View view) {
        Intent intent = new Intent(this, PreBattleActivity.class);
        startActivity(intent);
    }
}
