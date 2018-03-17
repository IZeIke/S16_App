
package com.example.haritmoolphunt.facebookfeed.dao.video_dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paging {

    @SerializedName("cursors")
    @Expose
    private Cursors cursors;
    @SerializedName("next")
    @Expose
    private String next;

    public Cursors getCursors() {
        return cursors;
    }

    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

}
