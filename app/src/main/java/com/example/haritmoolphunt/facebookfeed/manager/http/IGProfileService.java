package com.example.haritmoolphunt.facebookfeed.manager.http;

import com.example.haritmoolphunt.facebookfeed.dao.ig_dao.IG_dao;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.Data;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.IG_feed_dao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Harit Moolphunt on 27/3/2561.
 */

public interface IGProfileService {
    @GET("{user}/?__a=1")
    Call<IG_dao> getIgProfileDao(@Path("user") String user);

    @GET("graphql/query/?query_hash=472f257a40c653c64c666ce877d59d2b")
    Call<IG_feed_dao> getIgFeedDao(@Query("variables") String id);

    @GET("graphql/query/?query_hash=472f257a40c653c64c666ce877d59d2b")
    Call<IG_feed_dao> getIgMoreFeedDao(@Query("variables") String id);
}
