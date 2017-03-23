package com.twitterclient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tweet {

    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("text")
    @Expose
    String body;

    @SerializedName("created_at")
    @Expose
    String createdAt;

    @SerializedName("user")
    @Expose
    User user;

    @SerializedName("favorited")
    @Expose
    boolean favorited;

    @SerializedName("retweeted")
    @Expose
    boolean retweeted;

    @SerializedName("retweet_count")
    @Expose
    int retweetCount;

    @SerializedName("favourites_count")
    @Expose
    int favouritesCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(int favouritesCount) {
        this.favouritesCount = favouritesCount;
    }
}
