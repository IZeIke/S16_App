package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;
import android.util.Log;

import com.example.haritmoolphunt.facebookfeed.dao.Datum;
import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;

import java.util.ArrayList;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FeedListManager {

    private static FeedListManager instance;

    public static FeedListManager getInstance() {
        if (instance == null)
            instance = new FeedListManager();
        return instance;
    }

    private Context mContext;
    private Posts dao;

    private FeedListManager() {
        mContext = Contextor.getInstance().getContext();
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
            dao.setData(new ArrayList<Datum>());
        }
        dao.getFeed().addAll(dao.getFeed().size(),newDao.getFeed());
        if(newDao.getMoreFeed().getNext() != null)
            dao.getMoreFeed().setNext(newDao.getMoreFeed().getNext());
        else{
            dao.getMoreFeed().setNext("end");
        }
    }
}
