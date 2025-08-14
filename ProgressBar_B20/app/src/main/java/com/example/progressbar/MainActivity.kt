package com.example.progressbar

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvPercent: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPercent = findViewById(R.id.tvPercent)
        progressBar = findViewById(R.id.progressBar)
        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener { startProgress() }
    }

    private fun startProgress() {
        progressBar.progress = 0
        tvPercent.text = "0%"

        lifecycleScope.launch {
            Toast.makeText(this@MainActivity, "onPreExecute()", Toast.LENGTH_SHORT).show()

            for (i in 1..100) {
                delay(100)               // giả lập công việc
                progressBar.progress = i
                tvPercent.text = "$i%"
            }

            Toast.makeText(this@MainActivity, "Update xong rồi!", Toast.LENGTH_SHORT).show()
        }
    }
}
