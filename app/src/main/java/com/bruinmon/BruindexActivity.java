package com.bruinmon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BruindexActivity extends AppCompatActivity {

    private ArrayList<Bruinmon> bruindex;
    private ListView listView;
    private BruinListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bruindex);

        bruindex = new ArrayList<Bruinmon>();
        bruindex.addAll(Bruinmon.getAll());

        listView = findViewById(R.id.bruindex);
        adapter = new BruinListAdapter(bruindex, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bruinmon bruinmon = bruindex.get(position);
                Intent intent = new Intent(getApplicationContext(), BruinInfoActivity.class);
                intent.putExtra("bruinmon", bruinmon);
                startActivity(intent);
            }
        });
    }
}
