package com.linux_girl.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TrailerFragment extends Fragment {

    // Tag for Logs
    String LOG_TAG = TrailerFragment.class.getSimpleName();

    final static String KEY_POSITION = "position";
    int mCurrentPosition = -1;
    static String MOVIE_EXTRA = "";
    TrailerActivity activity = new TrailerActivity();
    ListView listView;
    View rootView;
    JSONParse jsonParse = new JSONParse();

    ArrayList<Trailers> trailers;
    TrailerAdapter trailerAdapter;

    public TrailerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trailer, container, false);

        Bundle arguments = getArguments();


        if (arguments != null) {
            String mMovieId = arguments.getString(MainFragment.MOVIE_EXTRA);
            String uri = "http://api.themoviedb.org/3/movie/" + mMovieId + "/videos?";

            jsonParse.execute(mMovieId);



        }

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(KEY_POSITION);
        }

        trailerAdapter = new TrailerAdapter(getContext(), new ArrayList<Trailers>());
        listView = (ListView) rootView.findViewById(R.id.trailer_view);

        return rootView;
    }

    public void setTrailers(ArrayList<Trailers> trailers) {
        for(Trailers trailer : trailers) {
            Log.i(LOG_TAG, "TRAILERS" + trailer);
        }

        trailerAdapter.addAll(trailers);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current description selection in case we need to recreate the fragment
        outState.putInt(KEY_POSITION, mCurrentPosition);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
