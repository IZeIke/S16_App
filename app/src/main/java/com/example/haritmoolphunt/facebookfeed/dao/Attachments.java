
package com.example.haritmoolphunt.facebookfeed.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Attachments {

    @SerializedName("data")
    @Expose
    private List<AttachmentsData> data = null;

    public List<AttachmentsData> getData() {
        return data;
    }

    public void setData(List<AttachmentsData> data) {
        this.data = data;
    }

}
