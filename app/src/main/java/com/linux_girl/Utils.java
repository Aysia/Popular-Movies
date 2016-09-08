package com.linux_girl.popularmovies;

/**
 * Created by Lani on 9/6/2016.
 */
public class Utils {

    /**
     * Method to create a parcelable object (@link MovieObject)
     * @return a MovieObject
     */
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

   // private static MovieHelper dbHelper = new MovieHelper(context);



}
