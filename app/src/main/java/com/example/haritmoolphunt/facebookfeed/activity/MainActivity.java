package com.example.haritmoolphunt.facebookfeed.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.fragment.FeedFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.MainFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.UserProfileFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.ViewPagerMainFragment;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    AppBarLayout appBarLayout;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

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
                        .add(R.id.menuContainer, UserProfileFragment.newInstance())
                        .commit();
                getSupportFragmentManager().beginTransaction()
                //        .add(R.id.contentContainer, FeedFragment.newInstance(getString(R.string.Mahnmook)))
                        .add(R.id.contentContainer, ViewPagerMainFragment.newInstance(getString(R.string.Mahnmook)))
                        .commit();
            }
        }
    }

    private void initinstance() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.setExpanded(false, true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this ,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
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
    public void onLoginSuccess(String pageId) {
        getSupportFragmentManager().beginTransaction()
                //.replace(R.id.contentContainer, FeedFragment.newInstance(getString(R.string.Mahnmook)))
                .replace(R.id.contentContainer, ViewPagerMainFragment.newInstance(pageId))
                .commit();

        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

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
