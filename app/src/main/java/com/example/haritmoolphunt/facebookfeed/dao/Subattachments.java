
package com.example.haritmoolphunt.facebookfeed.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subattachments {

    @SerializedName("data")
    @Expose
    private List<MediaData> data = null;

    public List<MediaData> getData() {
        return data;
    }

    public void setData(List<MediaData> data) {
        this.data = data;
    }

}
