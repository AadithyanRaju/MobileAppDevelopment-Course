package com.example.tvstream;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    private final Context context;
    private final List<Channel> channelList;

    // Constructor
    public ChannelAdapter(Context context, List<Channel> channelList) {
        this.context = context;
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_channel, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel channel = channelList.get(position);
        holder.channelName.setText(channel.getName());

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            // Open PlayerActivity and pass the channel URL
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("videoUrl", channel.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    // ViewHolder Class
    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        TextView channelName;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            channelName = itemView.findViewById(R.id.channelName);
        }
    }
}
