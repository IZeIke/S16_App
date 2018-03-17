
package com.example.haritmoolphunt.facebookfeed.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentsMedia {

    @SerializedName("image")
    @Expose
    private ImageSrc image;

    public ImageSrc getImage() {
        return image;
    }

    public void setImage(ImageSrc image) {
        this.image = image;
    }

}
