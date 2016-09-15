package com.linux_girl.popularmovies;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerActivity extends AppCompatActivity {

    private final String LOG_TAG = TrailerActivity.class.getSimpleName();
    public String TRAILERFRAGMENT_TAG = "TFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        //display DetailFragment
        if (savedInstanceState == null) {

            Bundle args = new Bundle();
            args.putParcelable(TrailerFragment.MOVIE_EXTRA, getIntent().getParcelableExtra(MainFragment.MOVIE_EXTRA));

            TrailerFragment trailerFragment = new TrailerFragment();
            trailerFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.trailer_main_container, trailerFragment)
                    .commit();
        }
    }

    public TrailerAdapter setTrailerAdapter(ArrayList<Trailers> trailers) {
        TrailerAdapter trailerAdapter = new TrailerAdapter(this, trailers);
        return trailerAdapter;
    }


    public class TrailerObject implements Parcelable {
        String mId;
        String mName;
        String mKey;
        String mSite;

        public TrailerObject() {
        }

        public TrailerObject(String id, String name, String key, String site) {
            this.mId = id;
            this.mName = name;
            this.mKey = key;
            this.mSite = site;
        }

        public TrailerObject(Parcel in) {
            mId = in.readString();
            mName = in.readString();
            mKey = in.readString();
            mSite = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(mId);
            parcel.writeString(mName);
            parcel.writeString(mKey);
            parcel.writeString(mSite);
        }

        public final Parcelable.Creator<TrailerObject> CREATOR
                = new Parcelable.Creator<TrailerObject>() {
            public TrailerObject createFromParcel(Parcel in) {
                return new TrailerObject(in);
            }

            public TrailerObject[] newArray(int size) {
                return new TrailerObject[size];
            }
        };
    }
}

