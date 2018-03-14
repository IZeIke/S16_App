package com.example.haritmoolphunt.facebookfeed.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.haritmoolphunt.facebookfeed.dao.Accesstoken;
import com.example.haritmoolphunt.facebookfeed.event.BusEvent;
import com.example.haritmoolphunt.facebookfeed.manager.AccessTokenManager;
import com.example.haritmoolphunt.facebookfeed.manager.AppTokenManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harit Moolphunt on 5/3/2561.
 */

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("initialData", Context.MODE_PRIVATE);

        if(AppTokenManager.getInstance().getAccessToken() == null) {

            if(sp.getString("Token",null) != null && sp.getString("AppId",null) != null)
            {
                AccessToken accessToken = new AccessToken(sp.getString("Token",null), sp.getString("AppId",null), "100001381973949", null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, null, null);
                AppTokenManager.getInstance().setAccessToken(accessToken);

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                // close splash activity
                finish();
            }else {

                Call<Accesstoken> call = AccessTokenManager.getInstance().getService().getId();
                call.enqueue(new Callback<Accesstoken>() {
                    @Override
                    public void onResponse(Call<Accesstoken> call, Response<Accesstoken> response) {
                        if (response.isSuccessful()) {
                            Accesstoken dao = response.body();
                            saveToSharePreferances(dao.getAppId() + "|" + dao.getAppSecret(), dao.getAppId());
                            AccessToken accessToken = new AccessToken(dao.getAppId() + "|" + dao.getAppSecret(), dao.getAppId(), "100001381973949", null, null, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE, null, null);
                            AppTokenManager.getInstance().setAccessToken(accessToken);

                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            // close splash activity
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Accesstoken> call, Throwable t) {

                    }
                });

            }
        }else{
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            // close splash activity
            finish();
        }


    }

    public void saveToSharePreferances(String token,String appId){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Token", token);
        editor.putString("AppId",appId);
        editor.commit();
    }
}