
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedData implements Parcelable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("attachments")
    @Expose
    private Attachments attachments;

    protected FeedData(Parcel in) {
        id = in.readString();
        createdTime = in.readString();
        message = in.readString();
        attachments = in.readParcelable(Attachments.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createdTime);
        dest.writeString(message);
        dest.writeParcelable(attachments, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedData> CREATOR = new Creator<FeedData>() {
        @Override
        public FeedData createFromParcel(Parcel in) {
            return new FeedData(in);
        }

        @Override
        public FeedData[] newArray(int size) {
            return new FeedData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

}
