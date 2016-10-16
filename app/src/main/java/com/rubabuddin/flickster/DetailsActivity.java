package com.rubabuddin.flickster;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
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
    @BindView(R.id.tvDetailTitle)
    TextView tvDetailTitle;
    @BindView(R.id.tvDetailOverview)
    TextView tvDetailOverview;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.ivDetailPoster)
    ImageView ivDetailPoster;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.tvVoteCount)
    TextView tvVoteCount;
    @BindView(R.id.playerDetailYoutube)
    YouTubePlayerView playerDetailYoutube;
    @BindView(R.id.ivAdult)
    ImageView ivAdult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        String posterPath = intent.getStringExtra("posterPath");
        final String title = intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        Double voteAverage = intent.getDoubleExtra("voteAverage", 0.0);
        String popularity = intent.getStringExtra("popularity");
        String videoExists = intent.getStringExtra("videoExists");
        int voteCount = intent.getIntExtra("voteCount", 0);
        String releaseYear = intent.getStringExtra("releaseDate").substring(0, 4);
        boolean isAdult = intent.getBooleanExtra("adult", false);

        String rating = "Unrated";
        String displayVoteCount = String.valueOf(voteCount);

        Button btnShowTimes = (Button) findViewById(R.id.btnShowTimes);



        btnShowTimes.setOnClickListener(new View.OnClickListener() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        public void onClick(View v) {
            String url = "https://www.google.com/movies?near=" + latitude+ "," + longitude + "&q=" + title.replace(" ", "%20");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            }
        });



        if(voteCount > 0) {
            DecimalFormat formatDec = new DecimalFormat("#.#");
            formatDec.setDecimalSeparatorAlwaysShown(false);
            rating = formatDec.format(voteAverage) + "/5";
        }

        ViewTrailersHelper viewTrailersHelper = new ViewTrailersHelper();
        viewTrailersHelper.showVideo(id, "default", playerDetailYoutube);
        String displayTitle = title + " ("+ releaseYear + ")";
        tvDetailTitle.setText(displayTitle);
        tvRating.setText(rating);
        tvVoteCount.setText(displayVoteCount);
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

        if(!isAdult){
            Picasso.with(this)
                    .load(R.drawable.pg13)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(ivAdult);
        }

    }
}
