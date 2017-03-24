package com.twitterclient.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.activities.TimelineActivity;
import com.twitterclient.models.Tweet;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeTweetFragment extends DialogFragment {

    public interface ComposeTweetListener {
        void onFinishTweet(Tweet tweet);
    }


    static final String TAG = ComposeTweetFragment.class.getSimpleName();


    ImageView ivClose;
    ImageView ivUser;
    EditText etBody;
    TextView tvChars;
    Button btnTweet;

    TimelineActivity activity;

    public ComposeTweetFragment() {
    }

    public static ComposeTweetFragment getInstance() {

        ComposeTweetFragment frag = new ComposeTweetFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compose_frag, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivClose = (ImageView) view.findViewById(R.id.ivClose);
        ivUser = (ImageView) view.findViewById(R.id.ivUser);
        etBody = (EditText) view.findViewById(R.id.etTweet);
        tvChars = (TextView) view.findViewById(R.id.tvLetterCount);
        btnTweet = (Button) view.findViewById(R.id.btnTweet);

        etBody.setMaxWidth(view.getWidth());
        etBody.setHintTextColor(Color.GRAY);
        etBody.setTextColor(Color.GRAY);
        etBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int count = 140 - charSequence.length();
                tvChars.setText(String.valueOf(count));
                if(count<0) {
                    tvChars.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Show save draft dialog
                dismiss();
            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tweet = etBody.getText().toString();
                if(!tweet.isEmpty()){
                    postTweet(tweet);
                }
                dismiss();
            }
        });

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        activity = (TimelineActivity) getActivity();

        Log.d(TAG,"Activity state test");

    }

    @Override
    public void onResume() {

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }


    public void postTweet(String tweetStr) {

        ((TimelineActivity)getActivity()).getTwitterCLient().postTweet(tweetStr,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        Gson gson = new Gson();
                        Log.d(TAG, response.toString());
                        try {
                            Tweet tweet = gson.fromJson(response.toString(), Tweet.class);

                            //TimelineActivity activity = (TimelineActivity) getActivity();
                            activity.onFinishTweet(tweet);

                        } catch (Exception e) {
                            Log.e(TAG,e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                          JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });


    }
}
