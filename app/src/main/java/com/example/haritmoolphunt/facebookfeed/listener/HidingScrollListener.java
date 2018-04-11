package com.example.haritmoolphunt.facebookfeed.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Harit Moolphunt on 24/2/2561.
 */

public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    int sum_dy = 0;
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        Log.d("ckeck",dy+"");
        Log.d("sum ckeck",sum_dy+"");
        //show views if first item is first visible position and views are hidden
        if (firstVisibleItem == 0 && sum_dy < 60) {
            if(dy > 0)
                sum_dy+=dy;

            if(!controlsVisible) {
                onShow();
                controlsVisible = true;
            }
        } else {
            if(firstVisibleItem != 0)
            {
                sum_dy = 0;
            }
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                onHide();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                onShow();
                controlsVisible = true;
                scrolledDistance = 0;
            }
        }

        if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onHide();
    public abstract void onShow();

}
