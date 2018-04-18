
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paging implements Parcelable{

    @SerializedName("cursors")
    @Expose
    private Cursors cursors;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private String previous;

    protected Paging(Parcel in) {
        cursors = in.readParcelable(Cursors.class.getClassLoader());
        next = in.readString();
        previous = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(cursors, flags);
        dest.writeString(next);
        dest.writeString(previous);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Paging> CREATOR = new Creator<Paging>() {
        @Override
        public Paging createFromParcel(Parcel in) {
            return new Paging(in);
        }

        @Override
        public Paging[] newArray(int size) {
            return new Paging[size];
        }
    };

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
