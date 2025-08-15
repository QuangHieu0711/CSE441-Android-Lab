// app/src/main/java/com/example/tygiadongabank/MainActivity.kt
package com.example.tygiadongabank

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var lvTyGia: ListView
    private lateinit var txtDate: TextView
    private lateinit var adapter: TyGiaAdapter
    private val items = mutableListOf<TyGia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvTyGia = findViewById(R.id.lvTyGia)
        txtDate = findViewById(R.id.txtdate)

        adapter = TyGiaAdapter(this, R.layout.item, items)
        lvTyGia.adapter = adapter

        showToday()
        loadData()
    }

    private fun showToday() {
        val current = Calendar.getInstance().time
        val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        txtDate.text = "Hôm Nay: ${fmt.format(current)}"
    }

    private fun loadData() {
        lifecycleScope.launchWhenStarted {
            val data = fetchRates()
            if (data.isEmpty()) {
                // Fallback để bạn thấy UI ngay cả khi server lỗi
                adapter.replaceAll(
                    listOf(
                        TyGia(
                            type = "USD",
                            muatienmat = "23300",
                            muack = "23370",
                            bantienmat = "23600",
                            banck = "23640",
                            bank = "DONGA"
                        ),
                        TyGia(
                            type = "EUR",
                            muatienmat = "25300",
                            muack = "25350",
                            bantienmat = "25800",
                            banck = "25850",
                            bank = "DONGA"
                        )
                    )
                )
            } else {
                adapter.replaceAll(data)
            }
        }
    }

    /**
     * Tải dữ liệu tỉ giá từ DongABank.
     * - Thử HTTPS trước, rồi fallback HTTP.
     * - Bóc JSON bằng vị trí dấu { ... } để không phụ thuộc prefix "var rate =".
     * - Tải ảnh cờ nếu có.
     */
    private suspend fun fetchRates(): List<TyGia> = withContext(Dispatchers.IO) {
        val result = mutableListOf<TyGia>()
        val endpoints = listOf(
            "https://dongabank.com.vn/exchange/export/",
            "http://dongabank.com.vn/exchange/export/"
        )

        var rawText: String? = null
        for (u in endpoints) {
            rawText = downloadText(u)
            if (!rawText.isNullOrBlank()) break
        }
        if (rawText.isNullOrBlank()) return@withContext result

        // Lấy phần JSON thuần tuý giữa { ... }
        val start = rawText!!.indexOf('{')
        val end = rawText!!.lastIndexOf('}')
        if (start == -1 || end <= start) return@withContext result
        val jsonText = rawText!!.substring(start, end + 1)

        try {
            val root = JSONObject(jsonText)
            val arr: JSONArray = root.optJSONArray("items") ?: JSONArray()
            for (i in 0 until arr.length()) {
                val item = arr.getJSONObject(i)
                val tg = TyGia(
                    type = item.optString("type"),
                    imageurl = item.optString("imageurl"),
                    muatienmat = item.optString("muatienmat"),
                    muack = item.optString("muack"),
                    bantienmat = item.optString("bantienmat"),
                    banck = item.optString("banck"),
                    bank = item.optString("bank")
                )

                // Tải ảnh lá cờ (không chặn UI nếu lỗi)
                if (tg.imageurl.isNotBlank()) {
                    tg.bitmap = downloadBitmap(tg.imageurl)
                }

                result.add(tg)
            }
        } catch (e: Exception) {
            Log.e("TyGiaDongABank", "Parse JSON error: ${e.message}")
        }
        result
    }

    /** Tải văn bản từ URL */
    private fun downloadText(u: String): String? {
        return try {
            val conn = (URL(u).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("User-Agent", "Mozilla/5.0 (Android) TyGiaDongABank")
                setRequestProperty("Accept", "*/*")
                connectTimeout = 15000
                readTimeout = 15000
            }
            val reader = BufferedReader(InputStreamReader(conn.inputStream, Charsets.UTF_8))
            val sb = StringBuilder()
            var line: String? = reader.readLine()
            while (line != null) {
                sb.append(line).append('\n')
                line = reader.readLine()
            }
            reader.close()
            conn.disconnect()
            sb.toString()
        } catch (e: Exception) {
            Log.w("TyGiaDongABank", "downloadText failed: ${e.message}")
            null
        }
    }

    /** Tải ảnh bitmap từ URL */
    private fun downloadBitmap(link: String): Bitmap? {
        return try {
            val c = (URL(link).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("Accept", "*/*")
                connectTimeout = 10000
                readTimeout = 10000
            }
            val bmp = BitmapFactory.decodeStream(c.inputStream)
            c.disconnect()
            bmp
        } catch (e: Exception) {
            Log.w("TyGiaDongABank", "downloadBitmap failed: ${e.message}")
            null
        }
    }
}
