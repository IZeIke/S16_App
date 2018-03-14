package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;

import com.example.haritmoolphunt.facebookfeed.template.Contextor;
import com.facebook.AccessToken;

/**
 * Created by Harit Moolphunt on 2/3/2561.
 */

public class AppTokenManager {
    private static AppTokenManager instance;
    private AccessToken accessToken;

    public static  AppTokenManager getInstance() {
        if (instance == null)
            instance = new AppTokenManager();
        return instance;
    }

    private Context mContext;

    private AppTokenManager() {
        mContext = Contextor.getInstance().getContext();

    }

    public void setAccessToken(AccessToken ac){
        accessToken = ac;
    }

    public AccessToken getAccessToken(){
        return accessToken;
    }
}
