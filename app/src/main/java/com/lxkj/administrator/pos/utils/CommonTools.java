package com.lxkj.administrator.pos.utils;

import android.content.ContentValues;
import android.net.ConnectivityManager;
import android.util.Log;

import com.lxkj.administrator.pos.bean.DrugButtonBean;
import com.lxkj.administrator.pos.bean.LYGBean;
import com.lxkj.administrator.pos.bean.ReceiveBean;
import com.lxkj.administrator.pos.bean.SystemBean;
import com.lxkj.administrator.pos.service.DrugButtonBeanService;
import com.lxkj.administrator.pos.service.LYGBeanService;
import com.lxkj.administrator.pos.service.SystemBeanService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
     * 插入数据到表DrugButtonBean
     */
    public static void insertDrugButtonBean(DrugButtonBeanService drugButtonBeanService, String tableName, String BUTTONNAME, String BUTTONVALU, String DRUGCODING, String DRUGNAME,
                                            String DRUGSTYLE, String USESTATUS, String CURRENTAMO, String MAXAMOUNT,
                                            String VALIDDATE, String BATCH, String ROOTJIAO) {
        DrugButtonBean drugButtonBean = new DrugButtonBean(BUTTONNAME, BUTTONVALU, DRUGCODING, DRUGNAME, DRUGSTYLE, USESTATUS, CURRENTAMO,
                MAXAMOUNT, VALIDDATE, BATCH, ROOTJIAO);
        ContentValues values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(tableName, values);
    }

    /**
     * 写LYGBean
     */
    public static void insertLYGBean(LYGBeanService lygBeanService, String tableName, String ID, String date, String flag, String List) {
        LYGBean lygBean = new LYGBean(ID, date, flag, List);
        ContentValues values = lygBeanService.putContentValues(lygBean);
        lygBeanService.insert(tableName, values);
    }

    /**
     * 根据身份证计算年龄
     */
    public static int getAgeforIdCard(String idcard) {
        // 获取出生日期
        String birthday = idcard.substring(6, 14);
        Date birthdate = null;
        int age = 0;
        try {
            birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
            GregorianCalendar currentDay = new GregorianCalendar();
            currentDay.setTime(birthdate);
            //获取年龄
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            String year = simpleDateFormat.format(new Date());
            age = Integer.parseInt(year) - currentDay.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return age;
    }

    /**
     * 初始化系统设置表
     */
    public static void initSystemBean(SystemBeanService systemBeanService) {
        SystemBean systemBean = new SystemBean(ParameterManager.SYSTEMBEAN_POSNUM_VALUE, ParameterManager.SYSTEMBEAN_ONECODE_VALUE,
                ParameterManager.SYSTEMBEAN_TWOCODE_VALUE, ParameterManager.SYSTEMBEAN_THREECODE_VALUE,
                ParameterManager.SYSTEMBEAN_ADDRESS_VALUE, ParameterManager.SYSTEMBEAN_IP_VALUE,
                ParameterManager.SYSTEMBEAN_PORT_VALUE, ParameterManager.SYSTEMBEAN_DATE_VALUE,
                ParameterManager.SYSTEMBEAN_Min_VALUE, ParameterManager.SYSTEMBEAN_Max_VALUE,
                ParameterManager.SYSTEMBEAN_AREACODE_VALUE);
        systemBeanService.insert(ParameterManager.TABLENAME_SYSTEMBEAN, systemBean);
        systemBeanService.insert(ParameterManager.TABLENAME_SYSTEMBEAN_DUPLICATEFILE, systemBean);
    }

    /**
     * 写ReceiveBean
     */
    public static ReceiveBean ReceiveBean(DrugButtonBeanService drugButtonBeanService, String IDENTITYNU, String AMOUNT, String CODING, String STYLE, String TIME, String POSNUM,
                                          String ONECODE, String TWOCODE, String THREECODE, String PRICE, String AREACODE,
                                          String USERNAME, String USERSEX, String USERNATION, String BORNDATE, String PAPERWORKD, String ADDRESS) {
        ReceiveBean receiveBean = new ReceiveBean(IDENTITYNU, AMOUNT, CODING, STYLE, TIME, POSNUM, ONECODE, TWOCODE, THREECODE, PRICE, AREACODE, USERNAME, USERSEX, USERNATION, BORNDATE, PAPERWORKD, ADDRESS);
        ContentValues values = drugButtonBeanService.putContentValuesforReceive(receiveBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_RECEIVEBEAN, values);
        return receiveBean;
    }

    /**
     * 把DrugButtonBean中的该通道Max的值置为0,该条记录的CURRENTAMO的值置为0
     *
     * @param BUTTONVALU 键值 对应通道
     */
    private void changeDrugButtonBeanMAX(DrugButtonBeanService drugButtonBeanService, String BUTTONVALU) {
        drugButtonBeanService.updata(ParameterManager.TABLENAME_DRUGBUTTONBEAN, "CURRENTAMO", "0", "BUTTONVALU = ?", new String[]{BUTTONVALU});
        drugButtonBeanService.updata(ParameterManager.TABLENAME_DRUGBUTTONBEAN, "MAXAMOUNT", "0", "BUTTONVALU = ?", new String[]{BUTTONVALU});
    }

    /**
     * 通过WebService接口上传ReceivBean中的所有数据。上传成功删除ReceivBean中已上传的数据
     */
    public static void uploadData(ReceiveBean receiveBean) {
        String[] keys = new String[]{"Key", "IDENTITYNU", "AMOUNT", "CODING", "STYLE", "TIME", "POSNUM", "ONECODE", "TWOCODE", "THREECODE", "PRICE", "AREACODE",
                "USERNAME", "USERSEX", "USERNATION", "BORNDATE", "PAPERWORKD", "ADDRESS"};
        Map<String, Object> requestParamsMap = CommonTools.getParameterMap(keys, ParameterManager.KEY, receiveBean.getIDENTITYNU(), receiveBean.getAMOUNT(), receiveBean.getCODING(),
                receiveBean.getSTYLE(), receiveBean.getTIME(), receiveBean.getPOSNUM(), receiveBean.getONECODE(), receiveBean.getTWOCODE(), receiveBean.getTHREECODE(), receiveBean.getPRICE(),
                receiveBean.getAREACODE(), receiveBean.getUSERNAM(), receiveBean.getUSERSEX(), receiveBean.getUSERNATION(), receiveBean.getBORNDATE(), receiveBean.getPAPERWORKD(), receiveBean.getADDRESS());
        new MyAsyncTask(requestParamsMap).execute();
    }

    /**
     * POST请求获取数据
     */
    public static String postDown(String requestUrl, Map<String, Object> requestParamsMap) {
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
                    return sb.toString();
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
        return null;
    }
}
