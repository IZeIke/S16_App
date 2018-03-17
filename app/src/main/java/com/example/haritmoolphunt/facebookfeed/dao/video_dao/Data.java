
package com.example.haritmoolphunt.facebookfeed.dao.video_dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("id")
    @Expose
    private String id;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
