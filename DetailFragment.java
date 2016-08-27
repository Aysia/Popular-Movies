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
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    String LOG_TAG = DetailFragment.class.getSimpleName();
    ArrayList<Movies> movie;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        /** Get the intent from MainFragment */
        Intent intent = getActivity().getIntent();
        MovieObject object = intent.getParcelableExtra(MainFragment.MOVIE_EXTRA);

        TextView titleView = (TextView) rootView.findViewById(R.id.movie_title);
        titleView.setText(object.movieTitle);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        String imageUri = "http://image.tmdb.org/t/p/w185/" + object.imageUrl;
        Picasso.with(getContext())
                .load(imageUri)
                .into(imageView);

        TextView plotView = (TextView) rootView.findViewById(R.id.movie_plot);
        plotView.setText(object.moviePlot);

        TextView dateView = (TextView) rootView.findViewById(R.id.release_date);
        dateView.setText(object.releaseDate);

        TextView ratingView = (TextView) rootView.findViewById(R.id.user_ratings);
        ratingView.setText(object.userRating);

        return rootView;
    }
}

