package com.linux_girl.popularmovies;

/**
 * Created by Lani on 8/25/2016.
 */
public class Movies {
    public final String movieId;
    public final String movieTitle;
    public final String moviePlot;
    public final String userRating;
    public final String releaseDate;
    public final String imageUrl;

    /**
     * Construct a new @link Movie
     */

    public Movies(String id, String title, String plot, String rating, String date, String image) {
        movieId = id;
        movieTitle = title;
        moviePlot = plot;
        userRating = rating;
        releaseDate = date;
        imageUrl = image;
    }

    public String getMovieId() {
        return movieId;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public String getMoviePlot() {
        return moviePlot;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
