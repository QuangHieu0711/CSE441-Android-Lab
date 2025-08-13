package com.example.sumhistorykotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtA: EditText
    private lateinit var edtB: EditText
    private lateinit var txtKetQua: TextView
    private lateinit var txtLichSu: TextView
    private lateinit var btnTong: Button
    private lateinit var btnClear: Button

    private val PREF_NAME = "mysave"
    private val KEY_HISTORY = "ls"
    private var lichSu: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)   // dùng layout trực tiếp

        // Ánh xạ view
        edtA = findViewById(R.id.edtA)
        edtB = findViewById(R.id.edtB)
        txtKetQua = findViewById(R.id.txtKetQua)
        txtLichSu = findViewById(R.id.txtLichSu)
        btnTong = findViewById(R.id.btnTong)
        btnClear = findViewById(R.id.btnClear)

        // Load lịch sử đã lưu
        val sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        lichSu = sp.getString(KEY_HISTORY, "") ?: ""
        txtLichSu.text = lichSu

        // Nút TỔNG
        btnTong.setOnClickListener {
            val a = edtA.text.toString().ifBlank { "0" }.toInt()
            val b = edtB.text.toString().ifBlank { "0" }.toInt()
            val kq = a + b
            txtKetQua.text = kq.toString()
            lichSu += "$a + $b = $kq\n"
            txtLichSu.text = lichSu
        }

        // Nút CLEAR
        btnClear.setOnClickListener {
            lichSu = ""
            txtLichSu.text = lichSu
        }
    }

    override fun onPause() {
        super.onPause()
        // Lưu lịch sử
        getSharedPreferences(PREF_NAME, MODE_PRIVATE)
            .edit().putString(KEY_HISTORY, lichSu).apply()
    }
}
