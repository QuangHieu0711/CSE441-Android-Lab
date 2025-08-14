package com.example.sqliteassetsdemo

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var lv: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val data = ArrayList<String>()

    private val helper by lazy { DatabaseAssetHelper(this) }
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.lv)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        lv.adapter = adapter

        try {
            database = helper.openDatabase()
        } catch (e: Exception) {
            Toast.makeText(this, "Open DB error: ${e.message}", Toast.LENGTH_LONG).show()
            return
        }

        loadData()
    }

    private fun loadData() {
        data.clear()

        // Lấy 3 cột đầu của bảng tbsach làm ví dụ
        val c = database.query("tbsach", null, null, null, null, null, null)
        c.use {
            if (it.moveToFirst()) {
                do {
                    val s = "${it.getString(0)} - ${it.getString(1)} - ${it.getString(2)}"
                    data.add(s)
                } while (it.moveToNext())
            }
        }
        adapter.notifyDataSetChanged()
    }
}
