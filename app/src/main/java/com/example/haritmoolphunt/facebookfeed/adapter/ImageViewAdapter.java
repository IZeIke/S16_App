package com.example.haritmoolphunt.facebookfeed.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arasthel.spannedgridlayoutmanager.SpanLayoutParams;
import com.arasthel.spannedgridlayoutmanager.SpanSize;
import com.bumptech.glide.Glide;
import com.example.haritmoolphunt.facebookfeed.R;
import com.example.haritmoolphunt.facebookfeed.dao.Datum__;
import com.example.haritmoolphunt.facebookfeed.dao.Image;

import java.util.List;

/**
 * Created by Harit Moolphunt on 10/2/2561.
 */

public class ImageViewAdapter extends RecyclerView.Adapter<ViewHolder>{

    private List<Datum__> daoImage;

    public ImageViewAdapter(List<Datum__> Image){
        daoImage = Image;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(viewHolder.imageView.getContext())
                .load(daoImage.get(position).getMedia().getImage().getSrc())
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return daoImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view_item);

        }
    }
}
