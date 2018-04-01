
package com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EdgeMediaToCaption {

    @SerializedName("edges")
    @Expose
    private List<Edge_> edges = null;

    public List<Edge_> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge_> edges) {
        this.edges = edges;
    }

}
