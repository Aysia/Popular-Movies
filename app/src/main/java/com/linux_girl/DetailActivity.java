package com.linux_girl.popularmovies;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.lang.reflect.Field;

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //display DetailFragment
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.MOVIE_EXTRA, getIntent().getParcelableExtra(MainFragment.MOVIE_EXTRA));

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, fragment)
                    .commit();

        }
        if(findViewById(R.id.trailer_fragment) != null) {
            Log.i(LOG_TAG, "Location: trailer fragment found");
        }

    }

    public void displayTrailerFragment(Activity activity, String movieId) {
        TrailerFragment trailerFragment = new TrailerFragment();

        Bundle args = new Bundle();
        args.putString(TrailerFragment.MOVIE_EXTRA, movieId);
        trailerFragment.setArguments(args);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}