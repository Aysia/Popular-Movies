package com.linux_girl.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public boolean mTwoPane;
    public String DETAILFRAGMENT_TAG = "DFTAG";
    public String TRAILERFRAGMENT_TAG = "TFTAG";


    // newest file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Check whether the Activity is using the layout verison with the fragment_container
        // FrameLayout and if so we must add the first fragment

        if (findViewById(R.id.movie_info) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                // replace detail_container in activity_main with DetailFragment and set it's
                // tag to DFTAG
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();

                // replace trailer_container in activity_main with TrailerFragment and set it's tag
                // to TFTAG
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.trailer_container, new TrailerFragment(),TRAILERFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

        MainFragment mainFragment =  ((MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_main));
        mainFragment.updateMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemSelected(MovieObject object) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(MainFragment.MOVIE_EXTRA, object);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();

            // In two-pane mode, show the trailer view in this activity by
            // adding or replacing the trailer fragment using a
            // fragment transaction
            Bundle arguments = new Bundle();
            arguments.putString(TrailerFragment.MOVIE_ID,object.movieId);

            TrailerFragment trailerFragment = new TrailerFragment();
            trailerFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.trailer_container, trailerFragment, TRAILERFRAGMENT_TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(MainFragment.MOVIE_EXTRA, object);
            startActivity(intent);

            Intent i = new Intent(this, TrailerActivity.class);
            i.putExtra(TrailerFragment.MOVIE_ID, object.movieId);
            startActivity(i);

        }
    }

    // Check if there is internet connectivity
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    //create a movie object
    public static MovieObject getMovieObject(Movies currentMovie) {
        MovieObject obj = new MovieObject();
        obj.movieId = currentMovie.getMovieId();
        obj.movieTitle = currentMovie.getMovieTitle();
        obj.moviePlot = currentMovie.getMoviePlot();
        obj.userRating = currentMovie.getUserRating();
        obj.releaseDate = currentMovie.getReleaseDate();
        obj.imageUrl = currentMovie.getImageUrl();

        return obj;
    }

}
