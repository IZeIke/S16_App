package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;

import com.example.haritmoolphunt.facebookfeed.template.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RecyclerviewPosition {

    private static RecyclerviewPosition instance;

    public static RecyclerviewPosition getInstance() {
        if (instance == null)
            instance = new RecyclerviewPosition();
        return instance;
    }

    private Context mContext;
    private int position;

    private RecyclerviewPosition() {
        mContext = Contextor.getInstance().getContext();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
