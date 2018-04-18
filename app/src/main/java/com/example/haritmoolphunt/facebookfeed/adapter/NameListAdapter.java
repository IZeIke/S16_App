package com.example.haritmoolphunt.facebookfeed.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haritmoolphunt.facebookfeed.R;

/**
 * Created by Harit Moolphunt on 2/2/2561.
 */

public class NameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    String[] nameList;
    private ClickListener clickListener;

    public NameListAdapter(String[] namelist){
        nameList = namelist;
    }

    public NameListAdapter(String[] namelist,ClickListener clickListener){
        nameList = namelist;
        this.clickListener = clickListener;
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

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        Button textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.name_textview);
        }
    }

    public interface ClickListener {
        void onPositionClicked(int position);
    }

}
