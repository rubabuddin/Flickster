package com.rubabuddin.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rubabuddin.flickster.adapters.MovieArrayAdapter;
import com.rubabuddin.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.lvMovies) ListView lvItems;

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    String url = "";

    final String toastNowPlaying = "Now Playing in Theatres";
    final String urlNowPlaying = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    final String toastPopular = "Popular in Theatres";
    final String urlPopular = "https://api.themoviedb.org/3/movie/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setTitle(Html.fromHtml(getString(R.string.html_title_now_playing)));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.actionbar_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        ButterKnife.bind(this);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        url = urlNowPlaying;
        getMovieList();
        setupSwipeToRefresh();
        setupListViewListener();
    }

    public void setupListViewListener(){
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = movies.get(position);

                if(selectedMovie.isPopular()){

                    Intent intent = new Intent(MovieActivity.this, PlayVideoActivity.class);
                    intent.putExtra("id", selectedMovie.getId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MovieActivity.this, DetailsActivity.class);
                    intent.putExtra("id", selectedMovie.getId());
                    intent.putExtra("posterPath", selectedMovie.getPosterPath());
                    intent.putExtra("title",selectedMovie.getOriginalTitle());
                    intent.putExtra("voteAverage", selectedMovie.getVoteAverage());
                    intent.putExtra("popularity", selectedMovie.getPopularity());
                    intent.putExtra("videoExists", selectedMovie.getVideoExists());
                    intent.putExtra("overview", selectedMovie.getOverview());
                    intent.putExtra("releaseDate", selectedMovie.getRelease_date());
                    intent.putExtra("voteCount", selectedMovie.getVoteCount());
                    intent.putExtra("adult", selectedMovie.isAdult());

                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);


    }



    private void setupSwipeToRefresh() {
        // Lookup the swipe container view
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                movieAdapter.clear();
                if(url == urlNowPlaying) {
                    url = urlPopular;
                    setTitle(Html.fromHtml(getString(R.string.html_title_popular)));
                    Toast.makeText(getApplicationContext(), toastPopular, Toast.LENGTH_SHORT).show();
                } else {
                    url = urlNowPlaying;
                    setTitle(Html.fromHtml(getString(R.string.html_title_now_playing)));
                    Toast.makeText(getApplicationContext(), toastNowPlaying, Toast.LENGTH_SHORT).show();
                }
                getMovieList();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    private void getMovieList() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieAdapter.notifyDataSetChanged();
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
