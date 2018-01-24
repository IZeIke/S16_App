package com.example.haritmoolphunt.facebookfeed.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.adapter.FeedListAdapter;
import com.example.haritmoolphunt.facebookfeed.dao.PageProfile;
import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;
import com.example.haritmoolphunt.facebookfeed.manager.PageProfileManager;
import com.example.haritmoolphunt.facebookfeed.template.FragmentTemplateFull;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Harit Moolphunt on 10/1/2561.
 */

public class FeedFragment extends Fragment {

    RecyclerView recyclerView;
    FeedListAdapter feedListAdapter;

    public FeedFragment() {
        super();
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

     /*   GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Gson gson = new Gson();
                        Posts posts = gson.fromJson(response.getJSONObject().toString(), Posts.class);
                        System.out.println(posts.getFeed().get(0).getMessage());

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("ids", "737460709751015");
        parameters.putString("fields", "name,picture{url},posts{id,created_time,message,attachments{media{image{src}},title,type,url,subattachments{media{image{src}}}}}");
        request.setParameters(parameters);
        request.executeAsync(); */

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/737460709751015/posts",
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
       // request.executeAsync();

        GraphRequest request2 = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/737460709751015",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Gson gson = new Gson();
                        PageProfile pageProfile = gson.fromJson(response.getJSONObject().toString(),PageProfile.class);
                        PageProfileManager.getInstance().setDao(pageProfile);
                    }
                });

        Bundle parameters2 = new Bundle();
        parameters2.putString("fields", "name,picture");
        request2.setParameters(parameters2);
       // request2.executeAsync();


        GraphRequestBatch batch = new GraphRequestBatch(request2,request);
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                // Application code for when the batch finishes
                feedListAdapter.notifyDataSetChanged();
            }
        });
        batch.executeAsync();

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState]
        recyclerView = rootView.findViewById(R.id.recyclerview);
        feedListAdapter = new FeedListAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(llm);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(feedListAdapter);
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

}
