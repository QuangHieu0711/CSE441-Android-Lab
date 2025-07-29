package com.example.intentfilterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intentfilterapp.models.Student;
import com.example.intentfilterapp.adapters.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewStudents;
    private Button buttonOpenChild;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        listViewStudents = findViewById(R.id.lst_students);
        buttonOpenChild = findViewById(R.id.btnOpenChild);

        // Thiết lập sự kiện mở ChildActivity
        buttonOpenChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChildActivity.class);
                startActivity(intent);
            }
        });

        studentList = new ArrayList<>();
        studentList.add(new Student("Nguyễn Văn A", "12A1", R.drawable.avatar1));
        studentList.add(new Student("Trần Thị B", "12A2", R.drawable.avatar3));
        studentList.add(new Student("Lê Văn C", "12A3", R.drawable.avatar2));

        // Dữ liệu mẫu danh sách sinh viên
//        String[] studentNames = {
//                "Nguyen Quang Hieu",
//                "Tran Thi Mai",
//                "Le Van An",
//                "Pham Minh Chau",
//                "Hoang Thi Lan",
//                "Nguyen Van Binh",
//                "Vuong Quoc Duy",
//                "Nguyen Thi Hoa",
//                "Le Quang Huy",
//                "Pham Van Kien"
//        };

        // Thiết lập adapter cho ListView
        //StudentAdapter adapter = new StudentAdapter(this, studentNames);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        //listViewStudents.setAdapter(adapter);

        // Adapter thực tế được sử dụng:
        StudentAdapter adapter = new StudentAdapter(this, studentList);
        listViewStudents.setAdapter(adapter);
    }
}
