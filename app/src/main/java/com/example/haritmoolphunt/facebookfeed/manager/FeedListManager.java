package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.haritmoolphunt.facebookfeed.dao.FeedData;
import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.Data;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.Edge;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.IG_feed_dao;
import com.example.haritmoolphunt.facebookfeed.manager.http.IGProfileService;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;
import com.example.haritmoolphunt.facebookfeed.template.state.BundleSavedState;

import org.cryse.widget.persistentsearch.SearchItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedListManager {

    private static FeedListManager instance;
    private IGProfileService service;

    public static FeedListManager getInstance() {
        if (instance == null)
            instance = new FeedListManager();
        return instance;
    }

    private Context mContext;
    private Posts dao;
    private IG_feed_dao ig_dao;

    private String[] nameList;
    private String[] nameId;

    private FeedListManager() {
        mContext = Contextor.getInstance().getContext();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.instagram.com/")
                .build();

        service = retrofit.create(IGProfileService.class);
    }

    public Posts getDao() {
        return dao;
    }

    public void setDao(Posts dao) {
        this.dao = dao;
    }

    public void addDaoAtButtomPosition(Posts newDao)
    {
        if(dao == null)
        {
            dao = new Posts();
        }
        if(dao.getFeed() == null)
        {
            dao.setData(new ArrayList<FeedData>());
        }
        dao.getFeed().addAll(dao.getFeed().size(),newDao.getFeed());
        if(newDao.getMoreFeed().getNext() != null)
            dao.getMoreFeed().setNext(newDao.getMoreFeed().getNext());
        else{
            dao.getMoreFeed().setNext("end");
        }
    }

    public void addIgDaoAtButtomPosition(IG_feed_dao newDao)
    {
        if(ig_dao == null)
        {
            ig_dao = new IG_feed_dao();
        }
        if(ig_dao.getData() == null)
        {
            ig_dao.getData().getUser().getEdgeOwnerToTimelineMedia().setEdges(new ArrayList<Edge>());
        }

        ig_dao.getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().addAll(ig_dao.getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().size()
                ,newDao.getData().getUser().getEdgeOwnerToTimelineMedia().getEdges());

        if(newDao.getData().getUser().getEdgeOwnerToTimelineMedia().getPageInfo().getHasNextPage() != false)
            ig_dao.getData().getUser().getEdgeOwnerToTimelineMedia().getPageInfo().setEndCursor(newDao.getData().getUser().getEdgeOwnerToTimelineMedia().getPageInfo().getEndCursor());
        else{
            ig_dao.getData().getUser().getEdgeOwnerToTimelineMedia().getPageInfo().setEndCursor("end");
        }
    }

    public void setmHistorySuggestions(String[] namelist,String[] nameId) {
        this.nameId = nameId;
        this.nameList = namelist;
    }

    public String[] getNameList() {
        return nameList;
    }

    public String[] getNameId() {
        return nameId;
    }

    public IG_feed_dao getIg_dao() {
        return ig_dao;
    }

    public void setIg_dao(IG_feed_dao ig_dao) {
        this.ig_dao = ig_dao;
    }

    public IGProfileService getService() {
        return service;
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao",dao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        dao = savedInstanceState.getParcelable("dao");
    }

}
