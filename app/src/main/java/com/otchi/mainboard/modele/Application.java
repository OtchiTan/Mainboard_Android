package com.otchi.mainboard.modele;

import org.json.JSONException;
import org.json.JSONObject;

public class Application {
    String name;
    String image;

    public Application(JSONObject json) {
        try {
            name = json.getString("name");
            if (json.has("image"))
                image = json.getString("image");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
