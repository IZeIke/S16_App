
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentsData implements Parcelable{

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

    protected AttachmentsData(Parcel in) {
        title = in.readString();
        type = in.readString();
        url = in.readString();
        subattachments = in.readParcelable(Subattachments.class.getClassLoader());
        media = in.readParcelable(AttachmentsMedia.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeParcelable(subattachments, flags);
        dest.writeParcelable(media, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttachmentsData> CREATOR = new Creator<AttachmentsData>() {
        @Override
        public AttachmentsData createFromParcel(Parcel in) {
            return new AttachmentsData(in);
        }

        @Override
        public AttachmentsData[] newArray(int size) {
            return new AttachmentsData[size];
        }
    };

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
