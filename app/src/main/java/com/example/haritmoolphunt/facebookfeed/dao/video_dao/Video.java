
package com.example.haritmoolphunt.facebookfeed.dao.video_dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("paging")
    @Expose
    private Paging paging;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

}
