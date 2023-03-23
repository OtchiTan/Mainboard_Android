package com.otchi.mainboard.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.otchi.mainboard.R;
import com.otchi.mainboard.library.MagicPacket;

import java.io.IOException;

public class StartServerActivity extends AppCompatActivity {
    public String ip = "88.138.52.95";
    public String mac = "D8:97:BA:81:6A:9B";
    public int port = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_server);

        ImageView iv = findViewById(R.id.main_iv_powerOn);

        iv.setOnClickListener(view -> {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                MagicPacket.send(mac,ip,port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
