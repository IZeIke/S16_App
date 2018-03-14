package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;
import android.util.Log;

import com.example.haritmoolphunt.facebookfeed.dao.Accesstoken;
import com.example.haritmoolphunt.facebookfeed.manager.http.ApiService;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harit Moolphunt on 2/3/2561.
 */

public class AccessTokenManager {

    private static AccessTokenManager instance;
    private ApiService service;


    public static  AccessTokenManager getInstance() {
        if (instance == null)
            instance = new AccessTokenManager();
        return instance;
    }

    private Context mContext;

    private AccessTokenManager() {
        mContext = Contextor.getInstance().getContext();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://us-central1-idapi-808b6.cloudfunctions.net/")
                .build();

        service = retrofit.create(ApiService.class);
    }

    public ApiService getService(){
        return service;
    }


}
