package com.twitterclient.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.activities.TimelineActivity;
import com.twitterclient.models.Tweet;
import com.twitterclient.models.User;
import com.twitterclient.network.TwitterClient;
import com.twitterclient.network.TwitterClientApplication;
import com.twitterclient.utils.GenericUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ComposeTweetFragment extends DialogFragment
        implements DraftsFragment.DraftsFragmentListener{

    public interface ComposeTweetListener {
        void onFinishTweet(Tweet tweet);
    }

    static final String TAG = ComposeTweetFragment.class.getSimpleName();

    ImageView ivClose;
    ImageView ivUser;
    EditText etBody;
    TextView tvChars;
    Button btnTweet;

    Button btnDraft;


    static final String TWEET = "tweet";

    public ComposeTweetFragment() {
    }

    public static ComposeTweetFragment getInstance(String tweetData) {

        ComposeTweetFragment frag = new ComposeTweetFragment();
        Bundle args = new Bundle();
        args.putString(TWEET,tweetData);
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

        btnDraft = (Button) view.findViewById(R.id.btnDraft);

        if(getDrafts().isEmpty()) {
            btnDraft.setVisibility(View.GONE);
        }

        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DraftsFragment fragment = DraftsFragment.getInstance();
                fragment.setTargetFragment(ComposeTweetFragment.this, 0);
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(fm,"drafts_frag");
            }
        });

        etBody.setHorizontallyScrolling(false);
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
                    btnTweet.setClickable(false);
                    btnTweet.setBackgroundColor(ContextCompat
                            .getColor(getContext(),R.color.fadedBlue));
                } else {
                    btnTweet.setBackgroundColor(ContextCompat
                            .getColor(getContext(),R.color.twitterBlue));
                    btnTweet.setClickable(true);
                    tvChars.setTextColor(ContextCompat
                            .getColor(getContext(),R.color.grey));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        String screenName = getArguments().getString(TWEET);
        if(screenName!=null) {
            String tweetTo = screenName;
            etBody.setText(tweetTo);
            etBody.setSelection(tweetTo.length());
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etBody.getText().length()>0) {
                    MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                            .content("Save draft?")
                            .positiveText("SAVE")
                            .negativeText("DELETE")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    onSaveDraft(etBody.getText().toString());
                                    btnDraft.setVisibility(View.VISIBLE);
                                    dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dismiss();
                                }
                            }).build();

                    dialog.show();
                } else {
                    dismiss();
                }
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

        TwitterClient twitterCLient = TwitterClientApplication.getTwitterClient();
        twitterCLient.getPersonalUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                User user = gson.fromJson(response.toString(),
                        User.class);

                ivUser.setImageResource(0);
                String profileImageUrl = GenericUtils.modifyProfileImageUrl(user.getProfileImageUrl());
                Glide.with(getContext()).load(profileImageUrl)
                        .fitCenter()
                        .bitmapTransform(new RoundedCornersTransformation(getContext(),5,0))
                        .into(ivUser);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
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

        final TimelineActivity activity = (TimelineActivity)getActivity();
        activity.getTwitterClient().postTweet(tweetStr,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        Gson gson = new Gson();
                        Log.d(TAG, response.toString());
                        try {
                            Tweet tweet = gson.fromJson(response.toString(), Tweet.class);

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


    private void onSaveDraft(String draftTweet) {

        SharedPreferences draftsPref = getSharedPreferences();

        SharedPreferences.Editor editor = draftsPref.edit();
        editor.putString(draftTweet, draftTweet);
        editor.apply();

        btnDraft.setVisibility(View.VISIBLE);

    }


    private SharedPreferences getSharedPreferences() {

        SharedPreferences draftsPref = getActivity()
                .getSharedPreferences("Drafts", Context.MODE_PRIVATE);

        return draftsPref;
    }

    private List<String> getDrafts() {

        SharedPreferences sharedPreferences = getSharedPreferences();
        Map<String,String> draftsMap = (Map<String, String>) sharedPreferences.getAll();
        List<String> drafts = new ArrayList<>(draftsMap.values());
        if(drafts!=null && !drafts.isEmpty()) {
            Log.d("DEBUG", drafts.get(0));
        }

        return drafts;
    }

    @Override
    public void onFinishDraft(String tweet) {

        etBody.setText(tweet);
        etBody.setSelection(tweet.length());

        if(getDrafts()==null || getDrafts().isEmpty()) {
            btnDraft.setVisibility(View.GONE);
        }
    }
}
