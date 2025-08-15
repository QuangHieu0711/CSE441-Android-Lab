package com.example.vnexpressrss

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class RssFetcher {

    fun fetch(urlStr: String): List<Article> {
        val xml = download(urlStr)
        if (xml.isBlank()) return emptyList()

        val list = mutableListOf<Article>()
        val parser = XmlPullParserFactory.newInstance().newPullParser()
        parser.setInput(StringReader(xml))

        var event = parser.eventType
        var inItem = false
        var tag: String? = null

        var title = ""
        var link = ""
        var desc = ""

        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    tag = parser.name.lowercase()
                    if (tag == "item") {
                        inItem = true
                        title = ""; link = ""; desc = ""
                    }
                }
                XmlPullParser.TEXT, XmlPullParser.CDSECT -> {
                    if (inItem) {
                        val text = parser.text.trim()
                        when (tag) {
                            "title" -> title += text
                            "link" -> link += text
                            "description" -> desc += text
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if ((parser.name.lowercase() == "item") && inItem) {
                        val img = extractImage(desc)
                        val summary = stripHtml(desc)
                        list.add(Article(title, summary, link, img))
                        inItem = false
                    }
                    tag = null
                }
            }
            event = parser.next()
        }

        return list
    }

    private fun download(urlStr: String): String {
        return try {
            val conn = URL(urlStr).openConnection() as HttpURLConnection
            conn.connectTimeout = 10000
            conn.readTimeout = 10000
            conn.requestMethod = "GET"
            conn.setRequestProperty("User-Agent", "VNExpressRss")

            val reader = BufferedReader(InputStreamReader(conn.inputStream, StandardCharsets.UTF_8))
            reader.useLines { lines -> lines.joinToString("\n") }
        } catch (e: Exception) {
            Log.e("RssFetcher", "Error downloading RSS: ${e.message}")
            ""
        }
    }

    private fun extractImage(html: String): String? {
        val regex = Regex("<img[^>]+src=[\"']([^\"']+)[\"']", RegexOption.IGNORE_CASE)
        return regex.find(html)?.groupValues?.get(1)
    }

    private fun stripHtml(html: String): String {
        return html.replace(Regex("<[^>]+>"), " ").replace(Regex("\\s+"), " ").trim()
    }
}
