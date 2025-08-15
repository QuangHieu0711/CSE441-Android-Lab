package com.example.vnexpressrss

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ArticleAdapter(
    private val act: Activity,
    private val layoutId: Int,
    private val data: MutableList<Article>
) : ArrayAdapter<Article>(act, layoutId, data) {

    override fun getCount() = data.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutId, parent, false)
        val img = view.findViewById<ImageView>(R.id.imgThumb)
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val sum = view.findViewById<TextView>(R.id.txtSummary)

        val item = data[position]
        title.text = item.title
        sum.text = item.summary
        img.setImageResource(R.mipmap.ic_launcher) // tạm thời: không tải ảnh để loại trừ nguyên nhân treo

        view.setOnClickListener {
            act.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
        }
        return view
    }
}
