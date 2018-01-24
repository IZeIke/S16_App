package com.example.haritmoolphunt.facebookfeed.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.template.BaseCustomViewGroup;
import com.example.haritmoolphunt.facebookfeed.template.state.BundleSavedState;
import com.github.chrisbanes.photoview.PhotoView;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FeedListItem extends BaseCustomViewGroup {
    TextView timestamp;
    TextView description;
    PhotoView photo;

    public void setTimestamp(String timestamp) {
        this.timestamp.setText(timestamp);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setimageUrl(String photo) {

    }

    public FeedListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public FeedListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public FeedListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public FeedListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_feed, this);
    }

    private void initInstances() {
        // findViewById here
        timestamp = findViewById(R.id.timestamp);
        description = findViewById(R.id.description);
        photo = findViewById(R.id.feedImage);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

}
