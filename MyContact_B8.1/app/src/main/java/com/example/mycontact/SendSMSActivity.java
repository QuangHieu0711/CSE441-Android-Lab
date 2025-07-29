package com.example.mycontact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SendSMSActivity extends AppCompatActivity {
    EditText edtsms;
    Button btnback2;
    ImageButton btnsendsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_smsactivity);

        edtsms = findViewById(R.id.edtsms);
        btnback2 = findViewById(R.id.btnback2);
        btnsendsms = findViewById(R.id.btnsms);

        btnsendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("smsto:" + edtsms.getText().toString()));
                startActivity(callIntent);
            }
        });

        btnback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
