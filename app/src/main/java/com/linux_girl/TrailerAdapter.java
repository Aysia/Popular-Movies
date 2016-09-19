package com.linux_girl.popularmovies;

import android.content.Context;
import android.util.Log;
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

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.trailer_item, parent, false);
        }

        Trailers currentTrailer = getItem(position);

        TextView keyView = (TextView) convertView.findViewById(R.id.trailer_key);
        keyView.setText(currentTrailer.mKey);

        TextView nameView = (TextView) convertView.findViewById(R.id.trailer_name);
        nameView.setText(currentTrailer.mName);

        return convertView;
    }
}

