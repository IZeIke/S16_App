
package com.example.haritmoolphunt.facebookfeed.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentsData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("subattachments")
    @Expose
    private Subattachments subattachments;
    @SerializedName("media")
    @Expose
    private AttachmentsMedia media;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Subattachments getSubattachments() {
        return subattachments;
    }

    public void setSubattachments(Subattachments subattachments) {
        this.subattachments = subattachments;
    }

    public AttachmentsMedia getMedia() {
        return media;
    }

    public void setMedia(AttachmentsMedia media) {
        this.media = media;
    }

}