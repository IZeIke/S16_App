package com.example.haritmoolphunt.facebookfeed.manager;

import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by Harit Moolphunt on 6/3/2561.
 */

public class SearchModel implements Searchable {
    private String mTitle;
    private String mId;

    public SearchModel(String title,String id) {
        mTitle = title;
        mId = id;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public String getId() {
        return mId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setId(String id)
    {
        mId = id;
    }
}
