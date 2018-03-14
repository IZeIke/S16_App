package com.example.haritmoolphunt.facebookfeed.dao;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Harit Moolphunt on 2/3/2561.
 */

public class Accesstoken {
    @SerializedName("app-id")
    @Expose
    private String appId;
    @SerializedName("app-secret")
    @Expose
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
