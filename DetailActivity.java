package com.linux_girl.popularmovies;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //display DetailFragment
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putParcelable(MainFragment.MOVIE_EXTRA, getIntent().getData());
            Log.i(LOG_TAG, "LOCATION: " + arguments.toString());

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_main_container, fragment)
                    .commit();
        }

    }
}