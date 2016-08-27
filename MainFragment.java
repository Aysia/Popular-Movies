package com.linux_girl.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private final String LOG_TAG = MainFragment.class.getSimpleName();
    private static MovieAdapter adapter;
    private ArrayList<Movies> movie;
    public static String MOVIE_EXTRA = "";
    MovieObject obj = new MovieObject();

    public static void setMovieAdapter(ArrayList<Movies> movie) {
        adapter.clear();
        adapter.addAll(movie);
        adapter.notifyDataSetChanged();
    }

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for this fragment to handle menu events
        setHasOptionsMenu(true);
        updateMovies();
    }

    /**
     * Method for when a menu item has been selected
     * @param item is the menu item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(getContext(), SettingsActivity.class), 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            updateMovies();
        }
    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        updateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        adapter = new MovieAdapter(getActivity(), new ArrayList<Movies>());

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.maingrid);
        gridView.setAdapter(adapter);

        // Creating the intent to launch detailed view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Movies m = adapter.getItem(position);

                Intent i = new Intent(getActivity(), DetailActivity.class);

                //Parcelable finally works!
                //Set values to Parcelable
                obj.movieId = m.getMovieId();
                obj.movieTitle = m.getMovieTitle();
                obj.moviePlot = m.getMoviePlot();
                obj.userRating = m.getUserRating();
                obj.releaseDate = m.getReleaseDate();
                obj.imageUrl = m.getImageUrl();

                i.putExtra(MOVIE_EXTRA, obj);
                startActivity(i);
            }
        });
        return rootView;
    }

    private void updateMovies(){
        GetMovieTask getMovie = new GetMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));
        getMovie.execute(sortOrder);
    }
}
