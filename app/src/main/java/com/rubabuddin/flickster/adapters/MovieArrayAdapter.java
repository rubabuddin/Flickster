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

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by rubab.uddin on 10/8/2016.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public enum LayoutTypes{
        movieRegular,
        moviePopular
    }

    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    private static class ViewHolderPopular {
        ImageView ivImagePopular;
        TextView tvTitle;
        TextView tvOverview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);

    }

    @Override
    public int getItemViewType(int position){
        Movie movie = getItem(position);
        int type;

        if(movie.getVoteAverage() > 5){
            type = LayoutTypes.moviePopular.ordinal();
        } else {
            type = LayoutTypes.movieRegular.ordinal();
        }
        return type;
    }

    @Override
    public int getViewTypeCount(){
        return LayoutTypes.values().length;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for each position (utilizing row view recycler in android ~7 items displayed per view
        Movie movie = getItem(position);

        ViewHolder viewHolder; //view lookup cache stored in tag
        ViewHolderPopular viewHolderPopular;

        viewHolder = new ViewHolder();
        viewHolderPopular = new ViewHolderPopular();

        int type = getItemViewType(position);

        //check if the existing view is getting reused
        if (convertView == null) {
            if (type == 0) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie, parent, false); //dont attach to adapter, done elsewhere
                viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            } else if (type == 1) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_movie_popular, parent, false); //dont attach to adapter, done elsewhere
                viewHolderPopular.ivImagePopular = (ImageView) convertView.findViewById(R.id.ivMovieImagePopular);
                viewHolderPopular.tvTitle = (TextView) convertView.findViewById(R.id.tvTitlePopular);
                viewHolderPopular.tvOverview = (TextView) convertView.findViewById(R.id.tvOverviewPopular);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolderPopular);
            }
        } else {
                if (type == 0) {
                    // View is being recycled, retrieve the viewHolder object from tag
                    viewHolder = (ViewHolder) convertView.getTag();
                } else if (type == 1) {
                    viewHolderPopular = (ViewHolderPopular) convertView.getTag();
                }
        }

        int orientation = getContext().getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if(type == 0) {
                viewHolder.tvTitle.setText(movie.getOriginalTitle());
                viewHolder.tvOverview.setText(movie.getOverview());

                Picasso.with(getContext())
                        .load(movie.getPosterPath())
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.progress_animation)
                        .transform(new RoundedCornersTransformation(10, 10))
                        .into(viewHolder.ivImage);
            } else if (type == 1) {
                Picasso.with(getContext())
                        .load(movie.getBackdropPath())
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.progress_animation)
                        .transform(new RoundedCornersTransformation(10, 10))
                        .into(viewHolderPopular.ivImagePopular);
            }
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(type == 0) {
                viewHolder.tvTitle.setText(movie.getOriginalTitle());
                viewHolder.tvOverview.setText(movie.getOverview());

                Picasso.with(getContext())
                        .load(movie.getPosterPath())
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.progress_animation)
                        .transform(new RoundedCornersTransformation(10,10))
                        .into(viewHolder.ivImage);
                viewHolder.ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
            } else if(type == 1) {
                viewHolderPopular.tvTitle.setText(movie.getOriginalTitle());
                viewHolderPopular.tvOverview.setText(movie.getOverview());
                    Picasso.with(getContext())
                        .load(movie.getBackdropPath())
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.progress_animation)
                        .transform(new RoundedCornersTransformation(10,10))
                        .into(viewHolderPopular.ivImagePopular);
                viewHolderPopular.ivImagePopular.setScaleType(ImageView.ScaleType.FIT_XY);
            }


    }
    return convertView;
    }
}
