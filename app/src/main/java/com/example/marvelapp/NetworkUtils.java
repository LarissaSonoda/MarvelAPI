package com.example.marvelapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String MARVEL_URL = "gateway.marvel.com";
    private static final String API_KEY = "196673e041f9190f73350df2499c7e13";
    private static final String MAX_RESULTS = "maxResults";
    private static final String TYPE_PRINT = "printType";

    static String searchCharacters(String queryString){
        String marvelJSONString = null;

        try{
            marvelJSONString = searchCharacter(queryString);
            if(marvelJSONString == null){
                return null;
            }

            JSONObject marvelJSONObject = new JSONObject(marvelJSONString);

            String name = null;
            String thumbnail = null;
            String series = null;
            String comics = null;


            try{
                name = marvelJSONObject.getString("name");
                thumbnail = marvelJSONObject.getString("thumbnail");
                series = marvelJSONObject.getString("series");
                comics = marvelJSONObject.getString("comics");

            } catch (JSONException e){
                e.printStackTrace();
            }

            JSONObject productJSONObject = new JSONObject();
            try{
                productJSONObject.put("name", name);
                productJSONObject.put("thumbnail", thumbnail);
                productJSONObject.put("series", series);
                productJSONObject.put("comics", comics);

                marvelJSONString = marvelJSONObject.toString();
            } catch (JSONException e){
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, marvelJSONString);
        return marvelJSONString;
    }

    static String searchCharacter(String nameStartWith){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String marvelJSONString = null;

        try{
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("https")
                    .authority( MARVEL_URL + ":443" )
                    //.authority(":443")
                    .appendPath("v1")
                    .appendPath("public")
                    .appendPath("characters")
                    .appendQueryParameter("nameStartsWith", nameStartWith)
                    .appendQueryParameter("apikey", API_KEY)
                    .build();
            URL requestURL = new URL(uriBuilder.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            marvelJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, marvelJSONString);
        return marvelJSONString;
    }
}