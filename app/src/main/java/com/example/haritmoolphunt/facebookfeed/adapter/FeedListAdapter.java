package com.example.haritmoolphunt.facebookfeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.haritmoolphunt.facebookfeed.LayoutManager.Helper.MySpanSizeLookup;
import com.example.haritmoolphunt.facebookfeed.LayoutManager.SpannedGridLayoutManager;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.activity.PhotoActivity;
import com.example.haritmoolphunt.facebookfeed.dao.FeedData;
import com.example.haritmoolphunt.facebookfeed.dao.Image;
import com.example.haritmoolphunt.facebookfeed.dao.PageProfile;
import com.example.haritmoolphunt.facebookfeed.dao.Posts;
import com.example.haritmoolphunt.facebookfeed.event.BusEvent;
import com.example.haritmoolphunt.facebookfeed.manager.FeedListManager;
import com.example.haritmoolphunt.facebookfeed.manager.FeedVideoManager;
import com.example.haritmoolphunt.facebookfeed.manager.PageProfileManager;
import com.example.haritmoolphunt.facebookfeed.template.Contextor;
import com.example.haritmoolphunt.facebookfeed.view.FeedListItem;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.pchmn.materialchips.ChipView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Harit Moolphunt on 13/1/2561.
 */

public class FeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int FIRST_ITEM = 0;
    private int FEED_TEXT_ITEM = 1;
    private int FEED_PICTURE_ITEM = 2;
    private int FEED_VIDEO_ITEM = 3;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      //  FeedListItem view = new FeedListItem(parent.getContext());
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, parent, false);
        if(viewType == FIRST_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.decorator_item, parent, false);
            ViewHolder0 viewHolder = new ViewHolder0(view);
            return viewHolder;
        }else
        if(viewType == FEED_TEXT_ITEM)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_feed, parent, false);
            ViewHolder1 viewHolder = new ViewHolder1(view);
            return viewHolder;
        }else
        if(viewType == FEED_PICTURE_ITEM)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_feed_picture, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_feed_video, parent, false);
            ViewHolder2 viewHolder = new ViewHolder2(view);
            return viewHolder;
        }
    }


    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return FIRST_ITEM;
        }else
        if(FeedListManager.getInstance().getDao().getFeed().get(--position).getAttachments() == null){
            return FEED_TEXT_ITEM;
        }else
        if(FeedListManager.getInstance().getDao().getFeed().get(position).getAttachments().getData().get(0).getType().compareTo("video_inline") != 0){
            return FEED_PICTURE_ITEM;
        }else{
            return FEED_VIDEO_ITEM;
        }

        //return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == FEED_TEXT_ITEM) {
            position--;
            PageProfile pageProfile = PageProfileManager.getInstance().getDao();
            FeedData dao = FeedListManager.getInstance().getDao().getFeed().get(position);
            ViewHolder1 viewHolder = (ViewHolder1) holder;
            viewHolder.profileName.setText(pageProfile.getName());

            Glide.with(viewHolder.profilePicture.getContext())
                    .load(pageProfile.getPicture().getData().getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.profilePicture);
            setClickProfileImage(viewHolder.profilePicture,viewHolder.profileName);

            viewHolder.description.setText(dao.getMessage());
            viewHolder.timestamp.setText(getTimeAgoFromUTCString(dao.getCreatedTime()));

        }else
        if(getItemViewType(position) == FEED_PICTURE_ITEM){
            position--;
            PageProfile pageProfile = PageProfileManager.getInstance().getDao();
            final FeedData dao = FeedListManager.getInstance().getDao().getFeed().get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.profileName.setText(pageProfile.getName());

            Glide.with(viewHolder.profilePicture.getContext())
                    .load(pageProfile.getPicture().getData().getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.profilePicture);
            setClickProfileImage(viewHolder.profilePicture,viewHolder.profileName);

            viewHolder.description.setText(dao.getMessage());
            viewHolder.timestamp.setText(getTimeAgoFromUTCString(dao.getCreatedTime()));
            //viewHolder.image.setVisibility(View.VISIBLE);
            //viewHolder.photoGridRecyclerView.setVisibility(View.VISIBLE);
            if (dao.getAttachments() != null) {

                if (dao.getAttachments().getData().get(0).getMedia() != null) {
                    viewHolder.chip.setVisibility(View.GONE);
                    RequestOptions requestOptions = new RequestOptions().fitCenter();
                    requestOptions.placeholder(R.drawable.placeholder);

                    Glide.with(viewHolder.image.getContext()).setDefaultRequestOptions(requestOptions)
                            .load(dao.getAttachments().getData().get(0).getMedia().getImage().getSrc())
                            .into(viewHolder.image);

                    final String[] urlList = {dao.getAttachments().getData().get(0).getMedia().getImage().getSrc()};

                    viewHolder.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EventBus.getDefault().post(new BusEvent.PhotoActivityEvent(urlList));
                        }
                    });

                }else
                if(dao.getAttachments().getData().get(0).getSubattachments() != null){
                    viewHolder.chip.setVisibility(View.VISIBLE);
                    viewHolder.chip.setLabel("1/"+dao.getAttachments().getData().get(0).getSubattachments().getData().size());
                    RequestOptions requestOptions = new RequestOptions().fitCenter();
                    requestOptions.placeholder(R.drawable.placeholder);
                    Glide.with(viewHolder.image.getContext()).setDefaultRequestOptions(requestOptions)
                            .load(dao.getAttachments().getData().get(0).getSubattachments().getData().get(0).getMedia().getImage().getSrc())
                            .into(viewHolder.image);

                    final String[] urlList = new String[dao.getAttachments().getData().get(0).getSubattachments().getData().size()];
                    for(int i =0;i<dao.getAttachments().getData().get(0).getSubattachments().getData().size();i++)
                    {
                        urlList[i] = dao.getAttachments().getData().get(0).getSubattachments().getData().get(i).getMedia().getImage().getSrc();
                    }

                    viewHolder.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EventBus.getDefault().post(new BusEvent.PhotoActivityEvent(urlList));
                        }
                    });
                }
            }

        }else
        if(getItemViewType(position) == FEED_VIDEO_ITEM){
            position--;
            PageProfile pageProfile = PageProfileManager.getInstance().getDao();
            final FeedData dao = FeedListManager.getInstance().getDao().getFeed().get(position);
            ViewHolder2 viewHolder = (ViewHolder2) holder;
            viewHolder.profileName.setText(pageProfile.getName());

            Glide.with(viewHolder.profilePicture.getContext())
                    .load(pageProfile.getPicture().getData().getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.profilePicture);
            setClickProfileImage(viewHolder.profilePicture,viewHolder.profileName);

            String video_url = "";
            for(int i=0;i< FeedVideoManager.getInstance().getDao().getData().size();i++)
            {
                if(FeedVideoManager.getInstance().getDao().getData().get(i).getId().compareTo(dao.getId().substring(dao.getId().lastIndexOf("_") + 1)) == 0)
                    video_url = FeedVideoManager.getInstance().getDao().getData().get(i).getSource();
            }

            viewHolder.description.setText(dao.getMessage());
            viewHolder.timestamp.setText(getTimeAgoFromUTCString(dao.getCreatedTime()));


                viewHolder.video.setUp(video_url, JZVideoPlayerStandard.SCREEN_WINDOW_LIST, "");
                viewHolder.video.positionInList = position;
                Glide.with(viewHolder.video.getContext())
                        .load(dao.getAttachments().getData().get(0).getMedia().getImage().getSrc()).into(((ViewHolder2) holder).video.thumbImageView);
              /*
            RequestOptions requestOptions = new RequestOptions().fitCenter();

            Glide.with(viewHolder.videoView.getContext())
                    .load(dao.getAttachments().getData().get(0).getMedia().getImage().getSrc()).into(viewHolder.videoView.getCoverView());
            viewHolder.videoView.setVideoPath(video_url).setFingerprint(position); */

        }


    }

    public void setClickProfileImage(View profileImageView, final TextView textView){

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new BusEvent.ProfileActivityEvent());
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new BusEvent.ProfileActivityEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(FeedListManager.getInstance().getDao() == null)
            return 0;
        if(FeedListManager.getInstance().getDao().getFeed() == null)
            return 0;

        return FeedListManager.getInstance().getDao().getFeed().size();
    }

    public class ViewHolder0 extends RecyclerView.ViewHolder {
        public ViewHolder0(View view) {
            super(view);

        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        ImageView profilePicture;
        TextView profileName;
        TextView timestamp;
        TextView description;
        //RecyclerView photoGridRecyclerView;


        public ViewHolder1(View view) {
            super(view);
            profilePicture = view.findViewById(R.id.profile_picture);
            profileName = view.findViewById(R.id.profile_name);
            timestamp = view.findViewById(R.id.timestamp);
            description = view.findViewById(R.id.description);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePicture;
        TextView profileName;
        TextView timestamp;
        TextView description;
        ImageView image;
        ChipView chip;
        VideoView videoView;
        //RecyclerView photoGridRecyclerView;


        public ViewHolder(View view) {
            super(view);
            profilePicture = view.findViewById(R.id.profile_picture);
            profileName = view.findViewById(R.id.profile_name);
            timestamp = view.findViewById(R.id.timestamp);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.feedImage);
            chip = view.findViewById(R.id.chip_view);
            //photoGridRecyclerView = view.findViewById(R.id.photoGridRecyclerView);

           // videoView = view.findViewById(R.id.videoView);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        ImageView profilePicture;
        TextView profileName;
        TextView timestamp;
        TextView description;
        JZVideoPlayerStandard video;

        //RecyclerView photoGridRecyclerView;


        public ViewHolder2(View view) {
            super(view);
            profilePicture = view.findViewById(R.id.profile_picture);
            profileName = view.findViewById(R.id.profile_name);
            timestamp = view.findViewById(R.id.timestamp);
            description = view.findViewById(R.id.description);
            video = view.findViewById(R.id.videoplayer);
            //photoGridRecyclerView = view.findViewById(R.id.photoGridRecyclerView);


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
