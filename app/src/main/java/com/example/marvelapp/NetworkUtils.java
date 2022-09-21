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

            //Busca
            marvelJSONString = searchCharacter(queryString);
            JSONArray characterJSONArray = new JSONArray(marvelJSONString);
            // Procurando pelos itens
            try {
                if (characterJSONArray.length() < 1){
                    return null;
                }
                JSONObject characterJSONObject = characterJSONArray.getJSONObject(0);

                name= characterJSONObject.getString("name");
                thumbnail= characterJSONObject.getString("thumbnail");
                series = characterJSONObject.getString("series");
                comics = characterJSONObject.getString("comics");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Criando JSON com os dados
            JSONObject InfoJSONObject = new JSONObject();
            try {
                InfoJSONObject.put("name", name);
                InfoJSONObject.put("thumbnail", thumbnail);
                InfoJSONObject.put("series", series);
                InfoJSONObject.put("comics", comics);

                marvelJSONString = InfoJSONObject.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, marvelJSONString);
        return marvelJSONString;
    }

    static String searchCharacter(String nameStartsWith) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String marvelJSONString = null;

        try {
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("https")
                    .encodedAuthority("gateway.marvel.com:443")
                    //.authority(":443")
                    .appendPath("v1")
                    .appendPath("public")
                    .appendPath("characters")
                    .appendQueryParameter("nameStartsWith", nameStartsWith)
                    .appendQueryParameter("apikey", API_KEY)
                    .build();
            URL requestURL = new URL(uriBuilder.toString());
            try {
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
            }

        }catch (IOException e) {
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