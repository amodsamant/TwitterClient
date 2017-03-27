package com.twitterclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.twitterclient.R;
import com.twitterclient.activities.TweetDetailActivity;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.DateGenericUtils;
import com.twitterclient.utils.GenericUtils;

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

        final ViewHolderBR viewHolder = (ViewHolderBR) holder;

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

        String profileImageUrl = GenericUtils.modifyProfileImageUrl(tweet.getUser().getProfileImageUrl());
        Glide.with(context).load(profileImageUrl)
                .fitCenter()
                .bitmapTransform(new RoundedCornersTransformation(context,5,0))
                .into(viewHolder.ivUser);

        viewHolder.ivTweet.setImageResource(0);
        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                !tweet.getEntities().getMedia().isEmpty()  &&
                tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {
            Log.d("DEBUG", tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":large");

            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            window.getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            Glide.with(context).load(tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":medium")
                    .override(600,200)
                    .fitCenter()
                    .bitmapTransform( new RoundedCornersTransformation(context,10,0))
                    .into(viewHolder.ivTweet);
        }

        if(tweet.getExtendedEntities()!=null && tweet.getExtendedEntities().getMedia()!=null &&
                !tweet.getExtendedEntities().getMedia().isEmpty()  &&
                tweet.getExtendedEntities().getMedia().get(0).getType().equalsIgnoreCase("video")) {

            String videoUrl = tweet.getExtendedEntities()
                    .getMedia().get(0).getVideoInfo().getVariants().get(0).getUrl();

        }

        viewHolder.tvBody.setText(tweet.getBody());

        viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
        viewHolder.btnRetweet.setText(String.valueOf(tweet.getRetweetCount()));
        viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
        if(!tweet.isFavorited()) {
            Drawable img = context.getResources().getDrawable(R.drawable.ic_favorite);
            viewHolder.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tweet.isFavorited()) {
                    Drawable img = context.getResources().getDrawable(R.drawable.ic_favorite_set);
                    viewHolder.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setFavouritesCount(tweet.getFavouritesCount() + 1);
                    tweet.setFavorited(true);
                    viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
                } else {
                    Drawable img = context.getResources().getDrawable(R.drawable.ic_favorite);
                    viewHolder.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setFavouritesCount(tweet.getFavouritesCount() - 1);
                    tweet.setFavorited(false);
                    viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
                }
            }
        });

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
