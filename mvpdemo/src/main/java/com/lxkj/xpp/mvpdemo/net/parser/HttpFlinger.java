package com.lxkj.xpp.mvpdemo.net.parser;

import android.os.AsyncTask;

import com.lxkj.xpp.mvpdemo.listener.DataListener;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/2.
 */

public final class HttpFlinger {
    private static final DefaultParser DEFAULT_PARSER = new DefaultParser();

    private HttpFlinger() {

    }

    public static void get(String reqUrl, DataListener<String> listener) {
        get(reqUrl,DEFAULT_PARSER,listener);
    }

    /**
     * 发送get请求
     *
     * @param reqUrl
     * @param parser
     * @param listener
     * @param <T>
     */
    public static <T> void get(final String reqUrl, final RespParser<T> parser, final DataListener<T> listener) {
        new AsyncTask<Void, Void, T>() {
            @Override
            protected T doInBackground(Void... params) {
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) new URL(reqUrl).openConnection();
                    urlConnection.connect();
                    String result = streamToString(urlConnection.getInputStream());
                    return parser.parseResponse(result);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
                //当请求失败时数据会返回空，导致很多地方都需要判空，如何优化这一步呢？
                return null;
            }

            @Override
            protected void onPostExecute(T t) {
                if (listener!=null){
                    listener.onComplete(t);
                }
            }
        }.execute();
    }

    private static String streamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }
}
