package com.rubabuddin.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubabuddin.flickster.R;
import com.rubabuddin.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rubab.uddin on 10/8/2016.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for each position (utilizing row view recycler in android ~7 items displayed per view
        Movie movie = getItem(position);
        ViewHolder viewHolder; //view lookup cache stored in tag
        //check if the existing view is getting reused
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false); //dont attach to adapter, done elsewhere
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivImage.setImageResource(0);
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath()).placeholder(R.drawable.progress_animation).into(viewHolder.ivImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).placeholder(R.drawable.progress_animation).into(viewHolder.ivImage);
        }
        //clear out image from convertView

        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());



        //return the view
        return convertView;
    }
}
