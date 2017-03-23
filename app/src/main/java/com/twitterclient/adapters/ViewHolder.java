package com.twitterclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitterclient.R;

public class ViewHolder extends RecyclerView.ViewHolder  {

    ImageView ivUser;
    TextView tvUser;
    TextView tvScreenName;
    TextView tvBody;

    Button tvReply;
    Button tvRetweet;
    Button tvLike;

    public ViewHolder(View itemView) {
        super(itemView);

        ivUser = (ImageView) itemView.findViewById(R.id.ivUserBR);
        tvUser = (TextView) itemView.findViewById(R.id.tvUsernameBR);
        tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenNameBR);
        tvBody = (TextView) itemView.findViewById(R.id.tvTextBR);

        tvReply = (Button) itemView.findViewById(R.id.tvReplyBR);
        tvRetweet = (Button) itemView.findViewById(R.id.tvRetweetBR);
        tvLike = (Button) itemView.findViewById(R.id.tvLikeBR);
    }


}