package com.example.parsexmlassetsdemo

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Xml
import org.xmlpull.v1.XmlPullParser

data class Employee(
    var id: Int = 0,
    var title: String = "",
    var name: String = "",
    var phone: String = ""
)

class MainActivity : AppCompatActivity() {

    private lateinit var btnParse: Button
    private lateinit var lv: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val display = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnParse = findViewById(R.id.btnparse)
        lv = findViewById(R.id.lv)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, display)
        lv.adapter = adapter

        btnParse.setOnClickListener {
            try {
                val employees = parseEmployeesFromAssets("employee.xml")
                display.clear()
                employees.forEachIndexed { index, e ->
                    display.add("${index + 1}-${e.title}-${e.name}-${e.phone}")
                }
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(this, "Parse lỗi: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun parseEmployeesFromAssets(fileName: String): List<Employee> {
        val list = mutableListOf<Employee>()
        assets.open(fileName).use { input ->
            val parser = Xml.newPullParser()
            parser.setInput(input, "UTF-8")

            var event = parser.eventType
            var current: Employee? = null

            while (event != XmlPullParser.END_DOCUMENT) {
                when (event) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "employee" -> {
                                current = Employee().apply {
                                    id = parser.getAttributeValue(null, "id")?.toIntOrNull() ?: 0
                                    title = parser.getAttributeValue(null, "title") ?: ""
                                }
                            }
                            "name" -> current?.name = parser.nextText()
                            "phone" -> current?.phone = parser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "employee" && current != null) {
                            list.add(current!!)
                            current = null
                        }
                    }
                }
                event = parser.next()
            }
        }
        return list
    }
}
