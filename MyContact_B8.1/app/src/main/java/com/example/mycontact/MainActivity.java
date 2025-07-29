package com.example.mycontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCallPhone, btnSendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ nút từ layout
        btnCallPhone = findViewById(R.id.btncallphone);
        btnSendSMS = findViewById(R.id.btnsendsms);

        // sự kiện nút Call Phone
        btnCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, CallPhoneActivity.class);
                startActivity(intent1);
            }
        });

        // sự kiện nút Send SMS
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, SendSMSActivity.class);
                startActivity(intent2);
            }
        });
    }
}
