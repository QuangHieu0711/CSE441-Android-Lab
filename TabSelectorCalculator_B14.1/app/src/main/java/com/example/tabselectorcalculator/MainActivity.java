package com.example.tabselectorcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends Activity {
    EditText edta, edtb;
    Button btncong;
    ListView lv1;
    ArrayList<String> list;
    ArrayAdapter<String> myarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
    }

    private View createTabIndicator(String title, int iconResId) {
        View view = getLayoutInflater().inflate(R.layout.tab_indicator, null);
        ImageView icon = view.findViewById(R.id.tab_icon);
        TextView text = view.findViewById(R.id.tab_text);
        icon.setImageResource(iconResId);
        text.setText(title);
        return view;
    }

    private void addControl() {
        TabHost tab = findViewById(R.id.tabhost);
        tab.setup();

        // Tab 1: Phép cộng
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator(createTabIndicator("Phép cộng", R.drawable.cong));
        tab.addTab(tab1);

        // Tab 2: Lịch sử
        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator(createTabIndicator("Lịch sử", R.drawable.lichsu));
        tab.addTab(tab2);

        // Khởi tạo các view và adapter
        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        btncong = findViewById(R.id.btncong);
        lv1 = findViewById(R.id.lv1);

        list = new ArrayList<>();
        myarray = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv1.setAdapter(myarray);
    }

    private void addEvent() {
        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int a = Integer.parseInt(edta.getText().toString());
                    int b = Integer.parseInt(edtb.getText().toString());
                    String c = a + " + " + b + " = " + (a + b);
                    list.add(c);
                    myarray.notifyDataSetChanged();
                    edta.setText("");
                    edtb.setText("");
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
