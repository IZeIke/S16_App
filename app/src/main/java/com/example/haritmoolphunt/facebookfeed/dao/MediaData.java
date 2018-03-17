
package com.example.haritmoolphunt.facebookfeed.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaData {

    @SerializedName("media")
    @Expose
    private Media media;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

}
