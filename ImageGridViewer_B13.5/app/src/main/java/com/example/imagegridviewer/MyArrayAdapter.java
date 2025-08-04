package com.example.imagegridviewer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Image> {
    Activity context;
    int layoutId;
    ArrayList<Image> arrImage;

    public MyArrayAdapter(Activity context, int layoutId, ArrayList<Image> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.arrImage = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        ImageView img = convertView.findViewById(R.id.imageView1);
        TextView name = convertView.findViewById(R.id.textView1);

        img.setImageResource(arrImage.get(position).getImg());
        name.setText(arrImage.get(position).getName());

        return convertView;
    }
}
