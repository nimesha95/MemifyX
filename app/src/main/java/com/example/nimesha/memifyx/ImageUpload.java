package com.example.nimesha.memifyx;

import android.media.Image;

/**
 * Created by Nimesha on 10/7/2017.
 */

public class ImageUpload {

    public String name;
    public String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ImageUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ImageUpload(){}
}
