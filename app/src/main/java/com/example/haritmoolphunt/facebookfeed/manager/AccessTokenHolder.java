package com.example.haritmoolphunt.facebookfeed.manager;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.AccessToken;

/**
 * Created by Harit Moolphunt on 19/1/2561.
 */

public class AccessTokenHolder implements Parcelable {
    AccessToken accessToken;

    protected AccessTokenHolder(Parcel in) {

    }

    public static final Creator<AccessTokenHolder> CREATOR = new Creator<AccessTokenHolder>() {
        @Override
        public AccessTokenHolder createFromParcel(Parcel in) {
            return new AccessTokenHolder(in);
        }

        @Override
        public AccessTokenHolder[] newArray(int size) {
            return new AccessTokenHolder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
