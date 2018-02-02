package com.example.haritmoolphunt.facebookfeed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.dao.Datum;
import com.example.haritmoolphunt.facebookfeed.dao.PageProfile;
import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;
import com.example.haritmoolphunt.facebookfeed.manager.PageProfileManager;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;
import com.example.haritmoolphunt.facebookfeed.view.FeedListItem;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Harit Moolphunt on 13/1/2561.
 */

public class FeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      //  FeedListItem view = new FeedListItem(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PageProfile pageProfile = PageProfileManager.getInstance().getDao();
        Datum dao = FeedListManager.getInstance().getDao().getFeed().get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.profileName.setText(pageProfile.getName());
        Glide.with(viewHolder.profilePicture.getContext())
                .load(pageProfile.getPicture().getData().getUrl())
                .into(viewHolder.profilePicture);
        viewHolder.description.setText(dao.getMessage());
        viewHolder.timestamp.setText(getTimeAgoFromUTCString(dao.getCreatedTime()));
        viewHolder.image.setVisibility(View.VISIBLE);

        if(dao.getAttachments() != null){
            if(dao.getAttachments().getData().get(0).getMedia() != null) {

                RequestOptions requestOptions = new RequestOptions().fitCenter();

                Glide.with(viewHolder.image.getContext()).setDefaultRequestOptions(requestOptions)
                        .load(dao.getAttachments().getData().get(0).getMedia().getImage().getSrc())
                        .into(viewHolder.image);

               // Log.d("videocheck",dao.getAttachments().getData().get(0).getType());
               // Log.d("videocheck",dao.getAttachments().getData().get(0).getUrl());
                if(dao.getAttachments().getData().get(0).getType() == "video_inline")
                {

                }

            }else{
                viewHolder.image.setVisibility(View.GONE);
            }

        }else{
            viewHolder.image.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if(FeedListManager.getInstance().getDao() == null)
            return 0;
        if(FeedListManager.getInstance().getDao().getFeed() == null)
            return 0;

        return FeedListManager.getInstance().getDao().getFeed().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePicture;
        TextView profileName;
        TextView timestamp;
        TextView description;
        PhotoView image;
        VideoView videoView;

        public ViewHolder(View view) {
            super(view);
            profilePicture = view.findViewById(R.id.profile_picture);
            profileName = view.findViewById(R.id.profile_name);
            timestamp = view.findViewById(R.id.timestamp);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.feedImage);
           // videoView = view.findViewById(R.id.videoView);
        }
    }

    private CharSequence getTimeAgoFromUTCString(String utcLongDateTime) {
        long timeMillis = 0;
        try
        {
            timeMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSSS")
                    .parse(utcLongDateTime)
                    .getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                timeMillis,
                System.currentTimeMillis()-25200000, DateUtils.SECOND_IN_MILLIS);

        return timeAgo;
    }
}
