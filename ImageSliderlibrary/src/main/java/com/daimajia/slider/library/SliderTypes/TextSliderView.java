package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * This is a slider with a description TextView.
 */
public class TextSliderView extends BaseSliderView{
    public TextSliderView(Context context) {
        super(context);
    }
    View descriptionLayout;
    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text,null);
        PhotoView target = (PhotoView) v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView)v.findViewById(R.id.description);
        descriptionLayout = (View) v.findViewById(R.id.description_layout);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }

    public void setIndicatorHide(){
        descriptionLayout.setVisibility(View.INVISIBLE);
    }

    public void setIndicatorShow(){
        descriptionLayout.setVisibility(View.VISIBLE);
    }

    public boolean checkVisible(){
        if(descriptionLayout.getVisibility() == View.VISIBLE)
        {
            return true;
        }else {
            return false;
        }
    }
}
