package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;

import com.example.haritmoolphunt.facebookfeed.dao.PageProfile;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;

/**
 * Created by Harit Moolphunt on 24/1/2561.
 */

public class PageProfileManager {

    private static PageProfileManager instance;

    public static PageProfileManager getInstance() {
        if (instance == null)
            instance = new PageProfileManager();
        return instance;
    }

    private Context mContext;
    private PageProfile dao;

    private PageProfileManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PageProfile getDao() {
        return dao;
    }

    public void setDao(PageProfile dao) {
        this.dao = dao;
    }
}
