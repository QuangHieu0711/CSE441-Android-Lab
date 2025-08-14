package com.example.parsejsonassets

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var btnParse: Button
    private lateinit var lv: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val items = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnParse = findViewById(R.id.btnparse)
        lv = findViewById(R.id.lv)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lv.adapter = adapter

        btnParse.setOnClickListener { parseJson() }
    }

    private fun parseJson() {
        items.clear()
        try {
            val json = assets.open("computer.json").use { input ->
                val buffer = ByteArray(input.available())
                input.read(buffer)
                String(buffer, Charset.forName("UTF-8"))
            }

            val root = JSONObject(json)
            items.add(root.getString("MaDM"))
            items.add(root.getString("TenDM"))

            val arr: JSONArray = root.getJSONArray("Sanphams")
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val maSP = obj.getString("MaSP")
                val tenSP = obj.getString("TenSP")
                val soLuong = obj.getInt("SoLuong")
                val donGia = obj.getInt("DonGia")
                val thanhTien = if (obj.has("ThanhTien")) obj.getLong("ThanhTien")
                else soLuong.toLong() * donGia
                val hinh = obj.getString("Hinh")

                items.add("$maSP - $tenSP")
                items.add("$soLuong * $donGia = $thanhTien")
                items.add(hinh)
            }

            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(this, "Parse lỗi: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
