package com.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.adapters.TimelineRecyclerAdapter;
import com.twitterclient.helpers.EndlessRecyclerViewScrollListener;
import com.twitterclient.models.Tweet;
import com.twitterclient.network.TwitterClient;
import com.twitterclient.network.TwitterClientApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    List<Tweet> tweets;

    RecyclerView recyclerView;

    TimelineRecyclerAdapter adapter;

    TwitterClient twitterCLient;

    DividerItemDecoration dividerItemDecoration;
    LinearLayoutManager layoutManager;

    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        binding = DataBindingUtil.setContentView(this,R.layout.activity_timeline);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tweets = new ArrayList<>();

        adapter = new TimelineRecyclerAdapter(this,tweets);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //loadNextDataFromApi(page);
            }
        };

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        twitterCLient = TwitterClientApplication.getTwitterClient();


        populateTimeline();
    }

    /**
     * Override this function to close the app from the timeline activity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void populateTimeline() {


        twitterCLient.getTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Log.d("DEBUG", response.toString());
                List<Tweet> respTweets = new ArrayList<>();
                Gson gson = new Gson();
                for(int x = 0; x < response.length();x++) {
                    try {
                        Tweet tweet = gson.fromJson(response.getJSONObject(x).toString(),
                                Tweet.class);
                        respTweets.add(tweet);
                    } catch (JSONException e) {
                        //TODO:
                    }
                }

                tweets.addAll(respTweets);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }
}
