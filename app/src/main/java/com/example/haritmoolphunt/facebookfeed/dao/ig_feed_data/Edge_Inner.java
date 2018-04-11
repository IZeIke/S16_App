
package com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Edge_Inner {

    @SerializedName("node")
    @Expose
    private Text node;

    public Text getNode() {
        return node;
    }

    public void setNode(Text node) {
        this.node = node;
    }

}
