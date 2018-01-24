package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;

import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;

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
}
