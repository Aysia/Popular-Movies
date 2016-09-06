package com.linux_girl.popularmovies;


import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
    private static final int DETAIL_LOADER = 0;

    private TextView mTitleView;
    private ImageView mImageView;
    private TextView mPlotView;
    private TextView mDateView;
    private TextView mRatingView;
    private MovieObject object;

    final static String KEY_POSITION = "position";
    int mCurrentPosition = -1;

    public DetailFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {
            Bundle arguments = getArguments();

            if (arguments != null) {
                // Update Details
                int position = arguments.getInt("POSITION");
                updateDetails(position);
            } else if (mCurrentPosition != -1) {
                // Set description based on savedInstanceState defined during onCreateView()
                updateDetails(mCurrentPosition);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null){
            mCurrentPosition = savedInstanceState.getInt(KEY_POSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mTitleView = (TextView) rootView.findViewById(R.id.movie_title);
        mImageView = (ImageView) rootView.findViewById(R.id.imageView);
        mPlotView = (TextView) rootView.findViewById(R.id.movie_plot);
        mDateView = (TextView) rootView.findViewById(R.id.release_date);
        mRatingView = (TextView) rootView.findViewById(R.id.user_ratings);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void updateDetails(int position) {

        Movies currentMovie = MainFragment.adapter.getItem(position);
        object = MainFragment.getMovieObject(currentMovie);

        mTitleView.setText(object.movieTitle);

        String imageUri = "http://image.tmdb.org/t/p/w185/" + object.imageUrl;
        Picasso.with(getContext())
                .load(imageUri)
                .into(mImageView);

        mPlotView.setText(object.moviePlot);
        mDateView.setText(object.releaseDate);
        mRatingView.setText(object.userRating);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current description selection in case we need to recreate the fragment
        outState.putInt(KEY_POSITION,mCurrentPosition);
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

