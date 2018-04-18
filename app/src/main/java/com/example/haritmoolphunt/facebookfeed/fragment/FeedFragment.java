package com.example.haritmoolphunt.facebookfeed.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.example.haritmoolphunt.facebookfeed.layoutManager.SpeedyLinearLayoutManager;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.adapter.FeedListAdapter;
import com.example.haritmoolphunt.facebookfeed.dao.PageProfile;
import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.dao.video_dao.Video;
import com.example.haritmoolphunt.facebookfeed.event.BusEvent;
import com.example.haritmoolphunt.facebookfeed.manager.AppTokenManager;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;
import com.example.haritmoolphunt.facebookfeed.manager.FeedVideoManager;
import com.example.haritmoolphunt.facebookfeed.manager.PageProfileManager;
import com.example.haritmoolphunt.facebookfeed.manager.RecyclerviewPosition;
import com.example.haritmoolphunt.facebookfeed.manager.helper.InternetCheck;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by Harit Moolphunt on 10/1/2561.
 */

public class FeedFragment extends Fragment {

    String pageID;
    RecyclerView recyclerView;
    FeedListAdapter feedListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLoadingMore = false;

    public FeedFragment() {
        super();
    }

    public static FeedFragment newInstance(String pageid) {
        FeedFragment fragment = new FeedFragment();
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
        feedListAdapter = new FeedListAdapter();
        feedListAdapter.setOnProfileClickedListener(new FeedListAdapter.ProfileActivityEventListener() {
            @Override
            public void onProfileClicked() {
                EventBus.getDefault().post(new BusEvent.ProfileActivityEvent());
            }
        });
        final SpeedyLinearLayoutManager llm = new SpeedyLinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(llm);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(false);

        recyclerView.setAdapter(feedListAdapter);
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
                    if(feedListAdapter.getItemCount() > 0){
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
            if(savedInstanceState == null) {
                loadData();
            }
        }else
            Toast.makeText(getContext(),"No internet connection",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
        outState.putString("pageId",pageID);
        outState.putBundle("feedListManager",FeedListManager.getInstance().onSaveInstanceState());
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
        FeedListManager.getInstance().onRestoreInstanceState(savedInstanceState.getBundle("feedListManager"));
        pageID = savedInstanceState.getString("pageId");
        Log.d("pageId", "onRestoreInstanceState: "+ pageID);
    }

    private void loadData() {
        GraphRequest request1 = getFeedGraphRequest();
        // request.executeAsync();

        GraphRequest request2 = getPageProfileGraphRequest();
        // request2.executeAsync();
        GraphRequest request3 = getPageVideoGraphRequest();

        GraphRequestBatch batch = new GraphRequestBatch(request3,request2,request1);
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                // Application code for when the batch finishes
                swipeRefreshLayout.setRefreshing(false);
                feedListAdapter.notifyDataSetChanged();
            }
        });
        batch.executeAsync();
    }

    private void loadMoreData(){
        //GraphRequest request1 = getPageProfileGraphRequest();
        if(isLoadingMore){
            return;
        }
        if(FeedListManager.getInstance().getDao().getMoreFeed().getNext() == "end"){
            Snackbar.make(getView(),"End Feed",Snackbar.LENGTH_LONG).show();
            return;
        }
        isLoadingMore = true;
        String nextUrl = FeedListManager.getInstance().getDao().getMoreFeed().getNext();
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AppTokenManager.getInstance().getAccessToken(),
                nextUrl.substring(nextUrl.indexOf("/"+pageID)),
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        Gson gson = new Gson();
                        Posts posts = gson.fromJson(response.getJSONObject().toString(), Posts.class);
                        FeedListManager.getInstance().addDaoAtButtomPosition(posts);
                        feedListAdapter.notifyDataSetChanged();
                    }
                });

        request.executeAsync();
    }

    @NonNull
    private GraphRequest getPageProfileGraphRequest() {
        GraphRequest request2 = GraphRequest.newGraphPathRequest(
                AppTokenManager.getInstance().getAccessToken(),
                "/"+pageID,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        //Log.d("check",response.getJSONObject().toString());
                        Gson gson = new Gson();
                        PageProfile pageProfile = gson.fromJson(response.getJSONObject().toString(),PageProfile.class);
                        PageProfileManager.getInstance().setDao(pageProfile);
                    }
                });

        Bundle parameters2 = new Bundle();
        parameters2.putString("fields", "name,picture.width(800).height(800)");
        request2.setParameters(parameters2);
        return request2;
    }

    @NonNull
    private GraphRequest getFeedGraphRequest() {
        Set mySet1 = new HashSet();
        mySet1.add("public_profile");
        mySet1.add("email");
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AppTokenManager.getInstance().getAccessToken(),
                "/"+pageID+"/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        //Log.d("check",response.getJSONObject().toString());
                        Gson gson = new Gson();
                        Posts posts = gson.fromJson(response.getJSONObject().toString(), Posts.class);
                        FeedListManager.getInstance().setDao(posts);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("pretty", "0");
        parameters.putString("fields", "id,created_time,message,attachments{media{image{src}},title,type,url,subattachments{media{image{src}}}}");
        parameters.putString("limit", "25");
        request.setParameters(parameters);
        return request;
    }

    @NonNull
    private GraphRequest getPageVideoGraphRequest() {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AppTokenManager.getInstance().getAccessToken(),
                "/"+pageID+"/videos",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        //Log.d("check",response.getJSONObject().toString());
                        Gson gson = new Gson();
                        Video video = gson.fromJson(response.getJSONObject().toString(), Video.class);
                        FeedVideoManager.getInstance().setDao(video);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "source");
        request.setParameters(parameters);
        return request;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(BusEvent.ScrollUpEvent event) {
        recyclerView.smoothScrollToPosition(0);
    }

}
