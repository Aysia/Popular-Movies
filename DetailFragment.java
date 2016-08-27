package com.linux_girl.popularmovies;


import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    /**
     * Tag for Logs
     */
    String LOG_TAG = DetailFragment.class.getSimpleName();

    /** initialize @link Movies */
    ArrayList<Movies> movie;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        /** Get the intent from MainFragment */
        Intent intent = getActivity().getIntent();
        /**
         * Get Parcelable Extra from the intent and assign to MovieObject class
         */
        MovieObject object = intent.getParcelableExtra(MainFragment.MOVIE_EXTRA);

        /**
         * Display the Details of the movie in the UI
         */
        TextView titleView = (TextView) rootView.findViewById(R.id.movie_title);
        titleView.setText(object.movieTitle);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        /**
         * Create the url from the poster_path (imageUrl) Picasso will do the rest
         */
        String imageUri = "http://image.tmdb.org/t/p/w185/" + object.imageUrl;
        Picasso.with(getContext())
                .load(imageUri)
                .into(imageView);

        TextView plotView = (TextView) rootView.findViewById(R.id.movie_plot);
        plotView.setText(object.moviePlot);

        TextView dateView = (TextView) rootView.findViewById(R.id.release_date);
        /**
         * get an easy to read Date Format
         */
        dateView.setText(simpleDate(object.releaseDate));

        TextView ratingView = (TextView) rootView.findViewById(R.id.user_ratings);
        ratingView.setText(object.userRating);

        return rootView;
    }

    private String simpleDate(String dateStr) {
        String dStr = dateStr + " 00:00:00.0";
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        SimpleDateFormat dt1 = new SimpleDateFormat("MMM d, yyyy");
        try {
            Date date = dt.parse(dStr);
            return dt1.format(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}

