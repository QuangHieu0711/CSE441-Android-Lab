package com.example.songlisttabs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Item> {
    private final Activity context;
    private final int layoutId;
    private final ArrayList<Item> items;

    public MyArrayAdapter(Activity context, int layoutId, ArrayList<Item> items) {
        super(context, layoutId, items);
        this.context = context;
        this.layoutId = layoutId;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        Item item = items.get(position);
        TextView txtMaso = convertView.findViewById(R.id.txtmaso);
        TextView txtTieude = convertView.findViewById(R.id.txttieude);
        ImageView btnLike = convertView.findViewById(R.id.btnlike);

        txtMaso.setText(item.getMaso());
        txtTieude.setText(item.getTieude());

        btnLike.setImageResource(item.getThich() == 1 ? R.drawable.like : R.drawable.unlike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setThich(item.getThich() == 1 ? 0 : 1);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
