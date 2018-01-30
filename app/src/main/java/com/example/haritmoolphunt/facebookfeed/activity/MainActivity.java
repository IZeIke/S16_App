package com.example.haritmoolphunt.facebookfeed.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.fragment.FeedFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.MainFragment;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    AppBarLayout appBarLayout;

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

                appBarLayout.setExpanded(true, true);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainer, FeedFragment.newInstance(getString(R.string.Mahnmook)))
                        .commit();
            }
        }
    }

    private void initinstance() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.setExpanded(false, true);
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
                .replace(R.id.contentContainer, FeedFragment.newInstance(getString(R.string.Mahnmook)))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mahnmooksetting)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, FeedFragment.newInstance(getString(R.string.Mahnmook)))
                    .commit();
        }else
        if(item.getItemId() == R.id.ornsetting)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, FeedFragment.newInstance(getString(R.string.Orn)))
                    .commit();
        }else
        if(item.getItemId() == R.id.logoutsetting)
        {
            LoginManager.getInstance().logOut();
            appBarLayout = findViewById(R.id.appBarLayout);
            appBarLayout.setExpanded(false, true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }
        return true;
    }
}
