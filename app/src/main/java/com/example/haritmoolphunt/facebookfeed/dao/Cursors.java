
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cursors implements Parcelable{

    @SerializedName("before")
    @Expose
    private String before;
    @SerializedName("after")
    @Expose
    private String after;

    public Cursors(){

    }

    protected Cursors(Parcel in) {
        before = in.readString();
        after = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(before);
        dest.writeString(after);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cursors> CREATOR = new Creator<Cursors>() {
        @Override
        public Cursors createFromParcel(Parcel in) {
            return new Cursors(in);
        }

        @Override
        public Cursors[] newArray(int size) {
            return new Cursors[size];
        }
    };

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

}
