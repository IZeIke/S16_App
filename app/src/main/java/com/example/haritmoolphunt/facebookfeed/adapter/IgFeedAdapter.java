package com.example.haritmoolphunt.facebookfeed.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.dao.UserProfile;
import com.example.haritmoolphunt.facebookfeed.dao.ig_dao.IG_dao;
import com.example.haritmoolphunt.facebookfeed.dao.ig_dao.User;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;
import com.example.haritmoolphunt.facebookfeed.manager.UserProfileManager;
import com.pchmn.materialchips.ChipView;

/**
 * Created by Harit Moolphunt on 30/3/2561.
 */

public class IgFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int FIRST_ITEM = 0;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == FIRST_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_feed_ig_first, parent, false);
            IgFeedAdapter.ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_feed_ig, parent, false);
            IgFeedAdapter.ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
        {
            return FIRST_ITEM;
        }else{
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User profile = UserProfileManager.getInstance().getIg_dao().getGraphql().getUser();
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.profileName.setText(profile.getUsername());
        Glide.with(viewHolder.profilePicture.getContext())
                .load(profile.getProfilePicUrlHd())
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.profilePicture);
        viewHolder.description.setText(FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().get(position).getNode().getEdgeMediaToCaption().getEdges().get(0).getNode().getText());

        RequestOptions requestOptions = new RequestOptions().fitCenter();
        requestOptions.placeholder(R.drawable.placeholder);

        Glide.with(viewHolder.image.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().get(position).getNode().getDisplayUrl())
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        if(FeedListManager.getInstance().getIg_dao() == null){
            return 0;
        }else
        if(FeedListManager.getInstance().getIg_dao().getData() == null){
            return 0;
        }else {
            return FeedListManager.getInstance().getIg_dao().getData().getUser().getEdgeOwnerToTimelineMedia().getEdges().size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePicture;
        TextView profileName;
        TextView timestamp;
        TextView description;
        ImageView image;
        ChipView chip;


        public ViewHolder(View view) {
            super(view);
            profilePicture = view.findViewById(R.id.profile_picture);
            profileName = view.findViewById(R.id.profile_name);
            timestamp = view.findViewById(R.id.timestamp);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.feedImage);
            chip = view.findViewById(R.id.chip_view);
        }
    }
}
