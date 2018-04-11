
package com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EdgeMediaToCaption {

    @SerializedName("edges")
    @Expose
    private List<Edge_Inner> edges = null;

    public List<Edge_Inner> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge_Inner> edges) {
        this.edges = edges;
    }

}
