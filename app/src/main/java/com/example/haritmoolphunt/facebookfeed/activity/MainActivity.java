package com.example.haritmoolphunt.facebookfeed.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.fragment.FeedFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.MainFragment;
import com.facebook.AccessToken;
import com.facebook.login.LoginResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initinstance();

        if(savedInstanceState == null)
        {
            if(AccessToken.getCurrentAccessToken() == null) {

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainer, MainFragment.newInstance())
                        .commit();
            }else{

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainer, FeedFragment.newInstance())
                        .commit();
            }
        }
    }

    private void initinstance() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onLoginSuccess(AccessToken accessToken) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, FeedFragment.newInstance())
                .commit();
    }
}
