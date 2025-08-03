package com.example.spinnerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

    // Danh sách các loại hàng
    String arr[] = { "Hàng điện tử", "Hàng Hóa Chất", "Hàng Gia dụng", "Hàng Xây dựng" };
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = findViewById(R.id.txt1);
        Spinner spin1 = findViewById(R.id.spinner1);

        // Khởi tạo adapter và gán vào spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                arr
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);

        // Xử lý sự kiện khi chọn item
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt1.setText(arr[position]); // Hiển thị mục được chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không chọn gì thì không làm gì cả
            }
        });
    }
}
