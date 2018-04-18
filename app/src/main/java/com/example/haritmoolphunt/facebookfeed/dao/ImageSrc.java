
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageSrc implements Parcelable{

    @SerializedName("src")
    @Expose
    private String src;

    protected ImageSrc(Parcel in) {
        src = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(src);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageSrc> CREATOR = new Creator<ImageSrc>() {
        @Override
        public ImageSrc createFromParcel(Parcel in) {
            return new ImageSrc(in);
        }

        @Override
        public ImageSrc[] newArray(int size) {
            return new ImageSrc[size];
        }
    };

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
