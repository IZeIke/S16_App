
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaData implements Parcelable{

    @SerializedName("media")
    @Expose
    private Media media;

    protected MediaData(Parcel in) {
        media = in.readParcelable(Media.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(media, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaData> CREATOR = new Creator<MediaData>() {
        @Override
        public MediaData createFromParcel(Parcel in) {
            return new MediaData(in);
        }

        @Override
        public MediaData[] newArray(int size) {
            return new MediaData[size];
        }
    };

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

}
