package com.twitterclient.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twitterclient.R;
import com.twitterclient.fragments.ComposeTweetFragment;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.DateGenericUtils;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class TweetDetailActivity extends AppCompatActivity {

//    ActivityTweetDetailBinding actBinding;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
//        actBinding = DataBindingUtil.setContentView(this,R.layout.activity_tweet_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tweet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);

        TextView tvText = (TextView) findViewById(R.id.tvText);
        TextView tvRetweetCount = (TextView) findViewById(R.id.tvRetweetCount);
        TextView tvLikeCount = (TextView) findViewById(R.id.tvLikeCount);

        ImageView ivUser = (ImageView) findViewById(R.id.ivUser);
        ImageView ivTweet = (ImageView) findViewById(R.id.ivTweet);
        ImageView ivVerified = (ImageView) findViewById(R.id.ivVerified);

        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        TextView tvTime = (TextView) findViewById(R.id.tvTime);

//        final EditText etReply = (EditText) findViewById(R.id.etReplyDetail);
//        Button btnReplyDetail = (Button) findViewById(R.id.btnTweetDetail);


        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvUsername.setText(tweet.getUser().getName());
        tvScreenName.setText("@"+tweet.getUser().getScreenName());
        tvText.setText(tweet.getBody());

        tvRetweetCount.setText(tweet.getRetweetCount()+ " RETWEETS");
        tvLikeCount.setText(tweet.getFavouritesCount()+ " LIKES");

        ivUser.setImageResource(0);
        Glide.with(this).load(tweet.getUser().getProfileImageUrl())
                .fitCenter()
                .bitmapTransform( new RoundedCornersTransformation(this,5,5))
                .into(ivUser);

        ivTweet.setImageResource(0);
        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                !tweet.getEntities().getMedia().isEmpty()  &&
                tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {
            Log.d("DEBUG", tweet.getEntities().getMedia().get(0).getMediaUrlHttps());

            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            ivTweet.setVisibility(View.VISIBLE);

            Glide.with(this).load(tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":large")
                    .override(width,height)
                    .fitCenter()
                    .bitmapTransform( new RoundedCornersTransformation(this,20,5))
                    .into(ivTweet);
        } else {
            ivTweet.setVisibility(View.GONE);

        }


        if(!tweet.getUser().isVerified()) {
            ivVerified.setVisibility(View.GONE);
        }

        tvDate.setText(DateGenericUtils.getDate(tweet.getCreatedAt()));
        tvTime.setText(DateGenericUtils.getTime(tweet.getCreatedAt()));


        Button btnReply = (Button) findViewById(R.id.btnReply);
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                ComposeTweetFragment fragment = ComposeTweetFragment
                        .getInstance(tweet.getUser().getScreenName());
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(fm,"compose_frag");
            }
        });


        final Button btnLike = (Button) findViewById(R.id.btnLike);
        if(tweet.isFavorited()) {
            Drawable img = getResources().getDrawable(R.drawable.ic_favorite_set);
            btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }


//        etReply.setHint("Reply to @"+tweet.getUser().getScreenName());
//        etReply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String replyTo = "@"+ tweet.getUser().getScreenName();
//                etReply.setText(replyTo);
//                etReply.setSelection(replyTo.length());
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
