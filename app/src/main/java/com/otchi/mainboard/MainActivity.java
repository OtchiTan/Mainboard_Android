package com.otchi.mainboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.otchi.mainboard.activity.HomeActivity;
import com.otchi.mainboard.library.MagicPacket;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public String apiUrl = "http://192.168.1.38:3000/";
    public String ip = "88.138.52.95";
    public String mac = "D8:97:BA:81:6A:9B";
    public int port = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = apiUrl;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> onServerOnline(),
                error -> {
                    error.printStackTrace();
                    if (error.networkResponse == null) {
                        onServerOffline();
                    } else {
                        Toast toast = new Toast(this);
                        toast.setText("c la merde");
                        toast.show();
                    }
                }
        );

        requestQueue.add(request);
    }

    void onServerOffline() {
        TextView tv = findViewById(R.id.main_tv_checkOnline);
        tv.setVisibility(View.GONE);
        ImageView iv = findViewById(R.id.main_iv_powerOn);
        iv.setVisibility(View.VISIBLE);

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

    void onServerOnline() {
        startActivity(new Intent(this, HomeActivity.class));
    }
}