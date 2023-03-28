package com.otchi.mainboard;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.otchi.mainboard.activity.StartServerActivity;
import com.otchi.mainboard.library.AppGridAdapter;
import com.otchi.mainboard.modele.Application;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public String apiUrl = "http://otchi.ovh:3000/";
    public ArrayList<Application> applications;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                applications = (ArrayList<Application>) data.getSerializableExtra("apps");
                showApps();
            }
        });

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
                        showApps();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    error.printStackTrace();
                    if (error.networkResponse == null) {
                        Intent intent = new Intent(this, StartServerActivity.class);
                        launcher.launch(intent);
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

    void showApps() {
        GridView gridView = findViewById(R.id.main_gv_apps);

        AppGridAdapter arrayAdapter = new AppGridAdapter(this,applications);
        gridView.setAdapter(arrayAdapter);

        gridView.setOnItemClickListener((a, v, position, id) -> {
            Application app = (Application) gridView.getItemAtPosition(position);

            Toast.makeText(MainActivity.this, "Selected : " + app.name,Toast.LENGTH_LONG).show();
        });
    }
}