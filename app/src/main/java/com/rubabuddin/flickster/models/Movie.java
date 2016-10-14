package com.rubabuddin.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rubab.uddin on 10/8/2016.
 */
public class Movie{

    public String getId() { return id; }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w500/%s", backdropPath);
    }

    public double getVoteAverage(){
        return voteAverage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getPopularity() { return popularity;}

    public String getVideoExists() { return videoExists; }

    public String getRelease_date() { return release_date; }

    public int getVoteCount() { return voteCount; }

    String id;
    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    double popularity;
    double voteAverage;
    String videoExists;
    String release_date;
    int voteCount;



    public Movie(JSONObject jsonObject) throws JSONException { //throws exception in the case that the JSON object isn't found
        this.id = jsonObject.getString("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.popularity = jsonObject.getDouble("popularity");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.videoExists = jsonObject.getString("video");
        this.release_date = jsonObject.getString("release_date");
        this.voteCount = jsonObject.getInt("vote_count");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for(int x =0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public boolean isPopular(){
        if(getVoteAverage() > 5){
            return true;
        } else {
            return false;
        }
    }
}
