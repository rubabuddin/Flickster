package com.rubabuddin.flickster;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rubabuddin.flickster.helpers.ViewTrailersHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayVideoActivity extends YouTubeBaseActivity {
    @BindView(R.id.playerPopularMovie) YouTubePlayerView playerPopularMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        ButterKnife.bind(this);
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");

        ViewTrailersHelper viewTrailersHelper = new ViewTrailersHelper();
        viewTrailersHelper.showVideo(id, "popular", playerPopularMovie);
    }
}
