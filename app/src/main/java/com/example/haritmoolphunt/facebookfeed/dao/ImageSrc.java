
package com.example.haritmoolphunt.facebookfeed.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageSrc {

    @SerializedName("src")
    @Expose
    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
