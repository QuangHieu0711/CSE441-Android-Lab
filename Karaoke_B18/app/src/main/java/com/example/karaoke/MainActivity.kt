package com.example.karaoke

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TabHost
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtTim: EditText
    private lateinit var lvSearch: ListView
    private lateinit var lvAll: ListView
    private lateinit var lvFav: ListView
    private lateinit var btnXoa: ImageButton
    private lateinit var tabHost: TabHost

    private val helper by lazy { DatabaseAssetHelper(this) }
    private val db: SQLiteDatabase by lazy { helper.openDatabase() }

    private val listSearch = mutableListOf<Item>()
    private val listAll = mutableListOf<Item>()
    private val listFav = mutableListOf<Item>()

    private val adapterSearch by lazy {
        MyArrayAdapter(this, R.layout.listitem, listSearch) { db }
    }
    private val adapterAll by lazy {
        MyArrayAdapter(this, R.layout.listitem, listAll) { db }
    }
    private val adapterFav by lazy {
        MyArrayAdapter(this, R.layout.listitem, listFav) { db }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        setupTabs()
        setupEvents()

        // Nạp dữ liệu ban đầu
        loadAll()
        loadFavorites()
    }

    private fun bindViews() {
        btnXoa = findViewById(R.id.btnxoa)
        tabHost = findViewById(R.id.tabhost)
        edtTim = findViewById(R.id.edttim)
        lvSearch = findViewById(R.id.lv1)
        lvAll = findViewById(R.id.lv2)
        lvFav = findViewById(R.id.lv3)

        lvSearch.adapter = adapterSearch
        lvAll.adapter = adapterAll
        lvFav.adapter = adapterFav
    }

    private fun setupTabs() {
        tabHost.setup()

        val tab1 = tabHost.newTabSpec("t1").apply {
            setIndicator("Tìm")
            setContent(R.id.tab1)
        }
        val tab2 = tabHost.newTabSpec("t2").apply {
            setIndicator("DS")
            setContent(R.id.tab2)
        }
        val tab3 = tabHost.newTabSpec("t3").apply {
            setIndicator("Yêu thích")
            setContent(R.id.tab3)
        }
        tabHost.addTab(tab1)
        tabHost.addTab(tab2)
        tabHost.addTab(tab3)

        tabHost.setOnTabChangedListener { tabId ->
            when (tabId) {
                "t2" -> loadAll()
                "t3" -> loadFavorites()
            }
        }
    }

    private fun setupEvents() {
        btnXoa.setOnClickListener { edtTim.setText("") }

        edtTim.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s?.toString().orEmpty())
            }
        })
    }

    private fun performSearch(keyword: String) {
        listSearch.clear()
        if (keyword.isBlank()) {
            adapterSearch.notifyDataSetChanged()
            return
        }

        val like = "%$keyword%"
        val sql =
            "SELECT MABH, TENBH, YEUTHICH FROM ArirangSongList " +
                    "WHERE TENBH LIKE ? OR MABH LIKE ?"
        val c = db.rawQuery(sql, arrayOf(like, like))
        c.use {
            while (it.moveToNext()) {
                listSearch.add(
                    Item(
                        maso = it.getString(0),
                        tieude = it.getString(1),
                        thich = it.getInt(2)
                    )
                )
            }
        }
        adapterSearch.notifyDataSetChanged()
    }

    private fun loadAll() {
        listAll.clear()
        val c = db.rawQuery(
            "SELECT MABH, TENBH, YEUTHICH FROM ArirangSongList",
            null
        )
        c.use {
            while (it.moveToNext()) {
                listAll.add(
                    Item(
                        maso = it.getString(0),
                        tieude = it.getString(1),
                        thich = it.getInt(2)
                    )
                )
            }
        }
        adapterAll.notifyDataSetChanged()
    }

    private fun loadFavorites() {
        listFav.clear()
        val c = db.rawQuery(
            "SELECT MABH, TENBH, YEUTHICH FROM ArirangSongList WHERE YEUTHICH=1",
            null
        )
        c.use {
            while (it.moveToNext()) {
                listFav.add(
                    Item(
                        maso = it.getString(0),
                        tieude = it.getString(1),
                        thich = it.getInt(2)
                    )
                )
            }
        }
        adapterFav.notifyDataSetChanged()
    }
}
