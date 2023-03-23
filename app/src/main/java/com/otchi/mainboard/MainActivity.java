package com.otchi.mainboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.otchi.mainboard.activity.StartServerActivity;
import com.otchi.mainboard.modele.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public String apiUrl = "http://192.168.1.38:3001/";
    public ArrayList<Application> applications;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = apiUrl;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray apps = response.getJSONArray("applications");
                        this.applications = new ArrayList<>();
                        for (int i = 0; i < apps.length(); i++) {
                            this.applications.add(new Application(apps.getJSONObject(i)));
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    error.printStackTrace();
                    if (error.networkResponse == null) {
                        Intent intent = new Intent(this, StartServerActivity.class);
                        startActivity(intent);
                    } else {
                        Toast toast = new Toast(this);
                        toast.setText("c la merde");
                        toast.show();
                    }
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
}