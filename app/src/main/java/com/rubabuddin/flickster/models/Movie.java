package com.rubabuddin.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rubab.uddin on 10/8/2016.
 */
public class Movie{

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

    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    double voteAverage;

    public Movie(JSONObject jsonObject) throws JSONException { //throws exception in the case that the JSON object isn't found
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.voteAverage = jsonObject.getDouble("vote_average");
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
}
