package com.example.haritmoolphunt.facebookfeed.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.adapter.NameListAdapter;
import com.example.haritmoolphunt.facebookfeed.dao.UserProfile;
import com.example.haritmoolphunt.facebookfeed.manager.UserProfileManager;
import com.example.haritmoolphunt.facebookfeed.view.BandListItem;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.rohit.recycleritemclicksupport.RecyclerItemClickSupport;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class UserProfileFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView bnkRecyclerView;
    NameListAdapter nameListAdapter;
    NameListAdapter bnkListAdapter;
    BandListItem sweatButton;
    BandListItem bnkButton;
    //ImageView profilePicture;
    //TextView userName;
    String[] sweat16List = {"Mahnmook","Music","Mint","Ant","Ae","Fame","Pada","Petch","Proud","Pim","Sonja","Anny","Nink"};
    String[] sweat16Id = {"1705672169741865","108567926503749","138188866783330","445003689233126","709046175950703","1913673758896179","260045927831231","108001133246382","120406398606162","799444370230832","111090532897541","809228849236630","333542507094850"};
    String[] bnkList = {"Orn"};
    String[] bnkId = {"737460709751015"};

    public UserProfileFragment() {
        super();
    }

    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
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
        //       in onSavedInstanceState
        //userName = rootView.findViewById(R.id.user_profile_name);
        //profilePicture = rootView.findViewById(R.id.user_profile_picture);
        sweatButton = rootView.findViewById(R.id.sweat16_button);
        bnkButton = rootView.findViewById(R.id.bnk48_button);
        sweatButton.setName("SWEAT16!");
        sweatButton.setPicture(getResources().getDrawable(R.drawable.sweat16logo));
        bnkButton.setName("BNK48");
        bnkButton.setPicture(getResources().getDrawable(R.drawable.bnk48logo));

        /*GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Insert your code here
                        Gson gson = new Gson();
                        UserProfile userProfile = gson.fromJson(object.toString(),UserProfile.class);
                        UserProfileManager.getInstance().setDao(userProfile);
                        userName.setText(UserProfileManager.getInstance().getDao().getName());

                        Glide.with(getContext())
                                .load(UserProfileManager.getInstance().getDao().getPicture().getData().getUrl())
                                .apply(RequestOptions.circleCropTransform())
                                .into(profilePicture);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,picture.width(800).height(800)");
        request.setParameters(parameters);
        request.executeAsync(); */

        recyclerView = rootView.findViewById(R.id.name_RecyclerView);
        bnkRecyclerView = rootView.findViewById(R.id.bnk48_RecyclerView);
        bnkListAdapter = new NameListAdapter(bnkList);
        nameListAdapter = new NameListAdapter(sweat16List);
        final LinearLayoutManager llm1 = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL
                , false);
        final LinearLayoutManager llm2 = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(llm1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(nameListAdapter);
        recyclerView.setVisibility(View.GONE);
        nameListAdapter.notifyDataSetChanged();

        bnkRecyclerView.setLayoutManager(llm2);
        bnkRecyclerView.setNestedScrollingEnabled(false);
        bnkRecyclerView.setAdapter(bnkListAdapter);
        bnkRecyclerView.setVisibility(View.GONE);
        bnkListAdapter.notifyDataSetChanged();

        RecyclerItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new RecyclerItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        EventBus.getDefault().post(sweat16Id[position]);
                    }
                }
        );

        RecyclerItemClickSupport.addTo(bnkRecyclerView).setOnItemClickListener(
                new RecyclerItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        EventBus.getDefault().post(bnkId[position]);
                    }
                }
        );

        sweatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerView.getVisibility() == View.VISIBLE)
                    recyclerView.setVisibility(View.GONE);
                else
                    recyclerView.setVisibility(View.VISIBLE);
            }
        });

        bnkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bnkRecyclerView.getVisibility() == View.VISIBLE)
                    bnkRecyclerView.setVisibility(View.GONE);
                else
                    bnkRecyclerView.setVisibility(View.VISIBLE);
            }
        });
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
