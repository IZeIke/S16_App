package com.example.haritmoolphunt.facebookfeed.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.haritmoolphunt.facebookfeed.R;

import java.util.HashMap;

/**
 * Created by Harit Moolphunt on 11/3/2561.
 */

public class PhotoActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout slider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        initinstance();

    }

    private void initinstance() {
        slider = findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            for(int i=0;i<bundle.getInt("size");i++)
            {
                url_maps.put(""+i, bundle.getString(""+i));
            }
        }

        /*
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg"); */

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    //.description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(this);

            slider.addSlider(textSliderView);
        }

        if (bundle.getInt("size") < 2) {
            slider.stopAutoCycle();
            slider.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            slider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        }

        slider.stopAutoCycle();
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        //slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //slider.setCustomAnimation(new DescriptionAnimation());
        //slider.setDuration(4000);
        slider.addOnPageChangeListener(this);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        slider.stopAutoCycle();
        super.onStop();
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
