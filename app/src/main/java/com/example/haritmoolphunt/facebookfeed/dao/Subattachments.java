
package com.example.haritmoolphunt.facebookfeed.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subattachments {

    @SerializedName("data")
    @Expose
    private List<Datum__> data = null;

    public List<Datum__> getData() {
        return data;
    }

    public void setData(List<Datum__> data) {
        this.data = data;
    }

}
