package com.example.pizzaxmlparser

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class MainActivity : AppCompatActivity() {

    private lateinit var btnParse: Button
    private lateinit var lv1: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val items = ArrayList<String>()

    // Dùng XML ổn định từ W3Schools
    private val URL_XML = "https://www.w3schools.com/xml/simple.xml"
    private val TAG = "PizzaXmlParser"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnParse = findViewById(R.id.btnParse)
        lv1 = findViewById(R.id.lv1)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lv1.adapter = adapter

        btnParse.setOnClickListener {
            if (!isOnline()) {
                Toast.makeText(this, "Thiết bị chưa có Internet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            LoadExampleTask().execute()
        }
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    inner class LoadExampleTask : AsyncTask<Unit, Unit, List<String>>() {
        private var error: String? = null

        override fun onPreExecute() {
            super.onPreExecute()
            adapter.clear()
            Toast.makeText(this@MainActivity, "Đang tải XML…", Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg params: Unit?): List<String> {
            val list = ArrayList<String>()
            try {
                val xml = XMLParser().getXmlFromUrl(URL_XML)
                Log.d(TAG, "Fetched ${xml.length} chars: ${xml.take(150)}")
                if (xml.isBlank()) {
                    error = "Không lấy được nội dung từ server"
                    return list
                }

                // Cấu trúc W3Schools:
                // <breakfast_menu>
                //   <food>
                //     <name>...</name>
                //     <price>...</price>
                //     ...
                //   </food>
                // </breakfast_menu>

                val factory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = false
                val parser = factory.newPullParser()
                parser.setInput(StringReader(xml))

                var eventType = parser.eventType
                var currentTag: String? = null
                var nameValue = ""
                var priceValue = ""

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> currentTag = parser.name
                        XmlPullParser.TEXT -> {
                            val text = parser.text?.trim().orEmpty()
                            if (text.isNotEmpty()) {
                                when (currentTag) {
                                    "name" -> nameValue = text
                                    "price" -> priceValue = text
                                }
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            if (parser.name.equals("food", ignoreCase = true)) {
                                val display = if (nameValue.isNotBlank())
                                    "$nameValue - $priceValue"
                                else "(không tên) - $priceValue"
                                list.add(display)
                                nameValue = ""
                                priceValue = ""
                            }
                            currentTag = null
                        }
                    }
                    eventType = parser.next()
                }

                if (list.isEmpty()) error = "Parse xong nhưng không có item nào."
            } catch (e: Exception) {
                error = "Lỗi: ${e.localizedMessage}"
                Log.e(TAG, "Parse error", e)
            }
            return list
        }

        override fun onPostExecute(result: List<String>) {
            super.onPostExecute(result)
            if (!error.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show()
            }
            adapter.clear()
            adapter.addAll(result)
            Toast.makeText(this@MainActivity, "Hoàn tất: ${result.size} dòng", Toast.LENGTH_SHORT).show()
        }
    }
}
