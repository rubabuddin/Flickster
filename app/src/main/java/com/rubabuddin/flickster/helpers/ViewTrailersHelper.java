package com.rubabuddin.flickster.helpers;

import android.util.Log;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rubab.uddin on 10/13/2016.
 */

public class ViewTrailersHelper {



    String source = null ;
    AsyncHttpClient client;
    JSONArray videoJsonResult = null;
    JSONArray qtVideoJsonResult = null;

    public void showVideo(String id, final String activity, final YouTubePlayerView playerDetailYoutube)
    {
        String url = "https://api.themoviedb.org/3/movie/"+id+"/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    videoJsonResult = response.getJSONArray("youtube");
                    qtVideoJsonResult = response.getJSONArray("quicktime");

                    for (int i = 0; i < videoJsonResult.length(); i++) {
                        JSONObject obj = videoJsonResult.getJSONObject(i);
                        source = obj.getString("source");
                    }

                    if (source != null) {
                        playerDetailYoutube.initialize("AIzaSyBYoI4csJI6DHNy34nmpOW1LBJvHqfODPU",
                                new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                        YouTubePlayer youTubePlayer, boolean b) {
                                        // do any work here to cue video, play video, etc.
                                        //youTubePlayer.cueVideo("5xVh-7ywKpE");
                                        if(activity == "default"){
                                            youTubePlayer.cueVideo(source);
                                        } else {
                                            youTubePlayer.setFullscreen(true);
                                            youTubePlayer.loadVideo(source);
                                        }

                                    }
                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                        YouTubeInitializationResult youTubeInitializationResult) {
                                    }
                                });
                    }

                    Log.d("Debug", videoJsonResult.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}