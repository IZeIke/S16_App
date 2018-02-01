package com.example.haritmoolphunt.facebookfeed.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haritmoolphunt.facebookfeed.R;

/**
 * Created by Harit Moolphunt on 2/2/2561.
 */

public class NameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    String[] nameList;
    public NameListAdapter(String[] namelist){
        nameList = namelist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_textview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(nameList[position]);
    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.name_textview);
        }
    }

}
