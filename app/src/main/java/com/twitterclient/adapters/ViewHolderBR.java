package com.twitterclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.malmstein.fenster.view.FensterVideoView;
import com.twitterclient.R;

public class ViewHolderBR extends RecyclerView.ViewHolder  {

    ImageView ivUser;
    TextView tvUser;
    ImageView ivVerified;
    TextView tvScreenName;
    TextView tvBody;
    TextView tvRelTime;

    ImageView ivTweet;

    Button btnReply;
    Button btnRetweet;
    Button btnLike;

    FensterVideoView textureView;
//    MediaFensterPlayerController playerController;

    public ViewHolderBR(View itemView) {
        super(itemView);

        ivUser = (ImageView) itemView.findViewById(R.id.ivUserBR);

        tvUser = (TextView) itemView.findViewById(R.id.tvUsernameBR);
        ivVerified = (ImageView) itemView.findViewById(R.id.ivVerified);
        tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenNameBR);
        tvBody = (TextView) itemView.findViewById(R.id.tvTextBR);
        tvRelTime = (TextView) itemView.findViewById(R.id.tvSinceTimeBR);

        ivTweet = (ImageView) itemView.findViewById(R.id.ivTweetBR);

        btnReply = (Button) itemView.findViewById(R.id.btnReplyBR);
        btnRetweet = (Button) itemView.findViewById(R.id.btnRetweetBR);
        btnLike = (Button) itemView.findViewById(R.id.btnLikeBR);

        textureView = (FensterVideoView) itemView.findViewById(R.id.play_video_texture);
//        playerController = (MediaFensterPlayerController) itemView.findViewById(R.id.play_video_controller);

    }


}