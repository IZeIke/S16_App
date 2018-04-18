
package com.example.haritmoolphunt.facebookfeed.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentsMedia implements Parcelable{

    @SerializedName("image")
    @Expose
    private ImageSrc image;

    protected AttachmentsMedia(Parcel in) {
        image = in.readParcelable(ImageSrc.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(image, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttachmentsMedia> CREATOR = new Creator<AttachmentsMedia>() {
        @Override
        public AttachmentsMedia createFromParcel(Parcel in) {
            return new AttachmentsMedia(in);
        }

        @Override
        public AttachmentsMedia[] newArray(int size) {
            return new AttachmentsMedia[size];
        }
    };

    public ImageSrc getImage() {
        return image;
    }

    public void setImage(ImageSrc image) {
        this.image = image;
    }

}
