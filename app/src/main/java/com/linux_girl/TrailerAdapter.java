package com.linux_girl.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Trailer Adapter
public class TrailerAdapter extends ArrayAdapter<Trailers> {

    public TrailerAdapter(Context context, ArrayList<Trailers> trailers) {
        super(context, 0, trailers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.trailer_item, parent, false);
        }

        Trailers currentTrailer = getItem(position);
        TextView textView = (TextView) listView.findViewById(R.id.trailer_text);
        String trailer_text = currentTrailer.getTrailerId() + " | ";
        trailer_text += currentTrailer.getTrailerName() + " | ";
        trailer_text += currentTrailer.getTrailerKey() + " | ";
        trailer_text += currentTrailer.getTrailerSite();
        textView.setText(trailer_text);

        return listView;
    }

}

