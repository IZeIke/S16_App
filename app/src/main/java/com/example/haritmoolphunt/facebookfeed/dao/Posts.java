
package com.example.haritmoolphunt.facebookfeed.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("paging")
    @Expose
    private Paging paging;

    public List<Datum> getFeed() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Paging getMoreFeed() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

}
