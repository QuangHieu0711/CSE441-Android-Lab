package com.example.musicserviceapp;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    MediaPlayer mymedia;

    @Override
    public IBinder onBind(Intent intent) {
        // Không sử dụng bind trong ví dụ này
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Khởi tạo MediaPlayer với tệp nhạc trong thư mục raw
        mymedia = MediaPlayer.create(this, R.raw.thuongemhonchinhanh); // đổi tên nếu cần
        mymedia.setLooping(true); // Lặp lại liên tục
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Khi startService được gọi
        if (mymedia != null) {
            if (mymedia.isPlaying()) {
                mymedia.pause();
            } else {
                mymedia.start();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Khi stopService được gọi
        if (mymedia != null && mymedia.isPlaying()) {
            mymedia.stop();
            mymedia.release();
            mymedia = null;
        }
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
