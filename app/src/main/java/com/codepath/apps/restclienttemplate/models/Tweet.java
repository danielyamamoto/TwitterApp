package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public String body, createdAt, mediaHttp;
    public int likes, shares, replies;
    public User user;

    // Empty constructor needed by the Parceler library
    public Tweet() { }

    public  static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.likes = jsonObject.getInt("favorite_count");
        tweet.shares = jsonObject.getInt("retweet_count");
        //tweet.replies = jsonObject.getInt("reply_count");

        // If our json has extended entities
        if (!jsonObject.isNull("extended_entities")) {
            // Get media
            JSONArray jsonArray = jsonObject.getJSONObject("extended_entities").getJSONArray("media");
            // Get media-url-https
            tweet.mediaHttp = jsonArray.getJSONObject(0).getString("media_url_https");
        } else {
            tweet.mediaHttp = null;
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
