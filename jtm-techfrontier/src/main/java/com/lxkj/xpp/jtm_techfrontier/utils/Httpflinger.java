package com.lxkj.xpp.jtm_techfrontier.utils;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/30.
 */

public final class Httpflinger {
    private static final DefaultParser DEFAULT_PARSER = new DefaultParser();

    private Httpflinger() {

    }

    public static void get(String url, DataListener<String> listener) {
        get(url, DEFAULT_PARSER, listener);
    }

    public static <T> void get(final String url, final RespParser<T> parser, final DataListener<T> listener) {
        new AsyncTask<Void, Void, T>() {
            @Override
            protected T doInBackground(Void... params) {
                HttpURLConnection urlConnection=null;
                try {
                  urlConnection= (HttpURLConnection) new URL(url).openConnection();
                    urlConnection.connect();
                    String result = streamToString(urlConnection.getInputStream());
                    return parser.parseResponse(result);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                return null;
            }
            @Override
            protected void onPostExecute(T result) {
                if (listener != null) {
                    listener.onComplete(result);
                }
            }
        }.execute();
    }
    private static String streamToString(InputStream inputStream) throws IOException {
        StringBuilder sBuilder = new StringBuilder();
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                inputStream));
        while ((line = bufferedReader.readLine()) != null) {
            sBuilder.append(line).append("\n");
        }
        return sBuilder.toString();
    }
}
