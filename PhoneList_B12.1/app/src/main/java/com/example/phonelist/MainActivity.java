package com.example.phonelist; // Thay đổi nếu package khác

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Khai báo biến giao diện
    ListView lv1;
    TextView txt1;

    // Dữ liệu danh sách điện thoại
    String[] arr1 = new String[] {
            "Iphone 7",
            "SamSung Galaxy S7",
            "Nokia Lumia 730",
            "Sony Xperia XZ",
            "HTC One E9"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ control
        lv1 = findViewById(R.id.lv1);
        txt1 = findViewById(R.id.txtselection);

        // Tạo adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arr1
        );

        // Gán adapter vào ListView
        lv1.setAdapter(adapter1);

        // Gán sự kiện click cho ListView
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                txt1.setText("Vị trí " + i + " : " + arr1[i]);
            }
        });
    }
}
