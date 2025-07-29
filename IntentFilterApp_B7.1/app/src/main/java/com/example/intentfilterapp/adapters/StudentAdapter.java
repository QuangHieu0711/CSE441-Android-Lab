package com.example.intentfilterapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.intentfilterapp.R;
import com.example.intentfilterapp.models.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;
    private List<Student> students;

    public StudentAdapter(Context context, List<Student> students) {
        super(context, 0, students);
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = students.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_student, parent, false);
        }

        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtClass = convertView.findViewById(R.id.txtClass);

        imgAvatar.setImageResource(student.getAvatarResId());
        txtName.setText(student.getName());
        txtClass.setText(student.getStudentClass());

        return convertView;
    }
}
