package com.twitterclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.twitterclient.R;
import com.twitterclient.models.Tweet;

import java.util.List;

public class TimelineRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Tweet> tweets;

    public TimelineRecyclerAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            default:
                View view = inflater.inflate(R.layout.basic_tweet_row, parent, false);
                viewHolder = new ViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Tweet tweet = tweets.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.tvUser.setText(tweet.getUser().getName());
        viewHolder.tvScreenName.setText("@"+tweet.getUser().getScreenName());

        viewHolder.ivUser.setImageResource(0);
        Glide.with(context).load(tweet.getUser().getProfileImageUrl())
                .fitCenter().into(viewHolder.ivUser);

        viewHolder.tvBody.setText(tweet.getBody());


    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
