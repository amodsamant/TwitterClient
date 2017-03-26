package com.twitterclient.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.twitterclient.R;
import com.twitterclient.databinding.ActivityTweetDetailBinding;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.DateGenericUtils;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {

    ActivityTweetDetailBinding actBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actBinding = DataBindingUtil.setContentView(this,R.layout.activity_tweet_detail);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        actBinding.tvUsername.setText(tweet.getUser().getName());
        actBinding.tvScreenName.setText("@"+tweet.getUser().getScreenName());
        actBinding.tvText.setText(tweet.getBody());

        actBinding.tvRetweetCount.setText(tweet.getRetweetCount()+ " RETWEETS");
        actBinding.tvLikeCount.setText(tweet.getFavouritesCount()+ " LIKES");

        actBinding.ivUser.setImageResource(0);
        Glide.with(this).load(tweet.getUser().getProfileImageUrl())
                .fitCenter().into(actBinding.ivUser);


        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                !tweet.getEntities().getMedia().isEmpty()  &&
                tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {
            Log.d("DEBUG", tweet.getEntities().getMedia().get(0).getMediaUrlHttps());
            actBinding.ivTweet.setVisibility(View.VISIBLE);
            actBinding.ivTweet.setImageResource(0);
            Glide.with(this).load(tweet.getEntities().getMedia().get(0).getMediaUrlHttps())
                    .fitCenter().into(actBinding.ivTweet);
        } else {
            actBinding.ivTweet.setVisibility(View.GONE);

        }

        if(!tweet.getUser().isVerified()) {
            actBinding.ivVerified.setVisibility(View.GONE);
        }

        actBinding.tvDate.setText(DateGenericUtils.getDate(tweet.getCreatedAt()));
        actBinding.tvTime.setText(DateGenericUtils.getTime(tweet.getCreatedAt()));

    }
}
