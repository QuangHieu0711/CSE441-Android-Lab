package com.example.gridviewbrandsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView selection;
    GridView gridView;

    String[] brands = {
            "iPad", "iPhone", "New iPad",
            "Samsung", "Nokia", "Sony Ericson",
            "Q-Mobile", "HTC", "Blackberry",
            "LG", "G Phone", "FPT - Phone",
            "HK Phone"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selection = findViewById(R.id.selection);
        gridView = findViewById(R.id.gridView1);

        // Gắn dữ liệu vào GridView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                brands
        );
        gridView.setAdapter(adapter);

        // Thiết lập sự kiện click
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selection.setText("You selected: " + brands[position]);
            }
        });
    }
}
