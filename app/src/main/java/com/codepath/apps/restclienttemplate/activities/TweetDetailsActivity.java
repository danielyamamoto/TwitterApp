package com.codepath.apps.restclienttemplate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    Tweet mTweet;

    ImageView ivProfileImage, ivUrlImage;
    TextView tvName, tvScreenName, tvBody, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImageDetails);
        tvName = (TextView) findViewById(R.id.tvNameDetails);
        tvScreenName = (TextView) findViewById(R.id.tvScreenNameDetails);
        tvBody = (TextView) findViewById(R.id.tvBodyDetails);
        ivUrlImage = (ImageView) findViewById(R.id.ivUrlImageDetails);
        tvDate = (TextView) findViewById(R.id.tvDateDetails);

        // Unwrap the tweet passed in via intent, using its simple name as a key
        mTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        // Set the values
        Glide.with(this).
                load(mTweet.user.publicImageURL)
                .centerCrop()
                .transform(new RoundedCornersTransformation(100, 0))
                .into(ivProfileImage);

        tvName.setText(mTweet.user.name);
        tvScreenName.setText("@" + mTweet.user.screenName);
        tvBody.setText(mTweet.body);

        if (mTweet.mediaHttp != null) {
            ivUrlImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mTweet.mediaHttp)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(50, 10))
                    .into(ivUrlImage);
        } else{
            ivUrlImage.setVisibility(View.GONE);
        }

        tvDate.setText(mTweet.createdAt);
    }
}