package com.example.haritmoolphunt.facebookfeed.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.haritmoolphunt.facebookfeed.Listener.HidingScrollListener;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.adapter.FeedListAdapter;
import com.example.haritmoolphunt.facebookfeed.dao.PageProfile;
import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.dao.UserProfile;
import com.example.haritmoolphunt.facebookfeed.event.BusEvent;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;
import com.example.haritmoolphunt.facebookfeed.manager.PageProfileManager;
import com.example.haritmoolphunt.facebookfeed.manager.UserProfileManager;
import com.example.haritmoolphunt.facebookfeed.template.FragmentTemplateFull;
import com.example.haritmoolphunt.facebookfeed.view.SimpleDividerItemDecoration;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

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
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity()
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

        if(internet_connection())
            loadData();
        else
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

    private void loadData() {
        GraphRequest request1 = getFeedGraphRequest();
        // request.executeAsync();

        GraphRequest request2 = getPageProfileGraphRequest();
        // request2.executeAsync();

        GraphRequestBatch batch = new GraphRequestBatch(request2,request1);
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
                AccessToken.getCurrentAccessToken(),
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
                AccessToken.getCurrentAccessToken(),
                "/"+pageID,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
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
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+pageID+"/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
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
