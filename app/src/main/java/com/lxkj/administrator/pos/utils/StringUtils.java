package com.lxkj.administrator.pos.utils;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author levy
 * @creation 2015-6-18 ����5:27:20
 * @instruction �ַ�������
 */
public class StringUtils {

    /**
     * byte����תΪ��Ӧ��16�����ַ���
     * 
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * byte����תΪ��Ӧ��16�����ַ���
     * 
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || length <= 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * ʮ�����Ʊ����ַ���תΪ��Ӧ�Ķ���������
     * 
     * @param s
     * @return
     */
    public static byte[] hexStringToBytes(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {

                baKeyword[i] = (byte) (Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baKeyword;
    }

    /**
     * ʮ������תascii
     * 
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    /**
     * 10�����ַ��� תΪ16�����ַ���
     * @param dec
     * @return
     */
    public static String convertDecToHexString(String s) {
        Log.i("", s);
        String str = Integer.toHexString(Integer.parseInt(s));
        if(str.length()%2==1){
            str = "0"+str;
        }
        return str;
    }

    /**
     * ͨ�����������,���У����
     * @param cmd
     * @return
     */
    public static String xor(String cmd)
    {
        if(cmd.length()%2!=0){
            cmd = "0"+cmd;
        }
        int result = 0;
        for (int i = 0; i < cmd.length()-1; i=i+2) {
            //System.out.println(cmd.substring(i,i+2));
            result ^= Integer.valueOf(cmd.substring(i, i + 2), 16);
            System.out.println("16-->"+ Integer.valueOf(cmd.substring(i, i + 2), 16));
            System.out.println("result:"+result);
        }
        return Integer.toHexString(result);
    }

    /**
     * ��"-"����ַ���
     * @param str
     * @return
     */
    public static String[] splitString(String str){
        return str.split("-");
    }

    public static String takeCity(String str){
        String nstr = null;
        if(str!=null){
            nstr=str.substring(0, str.length()-1);
        }
        return nstr;
    }

    /**
     * ʱ���תΪ����
     * @param datestr
     * @return
     */
    public static String getSimpDate(String datestr){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String date = sdf.format(new Date(Long.parseLong(datestr) ));
        return date;
    }
    /**
     * ʱ���תΪ����
     * @param smdateint
     * @return
     */
    public static String getSimpDate(long smdateint){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String date = sdf.format(new Date(smdateint));
        System.out.println(date);
        return date;
    }
    /**
     * byte����תΪchar����
     * @return
     */
    public static char[] toChar(byte[] ucs2){
        int length=ucs2.length/2;
        char[] charArrary=new char[length];
        int charlength=0;

        for (int i=0;i<length;i++){
            charArrary[charlength++]=(char)((ucs2[i*2+1] & 0xFF )*256+(ucs2[i*2]& 0xFF ));
        }
        return charArrary;
    }
}