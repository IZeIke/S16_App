
package com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EdgeMediaToComment {

    @SerializedName("count")
    @Expose
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
