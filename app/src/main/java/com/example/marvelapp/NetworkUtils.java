package com.example.marvelapp;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String API_KEY = "196673e041f9190f73350df2499c7e13";
    private static final String API_PRIVATE = "4f50d4aaee9cb0c429e85a33250604a13ba7a1df";

    static String searchCharacter(String nameStartsWith) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String MarvelJSONString = null;

        try {
            String ts = String.valueOf (System.currentTimeMillis ());
            String input  = ts+ API_PRIVATE+API_KEY;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes(StandardCharsets.UTF_8));
            byte[] hashBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            String  hash = sb.toString();
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("https")
                    .encodedAuthority("gateway.marvel.com:443")
                    .appendPath("v1")
                    .appendPath("public")
                    .appendPath("characters")
                    .appendQueryParameter("nameStartsWith", nameStartsWith)
                    .appendQueryParameter("apikey", API_KEY)
                    .appendQueryParameter("ts", ts)
                    .appendQueryParameter("hash",hash)
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

                MarvelJSONString = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch (IOException | NoSuchAlgorithmException e) {
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

        Log.d(LOG_TAG, MarvelJSONString);
        return MarvelJSONString;
    }


    //LIGAÇÃO COM NOSSA API - NÃO FUNCIONAL AINDA
    static String GetAll(){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String CharactersJSONString = null;

        try {
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("https")
                    .encodedAuthority("localhost:44344")
                    .appendPath("api")
                    .appendPath("Personagens")
                    .appendPath("getAll")
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

                CharactersJSONString = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
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

        Log.d(LOG_TAG, CharactersJSONString);
        return CharactersJSONString;
    }
}