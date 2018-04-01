
package com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EdgeOwnerToTimelineMedia {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("page_info")
    @Expose
    private PageInfo pageInfo;
    @SerializedName("edges")
    @Expose
    private List<Edge> edges = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

}
