package com.twitterclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.twitterclient.R;
import com.twitterclient.activities.TweetDetailActivity;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.DateGenericUtils;

import org.parceler.Parcels;

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
                viewHolder = new ViewHolderBR(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Tweet tweet = tweets.get(position);

        ViewHolderBR viewHolder = (ViewHolderBR) holder;

        viewHolder.tvUser.setText(tweet.getUser().getName());

        if(tweet.getUser()!=null && tweet.getUser().isVerified()) {
            viewHolder.ivVerified.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivVerified.setVisibility(View.GONE);
        }

        viewHolder.tvScreenName.setText("@"+tweet.getUser().getScreenName());

        viewHolder.tvRelTime.setText(DateGenericUtils.
                getRelativeTimeAgo(tweet.getCreatedAt()));

        viewHolder.ivUser.setImageResource(0);

        Glide.with(context).load(tweet.getUser().getProfileImageUrl())
                .fitCenter().into(viewHolder.ivUser);

//        if(tweet.getEntities()!=null && tweet.getEntities().getUrls()!=null &&
//                !tweet.getEntities().getUrls().isEmpty()  &&
//                tweet.getEntities().getUrls().get(0).getExpandedUrl()!=null) {
//
//            Glide.with(context).load(tweet.getEntities().getUrls().get(0).getExpandedUrl())
//                    .fitCenter().into(viewHolder.ivTweet);
//        }

        viewHolder.tvBody.setText(tweet.getBody());

        viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
        viewHolder.btnRetweet.setText(String.valueOf(tweet.getRetweetCount()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, TweetDetailActivity.class);
                intent.putExtra("tweet", Parcels.wrap(tweet));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
