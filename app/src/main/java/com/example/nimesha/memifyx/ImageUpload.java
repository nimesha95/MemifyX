package com.example.nimesha.memifyx;

import android.media.Image;

/**
 * Created by Nimesha on 10/7/2017.
 */

public class ImageUpload {

    public String name;
    public String url;
    public int like;
    public String key;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getLike() {
        return like;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ImageUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ImageUpload(String name, String url, int like) {
        this.name = name;
        this.url = url;
        this.like = like;
    }

    public ImageUpload(){}
}


