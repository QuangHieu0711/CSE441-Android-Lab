// File: MainActivity.java
package com.example.ptb2app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edita, editb, editc;
    TextView txtkq;
    Button btnTiepTuc, btnGiai, btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edita = findViewById(R.id.edita);
        editb = findViewById(R.id.editb);
        editc = findViewById(R.id.editc);
        txtkq = findViewById(R.id.txtkq);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        btnGiai = findViewById(R.id.btnGiai);
        btnThoat = findViewById(R.id.btnThoat);

        btnGiai.setOnClickListener(v -> {
            String sa = edita.getText().toString();
            String sb = editb.getText().toString();
            String sc = editc.getText().toString();

            if (sa.isEmpty() || sb.isEmpty() || sc.isEmpty()) {
                txtkq.setText(R.string.input_warning);
                return;
            }

            int a = Integer.parseInt(sa);
            int b = Integer.parseInt(sb);
            int c = Integer.parseInt(sc);

            String kq;
            DecimalFormat dcf = new DecimalFormat("0.00");

            if (a == 0) {
                kq = (b == 0) ? (c == 0 ? getString(R.string.infinite_solutions) : getString(R.string.no_solution))
                        : getString(R.string.one_solution, dcf.format(-c / (double) b));
            } else {
                double delta = b * b - 4 * a * c;
                if (delta < 0) {
                    kq = getString(R.string.no_solution);
                } else if (delta == 0) {
                    kq = getString(R.string.double_solution, dcf.format(-b / (2.0 * a)));
                } else {
                    double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                    double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                    kq = getString(R.string.two_solutions, dcf.format(x1), dcf.format(x2));
                }
            }

            txtkq.setText(kq);
        });

        btnTiepTuc.setOnClickListener(v -> {
            edita.setText("");
            editb.setText("");
            editc.setText("");
            txtkq.setText("");
            edita.requestFocus();
        });

        btnThoat.setOnClickListener(v -> finish());
    }
}
