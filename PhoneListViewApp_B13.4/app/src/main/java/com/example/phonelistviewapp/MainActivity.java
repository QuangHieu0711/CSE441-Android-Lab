package com.example.phonelistviewapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Phone> mylist;
    MyArrayAdapter myAdapter;

    String[] namephone = {
            "Điện thoại Iphone 12", "Điện thoại Samsung S20", "Điện thoại Nokia 6",
            "Điện thoại Bphone 2020", "Điện thoại Oppo 5", "Điện thoại VSmart Joy2"
    };

    int[] imagephone = {
            R.drawable.ip, R.drawable.samsung, R.drawable.nk,
            R.drawable.bp, R.drawable.op, R.drawable.vs
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();

        for (int i = 0; i < namephone.length; i++) {
            mylist.add(new Phone(namephone[i], imagephone[i]));
        }

        myAdapter = new MyArrayAdapter(this, R.layout.layout_listview, mylist);
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, SubActivity.class);
                myIntent.putExtra("name", namephone[position]);
                startActivity(myIntent);
            }
        });
    }
}
