package com.otchi.mainboard.modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Application implements Serializable {
    public int id;
    public String name;
    public String image;

    public Application(JSONObject json) {
        try {
            id = json.getInt("id");
            name = json.getString("name");
            if (json.has("image"))
                image = json.getString("image");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
