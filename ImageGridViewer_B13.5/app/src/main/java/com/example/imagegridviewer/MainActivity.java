package com.example.imagegridviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String[] arrayName = {
            "Ảnh 1", "Ảnh 2", "Ảnh 3", "Ảnh 4", "Ảnh 5", "Ảnh 6",
            "Ảnh 7", "Ảnh 8", "Ảnh 9", "Ảnh 10", "Ảnh 11", "Ảnh 12"
    };

    public static int[] imageName = {
            R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
            R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8,
            R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12
    };

    GridView gridView;
    MyArrayAdapter adapter;
    ArrayList<Image> arrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid1);
        arrImage = new ArrayList<>();

        for (int i = 0; i < arrayName.length; i++) {
            arrImage.add(new Image(imageName[i], arrayName[i]));
        }

        adapter = new MyArrayAdapter(this, R.layout.listitem, arrImage);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            intent.putExtra("TITLE", position);
            startActivity(intent);
        });
    }
}
