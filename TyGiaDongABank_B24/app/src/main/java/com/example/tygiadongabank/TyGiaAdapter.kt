package com.example.tygiadongabank

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class TyGiaAdapter(
    context: Context,
    private val resource: Int,
    private val data: MutableList<TyGia>
) : ArrayAdapter<TyGia>(context, resource, data) {

    private class ViewHolder(v: View) {
        val img: ImageView = v.findViewById(R.id.imgminh)
        val txtType: TextView = v.findViewById(R.id.txtType)
        val txtMuaTM: TextView = v.findViewById(R.id.txtMuaTM)
        val txtMuaCK: TextView = v.findViewById(R.id.txtMuaCK)
        val txtBanTM: TextView = v.findViewById(R.id.txtBanTM)
        val txtBanCK: TextView = v.findViewById(R.id.txtBanCK)
        val txtBANK: TextView = v.findViewById(R.id.txtBANK)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView: View
        val holder: ViewHolder
        if (convertView == null) {
            rowView = LayoutInflater.from(context).inflate(resource, parent, false)
            holder = ViewHolder(rowView)
            rowView.tag = holder
        } else {
            rowView = convertView
            holder = convertView.tag as ViewHolder
        }

        val item = data[position]
        holder.txtType.text = item.type
        holder.txtMuaTM.text = "MuaTM: ${item.muatienmat}"
        holder.txtMuaCK.text = "MuaCK: ${item.muack}"
        holder.txtBanTM.text = "BanTM: ${item.bantienmat}"
        holder.txtBanCK.text = "BanCK: ${item.banck}"
        holder.txtBANK.text = item.bank
        holder.img.setImageBitmap(item.bitmap)

        return rowView
    }

    fun replaceAll(newItems: List<TyGia>) {
        clear()
        addAll(newItems)
        notifyDataSetChanged()
    }
}
