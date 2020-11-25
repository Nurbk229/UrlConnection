package com.nurbk.ps.urlconnection.network;

import android.net.Uri;

import com.nurbk.ps.urlconnection.Utils.AppExecutor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static NetworkUtils instance = null;

    private static final String URL_BASE = "http://api.geonames.org";
    private static final String EARTH_PATH = "/weatherJSON";
    private static final String NORTH = "north";
    private static final String SOUTH = "south";
    private static final String EAST = "east";
    private static final String WEST = "west";
    private static final String USERNAME = "username";
    private static final String VALUE_USER = "omaralbelbaisy";

    private static final String GET_METHOD = "GET";


    private NetworkUtils() {
    }

    public static NetworkUtils getInstance() {
        if (instance == null)
            instance = new NetworkUtils();
        return instance;
    }

    public void getData(OnResponseListener onResponseListener) {
        Uri uri = Uri.parse(URL_BASE + EARTH_PATH).buildUpon()
                .appendQueryParameter(NORTH, "44.1")
                .appendQueryParameter(SOUTH, "-9.9")
                .appendQueryParameter(WEST, "55.2")
                .appendQueryParameter(EAST, "-22.4")
                .appendQueryParameter(USERNAME, VALUE_USER)
                .build();
        loadDataFormWeb(uri.toString(), onResponseListener);
    }


    private void loadDataFormWeb(String url, OnResponseListener onResponseListener) {
        AppExecutor.getInstance().getNetworkExecutor().execute(() -> {
            try {
                URL uri = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
                httpURLConnection.setRequestMethod(GET_METHOD);
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                AppExecutor.getInstance().getMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        onResponseListener.onResponse(builder.toString());
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                AppExecutor.getInstance().getMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        onResponseListener.onError(e.getMessage());
                    }
                });

            }
        });

    }


}
