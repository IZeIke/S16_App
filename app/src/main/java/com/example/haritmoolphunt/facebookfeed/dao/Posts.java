
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts implements Parcelable{

    @SerializedName("data")
    @Expose
    private List<FeedData> data = null;
    @SerializedName("paging")
    @Expose
    private Paging paging;

    public Posts(){

    }

    protected Posts(Parcel in) {
        data = in.createTypedArrayList(FeedData.CREATOR);
        paging = in.readParcelable(Paging.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
        dest.writeParcelable(paging, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Posts> CREATOR = new Creator<Posts>() {
        @Override
        public Posts createFromParcel(Parcel in) {
            return new Posts(in);
        }

        @Override
        public Posts[] newArray(int size) {
            return new Posts[size];
        }
    };

    public List<FeedData> getFeed() {
        return data;
    }

    public void setData(List<FeedData> data) {
        this.data = data;
    }

    public Paging getMoreFeed() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

}
