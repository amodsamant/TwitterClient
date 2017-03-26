package com.twitterclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitterclient.R;
import com.twitterclient.activities.TweetDetailActivity;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.DateGenericUtils;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .bitmapTransform(new RoundedCornersTransformation(context,5,5))
                .into(viewHolder.ivUser);

        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                !tweet.getEntities().getMedia().isEmpty()  &&
                tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {

            viewHolder.ivTweet.setImageResource(0);
            Glide.with(context).load(tweet.getEntities().getMedia().get(0).getMediaUrlHttps())
                    .fitCenter()
                    .bitmapTransform( new RoundedCornersTransformation(context,20,10))
                    .into(viewHolder.ivTweet);
        }

        if(tweet.getExtendedEntities()!=null && tweet.getExtendedEntities().getMedia()!=null &&
                !tweet.getExtendedEntities().getMedia().isEmpty()  &&
                tweet.getExtendedEntities().getMedia().get(0).getType().equalsIgnoreCase("video")) {

            String videoUrl = tweet.getExtendedEntities()
                    .getMedia().get(0).getVideoInfo().getVariants().get(0).getUrl();

//            viewHolder.textureView.setMediaController(viewHolder.playerController);

            viewHolder.textureView.setVideo(videoUrl);
            viewHolder.textureView.start();

        }




        viewHolder.tvBody.setText(tweet.getBody());

        viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
        viewHolder.btnRetweet.setText(String.valueOf(tweet.getRetweetCount()));
        viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));

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
