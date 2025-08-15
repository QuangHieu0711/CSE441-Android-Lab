package com.example.pizzaxmlparser

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class XMLParser {
    fun getXmlFromUrl(urlString: String): String {
        var connection: HttpURLConnection? = null
        return try {
            val url = URL(urlString)
            connection = (url.openConnection() as HttpURLConnection).apply {
                connectTimeout = 15000
                readTimeout = 15000
                requestMethod = "GET"
                instanceFollowRedirects = true
            }
            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val reader = BufferedReader(InputStreamReader(stream))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) sb.append(line)
            reader.close()
            if (code !in 200..299) {
                Log.e("PizzaXmlParser", "HTTP $code từ $urlString; body=${sb.toString().take(200)}")
            }
            sb.toString()
        } catch (e: Exception) {
            Log.e("PizzaXmlParser", "HTTP error: ${e.localizedMessage}", e)
            ""
        } finally {
            connection?.disconnect()
        }
    }
}
