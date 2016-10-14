package com.rubabuddin.flickster;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rubabuddin.flickster.helpers.ViewTrailersHelper;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailsActivity extends YouTubeBaseActivity {
    @BindView(R.id.tvDetailTitle) TextView tvDetailTitle;
    @BindView(R.id.tvDetailOverview) TextView tvDetailOverview;
    @BindView(R.id.tvRating) TextView tvRating;
    @BindView(R.id.ivDetailPoster) ImageView ivDetailPoster;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.playerDetailYoutube) YouTubePlayerView playerDetailYoutube;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        String posterPath = intent.getStringExtra("posterPath");
        String title = intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        Double voteAverage = intent.getDoubleExtra("voteAverage", 0.0);
        String popularity = intent.getStringExtra("popularity");
        String videoExists = intent.getStringExtra("videoExists");
        int voteCount = intent.getIntExtra("voteCount", 0);
        String releaseDate = "Released: "+ intent.getStringExtra("releaseDate");
        String rating = "Unrated";

        if(voteCount > 0) {
            DecimalFormat formatDec = new DecimalFormat();
            formatDec.setDecimalSeparatorAlwaysShown(false);
            rating = formatDec.format(voteAverage) + "/5";
        }

        ViewTrailersHelper viewTrailersHelper = new ViewTrailersHelper();
        viewTrailersHelper.showVideo(id, playerDetailYoutube);

        tvDetailTitle.setText(title);
        tvRating.setText(rating);
        tvDetailOverview.setText(overview);
        ratingBar.setRating(voteAverage.floatValue());
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        Picasso.with(this)
                .load(posterPath)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.progress_animation)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(ivDetailPoster);

    }
}
