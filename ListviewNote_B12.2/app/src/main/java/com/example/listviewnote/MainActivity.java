package com.example.listviewnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    ListView lv;
    ArrayList<String> arraywork;
    ArrayAdapter<String> arrAdapter;
    EditText edtwork, edth, edtm;
    TextView txtdate;
    Button btnwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        edth = findViewById(R.id.edthour);
        edtm = findViewById(R.id.edtmin);
        edtwork = findViewById(R.id.edtwork);
        btnwork = findViewById(R.id.btnadd);
        lv = findViewById(R.id.listView1);
        txtdate = findViewById(R.id.txtdate2);

        // Khởi tạo danh sách và adapter
        arraywork = new ArrayList<>();
        arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraywork);
        lv.setAdapter(arrAdapter);

        // Hiển thị ngày hiện tại
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        txtdate.setText("Hôm Nay: " + format.format(currentDate));

        // Xử lý sự kiện khi bấm nút Add Work
        btnwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String work = edtwork.getText().toString().trim();
                String hour = edth.getText().toString().trim();
                String minute = edtm.getText().toString().trim();

                if (work.equals("") || hour.equals("") || minute.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please enter all information of the work");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // nothing
                        }
                    });
                    builder.show();
                } else {
                    String str = "+ " + work + " - " + hour + ":" + minute;
                    arraywork.add(str);
                    arrAdapter.notifyDataSetChanged();

                    // Reset input
                    edtwork.setText("");
                    edth.setText("");
                    edtm.setText("");
                }
            }
        });
    }
}
