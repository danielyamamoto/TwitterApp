package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.ParseRelativeDate;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

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

    // Define a viewholder
    public  class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage, ivUrlImage;
        TextView tvBody, tvScreenName, tvDate;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivUrlImage = itemView.findViewById(R.id.ivUrlImage);
        }

        public void bind(Tweet tweet) {
            ParseRelativeDate parse =  new ParseRelativeDate(); // Parse Date obj

            tvBody.setText(tweet.body);
            tvDate.setText(parse.getRelativeTimeAgo(tweet.createdAt)); // Parse the date
            tvScreenName.setText(tweet.user.screenName);
            Glide.with(context).load(tweet.user.publicImageURL).into(ivProfileImage);

            if (tweet.mediaHttp != null) {
                ivUrlImage.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.mediaHttp)
                        //.centerCrop()
                        //.transform(new RoundedCornersTransformation(50, 0))
                        //.transform(new RoundedCorners(50))
                        .into(ivUrlImage);
            }else{
                ivUrlImage.setVisibility(View.GONE);
            }

        }
    }
}
