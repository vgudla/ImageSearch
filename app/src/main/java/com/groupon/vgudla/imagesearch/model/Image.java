package com.groupon.vgudla.imagesearch.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Image implements Serializable {
    private String fullUrl;
    private String title;
    private String thumbNailUrl;

    public Image(String fullUrl, String title, String thumbNailUrl) {
        this.fullUrl = fullUrl;
        this.title = title;
        this.thumbNailUrl = thumbNailUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    public static List<Image> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Image> images = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String fullImageUrl = jsonObject.getString("url");
            String thumbNailImageUrl = jsonObject.getString("tbUrl");
            String title = jsonObject.getString("title");
            Image image = new Image(fullImageUrl, title, thumbNailImageUrl);
            images.add(image);
        }
        return images;
    }
}
