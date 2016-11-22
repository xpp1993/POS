package com.lxkj.administrator.pos.utils;

import android.net.ConnectivityManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by Administrator on 2016/11/22.
 */
public class CommonTools {
    private static final String TAG = CommonTools.class.getSimpleName();

    /**
     * 构建参数map对象的工具方法
     *
     * @param keys
     * @param values
     * @return
     */
    public static Map<String, Object> getParameterMap(String keys[], String... values) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    /**
     * 打开或关闭GPRS
     */
    public static boolean gprsEnabled(boolean bEnable, ConnectivityManager mCM) {

        boolean isOpen = gprsIsOpenMethod("getMobileDataEnabled", mCM);
        if (isOpen == !bEnable) {
            setGprsEnabled("setMobileDataEnabled", bEnable, mCM);
        }

        return isOpen;
    }

    /**
     * 检测GPRS是否打开
     */
    public static boolean gprsIsOpenMethod(String methodName, ConnectivityManager mCM) {
        Class cmClass = mCM.getClass();
        Class[] argClasses = null;
        Object[] argObject = null;

        Boolean isOpen = false;
        try {
            Method method = cmClass.getMethod(methodName, argClasses);

            isOpen = (Boolean) method.invoke(mCM, argObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOpen;
    }

    /**
     * 开启/关闭GPRS
     */
    public static void setGprsEnabled(String methodName, boolean isEnable, ConnectivityManager mCM) {
        Class cmClass = mCM.getClass();
        Class[] argClasses = new Class[1];
        argClasses[0] = boolean.class;

        try {
            Method method = cmClass.getMethod(methodName, argClasses);
            method.invoke(mCM, isEnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * POST请求获取数据
     */
    public static boolean postDown(String requestUrl, Map<String, Object> requestParamsMap) {
        PrintWriter printWriter = null;
        StringBuffer params = new StringBuffer();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        // 组织请求参数
        Iterator it = requestParamsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            params.append(element.getKey());
            params.append("=");
            params.append(element.getValue());
            params.append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        URL realUrl = null;
        try {
            realUrl = new URL(requestUrl);
            // 打开和URL之间的连接
            httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //设置请求属性
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 获取URLConnection对象对应的输出流
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write(params.toString());
            // flush输出流的缓冲
            printWriter.flush();
            // 根据ResponseCode判断连接是否成功
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                Log.e(TAG, " Error===" + responseCode);
            } else if (responseCode == 200) {
                Log.e(TAG, "Post Success!");
                inputStream = httpURLConnection.getInputStream();
                if (inputStream != null) {
                    StringBuffer sb = new StringBuffer();
                    String readLine;
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        sb.append(readLine).append("\n");
                    }
                    responseReader.close();
                    System.out.println(sb.toString());
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "send post request error!" + e);
        } finally {
            httpURLConnection.disconnect();
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
