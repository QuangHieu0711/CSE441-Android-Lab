package com.example.vnexpressrss

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var lv: ListView
    private lateinit var adapter: ArticleAdapter
    private val items = mutableListOf<Article>()

    private val FEEDS = listOf(
        "https://vnexpress.net/rss/tin-moi-nhat.rss",
        "https://vnexpress.net/rss/thoi-su.rss",
        "https://vnexpress.net/rss/the-gioi.rss",
        "https://vnexpress.net/rss/kinh-doanh.rss",
        "https://vnexpress.net/rss/the-thao.rss",
        "https://www.w3schools.com/xml/simple.xml" // fallback RSS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.lvNews)
        adapter = ArticleAdapter(this, R.layout.item_article, items)
        lv.adapter = adapter

        LoadRssTask().execute()
    }

    inner class LoadRssTask : AsyncTask<Unit, Unit, Pair<String, List<Article>>>() {
        private var errMsg: String? = null

        override fun onPreExecute() {
            super.onPreExecute()
            items.clear()
            adapter.notifyDataSetChanged()
            Toast.makeText(this@MainActivity, "🔄 Đang tải dữ liệu RSS…", Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg params: Unit?): Pair<String, List<Article>> {
            val fetcher = RssFetcher()
            for (url in FEEDS) {
                val result = runCatching { fetcher.fetch(url) }.getOrDefault(emptyList())
                if (result.isNotEmpty()) return url to result
            }
            errMsg = "❌ Không lấy được dữ liệu từ các kênh VNExpress!"
            return FEEDS.last() to emptyList()
        }

        override fun onPostExecute(result: Pair<String, List<Article>>) {
            val (sourceUrl, data) = result
            if (!errMsg.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, errMsg, Toast.LENGTH_LONG).show()
            }

            items.addAll(data)
            adapter.notifyDataSetChanged()

            val message = if (data.isNotEmpty()) {
                "✅ Tải thành công từ:\n$sourceUrl\n(${data.size} bài viết)"
            } else {
                "⚠️ Không có bài viết nào."
            }
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }
    }
}
