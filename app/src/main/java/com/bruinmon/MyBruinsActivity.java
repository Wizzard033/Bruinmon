package com.bruinmon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyBruinsActivity extends AppCompatActivity {

    private ArrayList<Bruinmon> myBruinmon;
    private ListView listView;
    private BruinListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bruins);

        myBruinmon = new ArrayList<Bruinmon>();
        myBruinmon.addAll(Bruinmon.getAllOwned());

        listView = findViewById(R.id.my_bruins);
        adapter = new BruinListAdapter(myBruinmon, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bruinmon bruinmon = myBruinmon.get(position);
                Intent intent = new Intent(getApplicationContext(), BruinInfoActivity.class);
            }
        });
    }
}
