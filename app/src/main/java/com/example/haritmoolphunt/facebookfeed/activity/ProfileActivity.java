package com.example.haritmoolphunt.facebookfeed.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.fragment.ProfileFragment;


/**
 * Created by Harit Moolphunt on 17/3/2561.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initinstance();

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, ProfileFragment.newInstance())
                    .commit();
        }
    }

    private void initinstance() {


    }
}
