// MainActivity.java - Project: SongListTabs
package com.example.songlisttabs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.content.res.AppCompatResources;
import android.text.Editable;
import android.text.TextWatcher;
import java.util.ArrayList;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends Activity {
    EditText edtTim;
    ListView lv1, lv2, lv3;
    TabLayout tabLayout;
    ArrayList<Item> allSongs;
    ArrayList<Item> searchSongs, favouriteSongs;
    MyArrayAdapter searchAdapter, listAdapter, favouriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
    }

    private void addControl() {
        tabLayout = findViewById(R.id.tabLayout);
        edtTim = findViewById(R.id.edttim);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);

        // Thêm tab với icon và text
        tabLayout.addTab(tabLayout.newTab().setText("Tìm bài").setIcon(android.R.drawable.ic_menu_search));
        tabLayout.addTab(tabLayout.newTab().setText("Danh sách").setIcon(android.R.drawable.ic_menu_agenda));
        tabLayout.addTab(tabLayout.newTab().setText("Yêu thích").setIcon(android.R.drawable.star_on));

        // Danh sách chung
        allSongs = new ArrayList<>();
        allSongs.add(new Item("52300", "Em là tất cả", 0));
        allSongs.add(new Item("52068", "Buồn của anh", 1));
        allSongs.add(new Item("57326", "Gói em đi cuối sông Hồng", 0));
        allSongs.add(new Item("51548", "Em ơi đi", 0));
        allSongs.add(new Item("57869", "Hát với dòng sông", 1));
        allSongs.add(new Item("58716", "Say tình - Remix", 0));
        allSongs.add(new Item("60001", "Chúng ta của hiện tại", 1));
        allSongs.add(new Item("60002", "3107", 1));
        allSongs.add(new Item("60003", "Lạ lùng", 0));
        allSongs.add(new Item("60004", "Có hẹn với thanh xuân", 0));
        allSongs.add(new Item("60005", "Từng quen", 0));
        allSongs.add(new Item("60006", "Nơi này có anh", 1));
        allSongs.add(new Item("60007", "Tháng tư là lời nói dối của em", 0));
        allSongs.add(new Item("60008", "Chạy ngay đi", 1));


        // Danh sách cho từng tab
        searchSongs = new ArrayList<>();
        favouriteSongs = new ArrayList<>();

        searchAdapter = new MyArrayAdapter(this, R.layout.listitem, searchSongs);
        listAdapter = new MyArrayAdapter(this, R.layout.listitem, allSongs);
        favouriteAdapter = new MyArrayAdapter(this, R.layout.listitem, favouriteSongs);

        lv1.setAdapter(searchAdapter);
        lv2.setAdapter(listAdapter);
        lv3.setAdapter(favouriteAdapter);
    }

    private void addEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                lv1.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                lv2.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
                lv3.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                if (position == 0) {
                    String keyword = edtTim.getText().toString().trim().toLowerCase();
                    searchSongs.clear();
                    for (Item item : allSongs) {
                        if (item.getTieude().toLowerCase().contains(keyword) || item.getMaso().contains(keyword)) {
                            searchSongs.add(item);
                        }
                    }
                    searchAdapter.notifyDataSetChanged();
                }
                if (position == 1) {
                    listAdapter.notifyDataSetChanged();
                }
                if (position == 2) {
                    favouriteSongs.clear();
                    for (Item item : allSongs) {
                        if (item.getThich() == 1) {
                            favouriteSongs.add(item);
                        }
                    }
                    favouriteAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Sự kiện tìm kiếm động
        edtTim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim().toLowerCase();
                searchSongs.clear();
                for (Item item : allSongs) {
                    if (item.getTieude().toLowerCase().contains(keyword) || item.getMaso().contains(keyword)) {
                        searchSongs.add(item);
                    }
                }
                searchAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
