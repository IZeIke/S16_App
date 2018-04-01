
package com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Edge_ {

    @SerializedName("node")
    @Expose
    private Node_ node;

    public Node_ getNode() {
        return node;
    }

    public void setNode(Node_ node) {
        this.node = node;
    }

}
