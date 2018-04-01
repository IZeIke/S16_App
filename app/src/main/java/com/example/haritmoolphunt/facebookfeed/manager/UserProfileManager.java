package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;

import com.example.haritmoolphunt.facebookfeed.dao.PageProfile;
import com.example.haritmoolphunt.facebookfeed.dao.UserProfile;
import com.example.haritmoolphunt.facebookfeed.dao.ig_dao.IG_dao;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;

/**
 * Created by Harit Moolphunt on 2/2/2561.
 */

public class UserProfileManager {
    private static UserProfileManager instance;

    public static UserProfileManager getInstance() {
        if (instance == null)
            instance = new UserProfileManager();
        return instance;
    }

    private Context mContext;
    private UserProfile dao;
    private IG_dao ig_dao;

    private UserProfileManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public UserProfile getDao() {
        return dao;
    }

    public void setDao(UserProfile dao) {
        this.dao = dao;
    }

    public IG_dao getIg_dao() {
        return ig_dao;
    }

    public void setIg_dao(IG_dao ig_dao) {
        this.ig_dao = ig_dao;
    }
}
