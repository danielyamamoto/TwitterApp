package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.ParseRelativeDate;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.activities.TweetDetailsActivity;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;

    // Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);
        // Bind the data with view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    // Define a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage, ivUrlImage;
        TextView tvBody, tvScreenName, tvName, tvDate, tvLike, tvRetweet, tvMsg;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            ivUrlImage = (ImageView) itemView.findViewById(R.id.ivUrlImage);
            tvLike = (TextView) itemView.findViewById(R.id.tvLike);
            tvRetweet = (TextView) itemView.findViewById(R.id.tvRetweet);
            tvMsg = (TextView) itemView.findViewById(R.id.tvMsg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gets item position
                    int position = getAdapterPosition();
                    // Make sure the position is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the tweet at the position, this won't work if the class is static
                        Tweet tweet = tweets.get(position);
                        // create intent for the new activity
                        Intent intent = new Intent(context, TweetDetailsActivity.class);
                        // serialize the tweet using parceler, use its short name as a key
                        intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                        // show the activity
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void bind(Tweet tweet) {
            ParseRelativeDate parse =  new ParseRelativeDate(); // Parse Date obj

            tvBody.setText(tweet.body);
            tvDate.setText(parse.getRelativeTimeAgo(tweet.createdAt)); // Parse the date
            tvName.setText(tweet.user.name);
            tvScreenName.setText("@" + tweet.user.screenName);

            Glide.with(context).
                    load(tweet.user.publicImageURL)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(100, 0))
                    .into(ivProfileImage);

            if (tweet.mediaHttp != null) {
                ivUrlImage.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.mediaHttp)
                        .centerCrop()
                        .transform(new RoundedCornersTransformation(50, 10))
                        .into(ivUrlImage);
            } else{
                ivUrlImage.setVisibility(View.GONE);
            }

            tvLike.setText(String.valueOf(tweet.likes));
            tvRetweet.setText(String.valueOf(tweet.shares));
            tvMsg.setText(String.valueOf(tweet.replies));
        }
    }
}
