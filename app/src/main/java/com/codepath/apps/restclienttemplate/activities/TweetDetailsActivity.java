package com.codepath.apps.restclienttemplate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityTweetDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    Tweet mTweet;

    ImageView ivProfileImage, ivUrlImage, ivBack;
    TextView tvName, tvScreenName, tvBody, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTweetDetailsBinding binding = ActivityTweetDetailsBinding.inflate(getLayoutInflater());
        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        // Bindings
        ivProfileImage = binding.ivProfileImageDetails;
        tvName = binding.tvNameDetails;
        tvScreenName = binding.tvScreenNameDetails;
        tvBody = binding.tvBodyDetails;
        ivUrlImage = binding.ivUrlImageDetails;
        tvDate = binding.tvDateDetails;
        ivBack = binding.tbBack;

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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