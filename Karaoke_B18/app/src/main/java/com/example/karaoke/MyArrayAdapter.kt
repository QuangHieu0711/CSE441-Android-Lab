package com.example.karaoke

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyArrayAdapter(
    private val ctx: Context,
    private val layoutId: Int,
    private val arr: MutableList<Item>,
    private val dbProvider: () -> android.database.sqlite.SQLiteDatabase
) : ArrayAdapter<Item>(ctx, layoutId, arr) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(ctx)
        val row = convertView ?: inflater.inflate(layoutId, parent, false)

        val item = arr[position]

        val tvTieuDe = row.findViewById<TextView>(R.id.txttieude)
        val tvMaSo = row.findViewById<TextView>(R.id.txtmaso)
        val btnLike = row.findViewById<ImageView>(R.id.btnlike)
        val btnUnlike = row.findViewById<ImageView>(R.id.btnunlike)

        tvTieuDe.text = item.tieude
        tvMaSo.text = item.maso

        // Hiển thị icon theo trạng thái
        fun renderLikeState() {
            if (item.thich == 1) {
                btnLike.visibility = View.VISIBLE
                btnUnlike.visibility = View.INVISIBLE
            } else {
                btnLike.visibility = View.INVISIBLE
                btnUnlike.visibility = View.VISIBLE
            }
        }
        renderLikeState()

        // Toggle like → update DB
        fun updateFavorite(newVal: Int) {
            item.thich = newVal
            val values = ContentValues().apply { put("YEUTHICH", newVal) }
            dbProvider().update(
                "ArirangSongList",
                values,
                "MABH=?",
                arrayOf(item.maso)
            )
            renderLikeState()
        }

        btnLike.setOnClickListener { updateFavorite(0) }
        btnUnlike.setOnClickListener { updateFavorite(1) }

        // Mở chi tiết SubActivity khi chạm vào dòng
        row.setOnClickListener {
            val intent = Intent(ctx, SubActivity::class.java).apply {
                putExtra("maso", item.maso)
            }
            ctx.startActivity(intent)
        }

        return row
    }
}
