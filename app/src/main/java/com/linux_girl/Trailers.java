package com.linux_girl.popularmovies;

/**
 * Created by Lani on 9/8/2016.
 */
public class Trailers {

    public final String mId;
    public final String mName;
    public final String mKey;
    public final String mSite;

    /**
     * Construct a new @link Trailer
     */

    public Trailers(String id, String name, String key, String site) {
        mId = id;
        mName = name;
        mKey = key;
        mSite = site;
    }

    public String getTrailerId() {
        return mId;
    }
    public String getTrailerName() {
        return mName;
    }
    public String getTrailerKey() {
        return mKey;
    }
    public String getTrailerSite() { return mSite; }

}
