
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subattachments implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<MediaData> data = null;

    protected Subattachments(Parcel in) {
        data = in.createTypedArrayList(MediaData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subattachments> CREATOR = new Creator<Subattachments>() {
        @Override
        public Subattachments createFromParcel(Parcel in) {
            return new Subattachments(in);
        }

        @Override
        public Subattachments[] newArray(int size) {
            return new Subattachments[size];
        }
    };

    public List<MediaData> getData() {
        return data;
    }

    public void setData(List<MediaData> data) {
        this.data = data;
    }

}
