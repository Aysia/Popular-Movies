package com.linux_girl.popularmovies;

/**
 * Created by Lani on 9/12/2016.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class QueryUtils {
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */

    //ArrayList<Trailers> trailers;

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<Trailers> extractTrailers(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        ArrayList<Trailers> trailers = new ArrayList<>();

        try {
            jsonResponse = makeHttpRequest(url);
            trailers = extractFeatureFromJson(jsonResponse);

        } catch (IOException e) {
            Log.e("QueryUtils", "Problem parsing the trailers JSON results", e);
        }
        return trailers;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Trailers} object by parsing out information
     *  from the input trailersJSON string.
     */
    private static ArrayList<Trailers> extractFeatureFromJson(String trailersJSON) {

        String id;
        String key;
        String name;
        String site;
        final String BASE_RESULTS = "results";
        final String TRAILER_ID = "id";
        final String TRAILER_KEY = "key";
        final String TRAILER_SITE = "name";
        final String TRAILER_NAME = "site";

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(trailersJSON)) {
            return null;
        }

        try {
            JSONObject jsonRootObject = new JSONObject(trailersJSON);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.getJSONArray(BASE_RESULTS);

            ArrayList<Trailers> trailers = new ArrayList<>();

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){

                // Get the JSONObjects representing a trailer
                JSONObject theObject = jsonArray.getJSONObject(i);
                id = theObject.getString(TRAILER_ID);
                name = theObject.getString(TRAILER_NAME);
                key = theObject.getString(TRAILER_KEY);
                site = theObject.getString(TRAILER_SITE);

                trailers.add(new Trailers(id, name, key, site));
            }
            return trailers;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the trailers JSON results", e);
        }
        return null;
    }
}