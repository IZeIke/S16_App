package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;

import com.example.haritmoolphunt.facebookfeed.dao.video_dao.Video;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;

/**
 * Created by Harit Moolphunt on 14/3/2561.
 */

public class FeedVideoManager {

    private static FeedVideoManager instance;

    public static FeedVideoManager getInstance() {
        if (instance == null)
            instance = new FeedVideoManager();
        return instance;
    }

    private Context mContext;
    private Video dao;

    private FeedVideoManager() {
        mContext = Contextor.getInstance().getContext();
    }


    public Video getDao() {
        return dao;
    }

    public void setDao(Video dao) {
        this.dao = dao;
    }
}
