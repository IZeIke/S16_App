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

import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.adapter.FeedListAdapter;
import com.example.haritmoolphunt.facebookfeed.adapter.IgFeedAdapter;
import com.example.haritmoolphunt.facebookfeed.dao.ig_dao.IG_dao;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.Data;
import com.example.haritmoolphunt.facebookfeed.dao.ig_feed_data.IG_feed_dao;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;

import com.example.haritmoolphunt.facebookfeed.manager.UserProfileManager;
import com.example.haritmoolphunt.facebookfeed.manager.helper.InternetCheck;
import com.example.haritmoolphunt.facebookfeed.manager.http.IGProfileService;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;
import com.facebook.AccessToken;
import com.google.gson.Gson;

import javax.xml.datatype.Duration;

import cn.jzvd.JZVideoPlayer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harit Moolphunt on 24/3/2561.
 */

public class IgFeedFragment extends Fragment {

    String pageID;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLoadingMore = false;
    IgFeedAdapter igFeedAdapter;

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
        pageID = getArguments().getString("pageID");

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
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState]
        recyclerView = rootView.findViewById(R.id.recyclerview);
        igFeedAdapter = new IgFeedAdapter();
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL
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
                        //loadMoreData();
                    }
                }else{
                    isLoadingMore = false;
                }
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
                if(internet_connection())
                    loadData();
                else
                    Snackbar.make(getView(),"No internet connection.",Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        if(internet_connection()) {
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
        FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().clear();
        igFeedAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        // TODO: load data
        IGProfileService service = FeedListManager.getInstance().getService();

        Call<IG_feed_dao> igDataCall = service.getIgFeedDao("{\"id\":\""+pageID+"\",\"first\":20}");

        igDataCall.enqueue(new Callback<IG_feed_dao>() {
            @Override
            public void onResponse(Call<IG_feed_dao> call, Response<IG_feed_dao> response) {
                FeedListManager.getInstance().setIg_dao(response.body());
                //Log.d("check2",FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().get(0).getNode().getEdgeMediaToCaption().getEdges().get(0).getNode().getText());
                igFeedAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<IG_feed_dao> call, Throwable t) {

            }
        });
    }

    private void loadMoreData(){
        if(isLoadingMore){
            return;
        }
        if(FeedListManager.getInstance().getDao().getMoreFeed().getNext() == "end"){
            Snackbar.make(getView(),"End Feed",Snackbar.LENGTH_LONG).show();
            return;
        }
        isLoadingMore = true;
        // TODO: add data to dao
    }

    boolean internet_connection(){
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}