
package com.example.haritmoolphunt.facebookfeed.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture {

    @SerializedName("data")
    @Expose
    private PhotoData data;

    public PhotoData getData() {
        return data;
    }

    public void setData(PhotoData data) {
        this.data = data;
    }

}
