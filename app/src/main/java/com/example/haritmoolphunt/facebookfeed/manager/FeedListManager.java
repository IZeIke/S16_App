package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;
import android.util.Log;

import com.example.haritmoolphunt.facebookfeed.dao.FeedData;
import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;

import org.cryse.widget.persistentsearch.SearchItem;

import java.util.ArrayList;
import java.util.List;

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
    private String[] nameList;
    private String[] nameId;

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
            dao.setData(new ArrayList<FeedData>());
        }
        dao.getFeed().addAll(dao.getFeed().size(),newDao.getFeed());
        if(newDao.getMoreFeed().getNext() != null)
            dao.getMoreFeed().setNext(newDao.getMoreFeed().getNext());
        else{
            dao.getMoreFeed().setNext("end");
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
}
