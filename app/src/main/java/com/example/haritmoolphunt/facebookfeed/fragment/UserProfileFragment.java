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
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class UserProfileFragment extends Fragment {


    RecyclerView recyclerView;
    NameListAdapter nameListAdapter;
    Button button;
    ImageView profilePicture;
    TextView userName;
    String[] sweat16List = {"Mahnmook","Music","Mint","Ant","Ae","Fame","Pada","Petch","Proud","Pim","Sonja","Anny","Nink"};

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
        userName = rootView.findViewById(R.id.user_profile_name);
        profilePicture = rootView.findViewById(R.id.user_profile_picture);

        GraphRequest request = GraphRequest.newMeRequest(
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
                        Log.d("check",UserProfileManager.getInstance().getDao().getName());
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,picture.width(800).height(800)");
        request.setParameters(parameters);
        request.executeAsync();

        button = rootView.findViewById(R.id.toggle_button);
        recyclerView = rootView.findViewById(R.id.name_RecyclerView);
        nameListAdapter = new NameListAdapter(sweat16List);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(nameListAdapter);
        recyclerView.setVisibility(View.GONE);
        nameListAdapter.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //userName.setText(UserProfileManager.getInstance().getDao().getName());
                if(recyclerView.getVisibility() == View.VISIBLE)
                    recyclerView.setVisibility(View.GONE);
                else
                    recyclerView.setVisibility(View.VISIBLE);
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
