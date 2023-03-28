package com.otchi.mainboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.otchi.mainboard.R;
import com.otchi.mainboard.library.MagicPacket;
import com.otchi.mainboard.modele.Application;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartServerActivity extends AppCompatActivity {
    public String apiUrl = "http://otchi.ovh:3000/";
    public String ip = "88.138.52.95";
    public String mac = "D8:97:BA:81:6A:9B";
    public int port = 9;
    private boolean isCalling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_server);

        ImageView iv = findViewById(R.id.main_iv_powerOn);

        iv.setOnClickListener(view -> {
            if (!isCalling) {
                isCalling = true;
                getApi();
            }
        });
    }

    void getApi() {
        String url = apiUrl;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        ArrayList<Application> applications = new ArrayList<>();
                        JSONArray apps = response.getJSONArray("applications");
                        for (int i = 0; i < apps.length(); i++) {
                            applications.add(new Application(apps.getJSONObject(i)));
                        }
                        Intent intent = new Intent();
                        intent.putExtra("apps",applications);
                        setResult(RESULT_OK,intent);
                        finish();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    wakeOn();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        getApi();
                    },2000);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap headers = new HashMap();
                headers.put("platform", "MOBILE");
                return headers;
            }
        };

        requestQueue.add(request);
    }

    void wakeOn() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            MagicPacket.send(mac,ip,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
