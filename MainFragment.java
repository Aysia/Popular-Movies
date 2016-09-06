package com.linux_girl.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private final String LOG_TAG = MainFragment.class.getSimpleName();
    private static MovieAdapter adapter;
    public static String MOVIE_EXTRA = "";
    static MovieObject obj = new MovieObject();
    public static ArrayList<Movies> movie;

    private AdapterView.OnItemClickListener listener;

    public MainFragment() {
    }

    public static void setMovieAdapter(ArrayList<Movies> movie) {
        adapter.clear();
        adapter.addAll(movie);
        adapter.notifyDataSetChanged();
    }

/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        updateMovies();
    }*/

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

                Movies currentMovie = adapter.getItem(position);

                if (MainActivity.mTwoPane) {
                    //obj = getMovieObject(currentMovie);

                    DetailFragment df = new DetailFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt(MOVIE_EXTRA, position);
                    df.setArguments(bundle);

                } else {
                    //Go through DetailActivity
                    Intent i = new Intent(getActivity(), DetailActivity.class);
                    obj = getMovieObject(currentMovie);
                    i.putExtra(MOVIE_EXTRA, obj);
                    startActivity(i);
                }
            }
        });
        return rootView;
    }

    public void onItemClick(AdapterView a, View v, int position, long id) {
        OnMovieSelectedListener listener = (OnMovieSelectedListener) getActivity();
        listener.OnSelectionChanged(position);
    }

    /**
     * Method for when a menu item has been selected
     *
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


    private void updateMovies() {
        GetMovieTask getMovie = new GetMovieTask();
        movie = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));
        getMovie.execute(sortOrder);
    }

    /**
     * Method to create a parcelable object (@link MovieObject)
     *
     * @return a MovieObject
     */
    public static MovieObject getMovieObject(Movies currentMovie) {

        obj.movieId = currentMovie.getMovieId();
        obj.movieTitle = currentMovie.getMovieTitle();
        obj.moviePlot = currentMovie.getMoviePlot();
        obj.userRating = currentMovie.getUserRating();
        obj.releaseDate = currentMovie.getReleaseDate();
        obj.imageUrl = currentMovie.getImageUrl();

        return obj;
    }

}
