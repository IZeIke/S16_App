package com.example.haritmoolphunt.facebookfeed.manager.http;

import com.example.haritmoolphunt.facebookfeed.dao.Accesstoken;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Harit Moolphunt on 2/3/2561.
 */

public interface ApiService {
    @GET("appid")
    Call<Accesstoken> getId();
}


