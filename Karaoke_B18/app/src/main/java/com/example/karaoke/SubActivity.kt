package com.example.karaoke

import android.content.ContentValues
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {

    private lateinit var txtMaso: TextView
    private lateinit var txtTieuDe: TextView
    private lateinit var txtLoiBaiHat: TextView
    private lateinit var txtTacGia: TextView
    private lateinit var btnThich: ImageButton
    private lateinit var btnKhongThich: ImageButton

    private val db by lazy { DatabaseAssetHelper(this).openDatabase() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subactivity)

        txtMaso = findViewById(R.id.txtmaso)
        txtTieuDe = findViewById(R.id.txttieude)
        txtLoiBaiHat = findViewById(R.id.txtloibaihat)
        txtTacGia = findViewById(R.id.txttacgia)
        btnThich = findViewById(R.id.btnthich)
        btnKhongThich = findViewById(R.id.btnkhongthich)

        val mabaihat = intent.getStringExtra("maso") ?: return
        loadDetail(mabaihat)

        btnThich.setOnClickListener { setFavorite(mabaihat, 0) }
        btnKhongThich.setOnClickListener { setFavorite(mabaihat, 1) }
    }

    private fun loadDetail(maso: String) {
        val c = db.rawQuery(
            "SELECT * FROM ArirangSongList WHERE MABH = ?", arrayOf(maso)
        )
        c.use {
            if (it.moveToFirst()) {
                txtMaso.text = "#$maso"
                txtTieuDe.text = it.getString(it.getColumnIndexOrThrow("TENBH"))
                txtLoiBaiHat.text = it.getString(it.getColumnIndexOrThrow("LoiBH"))
                txtTacGia.text = it.getString(it.getColumnIndexOrThrow("TacGia"))

                val yeuThich = it.getInt(it.getColumnIndexOrThrow("YEUTHICH"))
                if (yeuThich == 1) {
                    btnThich.visibility = android.view.View.INVISIBLE
                    btnKhongThich.visibility = android.view.View.VISIBLE
                } else {
                    btnThich.visibility = android.view.View.VISIBLE
                    btnKhongThich.visibility = android.view.View.INVISIBLE
                }
            }
        }
    }

    private fun setFavorite(maso: String, value: Int) {
        val values = ContentValues().apply { put("YEUTHICH", value) }
        db.update("ArirangSongList", values, "MABH=?", arrayOf(maso))
        loadDetail(maso)
    }
}
