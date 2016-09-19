package com.linux_girl.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TrailerFragment extends Fragment implements TrailerTask.TaskListener {


//    public interface TaskListener {
//        public void onFinished(ArrayList<Trailers> trailers);
//    }

    ArrayList<Trailers> trailers;
    String LOG_TAG = TrailerFragment.class.getSimpleName();

    final static String KEY_POSITION = "position";
    int mCurrentPosition = -1;

    static String MOVIE_ID = "";

    View rootView;
    ListView listView;
    TrailerAdapter trailerAdapter;
    int currentTrailer;

    TrailerTask task = new TrailerTask(this);
    DetailActivity detailActivity = new DetailActivity();
    String mMovieId;

    public TrailerFragment() {
    }

    @Override
    public void onFinished(ArrayList<Trailers> trailers) {
        insertTrailers(trailers);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_trailer, container, false);
        listView = (ListView) rootView.findViewById(R.id.trailer_list_view);

        Bundle arguments = getArguments();

        if (arguments != null) {
            // Update Details

            mMovieId = arguments.getString(MOVIE_ID);
            task.execute(mMovieId);

        } else if (mCurrentPosition != -1) {
            // Set description based on savedInstanceState defined during onCreateView()
            task.execute(mMovieId);
        }

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(KEY_POSITION);
        }

        return rootView;
    }

    public void insertTrailers(ArrayList<Trailers> trailers) {

        /**
         * Create an {@link TrailerAdapter}, whose data source is a list of
         * {@link Trailers}. The adapter knows how to create list items for each
         * item in the list.
         */
        trailerAdapter = new TrailerAdapter(getContext(), trailers);

        /** Set the adapter on the {@link ListView}
         so the list can be populated in the user interface
         */
        listView.setAdapter(trailerAdapter);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current trailer selection in case we need to recreate the fragment
        outState.putInt(KEY_POSITION, mCurrentPosition);
    }
}
