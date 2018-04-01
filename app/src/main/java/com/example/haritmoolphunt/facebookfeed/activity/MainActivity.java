package com.example.haritmoolphunt.facebookfeed.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.dao.Accesstoken;
import com.example.haritmoolphunt.facebookfeed.dao.UserProfile;
import com.example.haritmoolphunt.facebookfeed.event.BusEvent;
import com.example.haritmoolphunt.facebookfeed.fragment.FeedFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.IgFeedFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.MainFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.UserProfileFragment;
import com.example.haritmoolphunt.facebookfeed.fragment.ViewPagerMainFragment;
import com.example.haritmoolphunt.facebookfeed.manager.AccessTokenManager;
import com.example.haritmoolphunt.facebookfeed.manager.AppTokenManager;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;
import com.example.haritmoolphunt.facebookfeed.manager.SampleSuggestionsBuilder;
import com.example.haritmoolphunt.facebookfeed.manager.SearchModel;
import com.example.haritmoolphunt.facebookfeed.manager.UserProfileManager;
import com.example.haritmoolphunt.facebookfeed.manager.helper.NameListCollector;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.cryse.widget.persistentsearch.HomeButton;
import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.SearchItem;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.SimpleSearchFilter;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.haritmoolphunt.facebookfeed.manager.helper.NameListCollector.*;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private PersistentSearchView mSearchView;
    private View mSearchTintView;
    NameListCollector nameListCollector;
    AHBottomNavigation bottomNavigation;
    TabLayout tabLayout;
    RecyclerView recentBar;
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
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.menuContainer, UserProfileFragment.newInstance())
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, ViewPagerMainFragment.newInstance(getString(R.string.Mahnmook)))
                    .commit();
        }
    }

    private void initinstance() {
        nameListCollector = new NameListCollector(getString(R.string.Mahnmook),NameListCollector.nameIGList[0]);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSearchView = findViewById(R.id.searchview);
        drawerLayout = findViewById(R.id.drawerLayout);
        mSearchTintView = findViewById(R.id.view_search_tint);
        appBarLayout = findViewById(R.id.appBarLayout);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Facebook", R.drawable.ic_facebook);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Instagram", R.drawable.ic_instagram);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Twitter", R.drawable.ic_twitter);

        /*
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#F44336"));
        bottomNavigation.setAccentColor(Color.WHITE);
        bottomNavigation.setInactiveColor(Color.parseColor("#cecece"));
        */
        bottomNavigation.setAccentColor(Color.parseColor("#F44336"));

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position == 0)
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer, ViewPagerMainFragment.newInstance(nameListCollector.getFbId()))
                            .commit();
                    //EventBus.getDefault().post(new BusEvent.ChangeFragmentEvent(1));
                }else
                if(position == 1)
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer, IgFeedFragment.newInstance(UserProfileManager.getInstance().getIg_dao().getGraphql().getUser().getId()))
                            .commit();
                }
                return true;
            }
        });
        //appBarLayout.setVisibility(View.VISIBLE);
        //appBarLayout.setExpanded(false, true);
        //tabLayout = findViewById(R.id.tabLayout);
       // tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
       // tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this ,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSearchView.setHomeButtonListener(new PersistentSearchView.HomeButtonListener() {

            @Override
            public void onHomeButtonClick() {
                //Hamburger has been clicked
                drawerLayout.openDrawer(Gravity.LEFT);

            }

        });

        mSearchTintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.cancelEditing();
            }
        });

        mSearchView.setSuggestionBuilder(new SampleSuggestionsBuilder(this));
        mSearchView.setSearchListener(new PersistentSearchView.SearchListener() {
            @Override
            public void onSearchCleared() {

            }

            @Override
            public void onSearchTermChanged(String term) {

            }

            @Override
            public void onSearch(String query) {
                String [] nameList = FeedListManager.getInstance().getNameList();
                String [] nameId = FeedListManager.getInstance().getNameId();
                Log.d("check",query);
                for(int i=0;i < nameList.length;i++) {
                    if(query.startsWith(nameList[i])){
                        onChangePage(nameId[i]);
                    }else
                    if(nameList[i].startsWith(query.substring(0,1).toUpperCase()+query.substring(1))) {
                        onChangePage(nameId[i]);
                    }
                }

                mSearchView.setSearchString("",false);
                //onBackPressed();

            }

            @Override
            public void onSearchEditOpened() {
                mSearchTintView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchEditClosed() {
                mSearchTintView.setVisibility(View.GONE);
            }

            @Override
            public boolean onSearchEditBackPressed() {
                return false;
            }

            @Override
            public void onSearchExit() {

            }
        });
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
    public void onLoginSuccess(BusEvent.LoginEvent loginEvent) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.menuContainer, UserProfileFragment.newInstance())
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentContainer, ViewPagerMainFragment.newInstance(getString(R.string.Mahnmook)))
                .commit();

        drawerLayout.closeDrawer(Gravity.START);
    }

    @Subscribe
    public void onChangePage(String pageId) {
        //getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        nameListCollector.setFbId(pageId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, ViewPagerMainFragment.newInstance(pageId))
                .commit();

        drawerLayout.closeDrawer(Gravity.START);
    }

    @Subscribe
    public void onHide(BusEvent.HideEvent hideEvent) {
        hideViews();
    }

    @Subscribe
    public void onShow(BusEvent.ShowEvent showEvent) {
       showViews();
    }

    @Subscribe
    public void onStartPhotoActivity(BusEvent.PhotoActivityEvent event){
        Intent intent = new Intent(MainActivity.this,PhotoActivity.class);
        String[] urlList = event.getUrlList();
        for(int i =0;i<urlList.length;i++)
        {
            intent.putExtra(""+i, urlList[i]);
        }
        intent.putExtra("size",urlList.length);
        startActivity(intent);
    }

    @Subscribe
    public void onProfileActivity(BusEvent.ProfileActivityEvent event){
        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
        startActivity(intent);
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

        if(item.getItemId() == R.id.action_search){
            new SimpleSearchDialogCompat(MainActivity.this, "Search...",
                    "Who are you looking for...?",null, createSampleData(),
                    new SearchResultListener<SearchModel>() {
                        @Override
                        public void onSelected(BaseSearchDialogCompat dialog,
                                               SearchModel item, int position) {
                            onChangePage(item.getId());
                            dialog.dismiss();
                        }
                    }).setFilterAutomatically(true).show();
        }

        if(item.getItemId() == R.id.logoutsetting)
        {

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }else
        if(mSearchView.isSearching()) {
            mSearchView.closeSearch();
        } /*else if(mRecyclerView.getVisibility() == View.VISIBLE) {
            mResultAdapter.clear();
            mRecyclerView.setVisibility(View.GONE);
        }*/
        else if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    private void hideViews() {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        mSearchView.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mSearchView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    String[] nameList = NameListCollector.nameList;
    String[] nameId = NameListCollector.nameId;

    private ArrayList<SearchModel> createSampleData(){
        ArrayList<SearchModel> items = new ArrayList<>();

        for(int i=0;i<nameList.length;i++)
            items.add(new SearchModel(nameList[i],nameId[i]));

        return items;
    }

    public void clearBackstack() {

        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                0);
        getSupportFragmentManager().popBackStack(entry.getId(),
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().executePendingTransactions();

    }
}

