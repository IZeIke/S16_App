package com.example.haritmoolphunt.facebookfeed.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.haritmoolphunt.facebookfeed.LayoutManager.SpeedyLinearLayoutManager;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.activity.MainActivity;
import com.example.haritmoolphunt.facebookfeed.adapter.FeedListAdapter;
import com.example.haritmoolphunt.facebookfeed.adapter.IgFeedAdapter;
import com.example.haritmoolphunt.facebookfeed.dao.ig_dao.IG_dao;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.Data;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.IG_feed_dao;
import com.example.haritmoolphunt.facebookfeed.event.BusEvent;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;

import com.example.haritmoolphunt.facebookfeed.manager.RecyclerviewPosition;
import com.example.haritmoolphunt.facebookfeed.manager.UserProfileManager;
import com.example.haritmoolphunt.facebookfeed.manager.helper.InternetCheck;
import com.example.haritmoolphunt.facebookfeed.manager.helper.NameListCollector;
import com.example.haritmoolphunt.facebookfeed.manager.http.IGProfileService;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;
import com.facebook.AccessToken;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import javax.xml.datatype.Duration;

import cn.jzvd.JZVideoPlayer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harit Moolphunt on 24/3/2561.
 */

public class IgFeedFragment extends Fragment{

    String pageName;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLoadingMore = false;
    IgFeedAdapter igFeedAdapter;
    String[] parts;

    public IgFeedFragment() {
        super();
    }

    public static IgFeedFragment newInstance(String pageid) {
        IgFeedFragment fragment = new IgFeedFragment();
        Bundle args = new Bundle();
        args.putString("pageID",pageid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageName = getArguments().getString("pageID");

        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        parts = pageName.split(",");
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState]
        recyclerView = rootView.findViewById(R.id.recyclerview);
        igFeedAdapter = new IgFeedAdapter();
        final SpeedyLinearLayoutManager llm = new SpeedyLinearLayoutManager(getActivity()
                , SpeedyLinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(llm);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(igFeedAdapter);
        //recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            int firstVisiblesItems, visibleItemCount, totalItemCount;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = llm.getChildCount();
                totalItemCount = llm.getItemCount();
                firstVisiblesItems = llm.findFirstVisibleItemPosition();
                if(firstVisiblesItems + visibleItemCount >= totalItemCount){
                    if(igFeedAdapter.getItemCount() > 0){
                        loadMoreData();
                    }
                }else{
                    isLoadingMore = false;
                }
                RecyclerviewPosition.getInstance().setPosition(firstVisiblesItems);
            }
        });

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                JZVideoPlayer.onChildViewAttachedToWindow(view, R.id.videoplayer);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer.onChildViewDetachedFromWindow(view);
            }
        });

        /*recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                EventBus.getDefault().post(new BusEvent.HideEvent());
                //hideViews();
            }
            @Override
            public void onShow() {
                //showViews();
                EventBus.getDefault().post(new BusEvent.ShowEvent());
            }
        }); */


        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(InternetCheck.internet_connection(getActivity()))
                    loadData();
                else
                    Snackbar.make(getView(),"No internet connection.",Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        if(InternetCheck.internet_connection(getActivity())) {
            loadData();
        }else
            Toast.makeText(getContext(),"No internet connection",Toast.LENGTH_LONG).show();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        if(FeedListManager.getInstance().getIg_dao() != null) {
            FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().clear();
        }
        igFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMessageEvent(BusEvent.ScrollUpEvent event) {
        recyclerView.smoothScrollToPosition(0);
    }



    private void loadData() {
        // TODO: load data
        IGProfileService service = FeedListManager.getInstance().getService();

        Call<IG_dao> igDaoCall = service.getIgProfileDao(parts[0]);
        Call<IG_feed_dao> igDataCall = service.getIgFeedDao("{\"id\":\""+parts[1]+"\",\"first\":20}");

        igDaoCall.enqueue(new Callback<IG_dao>() {
            @Override
            public void onResponse(Call<IG_dao> call, Response<IG_dao> response) {
                UserProfileManager.getInstance().setIg_dao(response.body());
                Log.d("check1", "onResponse: "+response.code());
                igFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<IG_dao> call, Throwable t) {

            }
        });

        igDataCall.enqueue(new Callback<IG_feed_dao>() {
            @Override
            public void onResponse(Call<IG_feed_dao> call, Response<IG_feed_dao> response) {
                FeedListManager.getInstance().setIg_dao(response.body());
                Log.d("check2", "onResponse: "+response.toString());
               // Log.d("check2",FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().get(0).getNode().getEdgeMediaToCaption().getEdges().get(0).getNode().getText());
                igFeedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<IG_feed_dao> call, Throwable t) {

            }
        });
    }

    private void loadMoreData(){
        Log.d("checkEnd",FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getPageInfo().getEndCursor());
        if(isLoadingMore){
            return;
        }
        if(FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getPageInfo().getEndCursor() == "end"){
            Snackbar.make(getView(),"End Feed",Snackbar.LENGTH_LONG).show();
            return;
        }
        isLoadingMore = true;
        // TODO: add data to dao
        IGProfileService service = FeedListManager.getInstance().getService();
        String nextPage = FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getPageInfo().getEndCursor();
        Call<IG_feed_dao> igDataCall = service.getIgFeedDao("{\"id\":\""+parts[1]+"\",\"first\":20,\"after\":\""+nextPage+"\"}");

        igDataCall.enqueue(new Callback<IG_feed_dao>() {
            @Override
            public void onResponse(Call<IG_feed_dao> call, Response<IG_feed_dao> response) {
                FeedListManager.getInstance().addIgDaoAtButtomPosition(response.body());
                //Log.d("check2",FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().get(0).getNode().getEdgeMediaToCaption().getEdges().get(0).getNode().getText());
                igFeedAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<IG_feed_dao> call, Throwable t) {

            }
        });

    }
}