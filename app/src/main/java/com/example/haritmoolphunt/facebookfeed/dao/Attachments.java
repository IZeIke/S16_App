
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Attachments implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<AttachmentsData> data = null;

    protected Attachments(Parcel in) {
        data = in.createTypedArrayList(AttachmentsData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Attachments> CREATOR = new Creator<Attachments>() {
        @Override
        public Attachments createFromParcel(Parcel in) {
            return new Attachments(in);
        }

        @Override
        public Attachments[] newArray(int size) {
            return new Attachments[size];
        }
    };

    public List<AttachmentsData> getData() {
        return data;
    }

    public void setData(List<AttachmentsData> data) {
        this.data = data;
    }

}
