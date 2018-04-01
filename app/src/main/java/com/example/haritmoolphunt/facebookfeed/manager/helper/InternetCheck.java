package com.example.haritmoolphunt.facebookfeed.manager.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.haritmoolphunt.facebookfeed.template.Contextor;

/**
 * Created by Harit Moolphunt on 24/3/2561.
 */

public class InternetCheck {

    public boolean internet_connection() {
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager) Contextor.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

}
