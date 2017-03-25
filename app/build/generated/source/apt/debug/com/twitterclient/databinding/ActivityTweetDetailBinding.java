package com.twitterclient.databinding;
import com.twitterclient.R;
import com.twitterclient.BR;
import android.view.View;
public class ActivityTweetDetailBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ivUser, 1);
        sViewsWithIds.put(R.id.tvUsername, 2);
        sViewsWithIds.put(R.id.tvScreenName, 3);
        sViewsWithIds.put(R.id.tvText, 4);
        sViewsWithIds.put(R.id.ivTweet, 5);
        sViewsWithIds.put(R.id.vvTweet, 6);
        sViewsWithIds.put(R.id.separator2, 7);
        sViewsWithIds.put(R.id.btnReply, 8);
        sViewsWithIds.put(R.id.btnRetweet, 9);
        sViewsWithIds.put(R.id.btnLike, 10);
        sViewsWithIds.put(R.id.separator1, 11);
        sViewsWithIds.put(R.id.tvRetweetCount, 12);
        sViewsWithIds.put(R.id.tvLikeCount, 13);
        sViewsWithIds.put(R.id.tvTime, 14);
        sViewsWithIds.put(R.id.tvDate, 15);
        sViewsWithIds.put(R.id.ivVerified, 16);
    }
    // views
    public final android.widget.Button btnLike;
    public final android.widget.Button btnReply;
    public final android.widget.Button btnRetweet;
    public final android.widget.ImageView ivTweet;
    public final android.widget.ImageView ivUser;
    public final android.widget.ImageView ivVerified;
    private final android.widget.ScrollView mboundView0;
    public final android.view.View separator1;
    public final android.view.View separator2;
    public final android.widget.TextView tvDate;
    public final android.widget.TextView tvLikeCount;
    public final android.widget.TextView tvRetweetCount;
    public final android.widget.TextView tvScreenName;
    public final android.widget.TextView tvText;
    public final android.widget.TextView tvTime;
    public final android.widget.TextView tvUsername;
    public final android.widget.VideoView vvTweet;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityTweetDetailBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds);
        this.btnLike = (android.widget.Button) bindings[10];
        this.btnReply = (android.widget.Button) bindings[8];
        this.btnRetweet = (android.widget.Button) bindings[9];
        this.ivTweet = (android.widget.ImageView) bindings[5];
        this.ivUser = (android.widget.ImageView) bindings[1];
        this.ivVerified = (android.widget.ImageView) bindings[16];
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.separator1 = (android.view.View) bindings[11];
        this.separator2 = (android.view.View) bindings[7];
        this.tvDate = (android.widget.TextView) bindings[15];
        this.tvLikeCount = (android.widget.TextView) bindings[13];
        this.tvRetweetCount = (android.widget.TextView) bindings[12];
        this.tvScreenName = (android.widget.TextView) bindings[3];
        this.tvText = (android.widget.TextView) bindings[4];
        this.tvTime = (android.widget.TextView) bindings[14];
        this.tvUsername = (android.widget.TextView) bindings[2];
        this.vvTweet = (android.widget.VideoView) bindings[6];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
        }
        return false;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ActivityTweetDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityTweetDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityTweetDetailBinding>inflate(inflater, com.twitterclient.R.layout.activity_tweet_detail, root, attachToRoot, bindingComponent);
    }
    public static ActivityTweetDetailBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityTweetDetailBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.twitterclient.R.layout.activity_tweet_detail, null, false), bindingComponent);
    }
    public static ActivityTweetDetailBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityTweetDetailBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_tweet_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityTweetDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}