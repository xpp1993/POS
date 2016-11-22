package com.lxkj.administrator.pos;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.lxkj.administrator.pos.bean.DrugButtonBean;
import com.lxkj.administrator.pos.bean.LYGBean;
import com.lxkj.administrator.pos.bean.ReceiveBean;
import com.lxkj.administrator.pos.bean.SystemBean;
import com.lxkj.administrator.pos.service.DrugButtonBeanService;
import com.lxkj.administrator.pos.service.LYGBeanService;
import com.lxkj.administrator.pos.service.SystemBeanService;
import com.lxkj.administrator.pos.utils.AppUtils;
import com.lxkj.administrator.pos.utils.CommonTools;
import com.lxkj.administrator.pos.utils.MyAsyncTask;
import com.lxkj.administrator.pos.utils.MySqliteHelper;
import com.lxkj.administrator.pos.utils.ParameterManager;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MySqliteHelper mySqliteHelper;
    private SystemBeanService systemBeanService;
    private DrugButtonBeanService drugButtonBeanService;
    private LYGBeanService lygBeanService;
    private Calendar calendar;
    ShimmerTextView tv;
    Shimmer shimmer;
    private static final String TAG = MainActivity.class.getSimpleName();
    private AlertDialog.Builder builder_identity;
    private boolean isHasIdInLYGBEAN = false;
    private boolean isHasIdInMLYGBEAN = false;
    private boolean isHasIdInBLACKBEAN = false;
    private ConnectivityManager mCM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySqliteHelper = new MySqliteHelper(this, ParameterManager.DATABASENAME, null, 1);
        systemBeanService = new SystemBeanService(mySqliteHelper);
        drugButtonBeanService = new DrugButtonBeanService(mySqliteHelper);
        lygBeanService = new LYGBeanService(mySqliteHelper);
        calendar = Calendar.getInstance();
        tv = (ShimmerTextView) findViewById(R.id.shimmer_tv);
        mCM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //判断apk是否是第一次登录
        if (AppUtils.isFirstInstall()) {
            AppUtils.firstInstall();
            //初始化数据表,插入数据
            initTable();
            Log.e(TAG, "initTable");
        }
        //1.读取system表、DrugButtonBean表、获取手机当前时间
        SystemBean systemBean = systemBeanService.findAll(ParameterManager.TABLENAME_SYSTEMBEAN);
        List<DrugButtonBean> drugButtonBeans = drugButtonBeanService.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, null, null);
        if (systemBean == null) {//删除表中的数据，把备份表中的数据拷贝过来,这里用一个事务比较好，删除和插入是一个事务，要么都不发生，要么都发生
            systemBeanService.delect(ParameterManager.TABLENAME_SYSTEMBEAN, null, null);
            systemBean = systemBeanService.findAll(ParameterManager.TABLENAME_SYSTEMBEAN_DUPLICATEFILE);
            systemBeanService.insert(ParameterManager.TABLENAME_SYSTEMBEAN, systemBean);
        }
        if (drugButtonBeans == null) {
            drugButtonBeanService.delect(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, null);
            drugButtonBeans = drugButtonBeanService.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, null, null, null);
            for (DrugButtonBean drugButtonBean : drugButtonBeans) {
                drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN, drugButtonBeanService.putContentValues(drugButtonBean));
            }
        }
        //  if (systemBean != null && drugButtonBean != null) {
        //2.判断LYGBean表中数据是否当前月份
        String[] dateStr;
        List<LYGBean> lygBeans = lygBeanService.query(ParameterManager.TABLENAME_LYGBEAN, null, null, null);
        if (lygBeans != null) {
            for (LYGBean lygBean : lygBeans) {
                dateStr = lygBean.getDate().split("-");
                if (!dateStr[1].equals(calendar.get(Calendar.DAY_OF_MONTH))) {//如果不是当前月份，清空LYBean表中数据
                    // lygBeanService.delect(ParameterManager.TABLENAME_LYGBEAN, null, null);
                    lygBeanService.delect(ParameterManager.TABLENAME_LYGBEAN, "DATE = ?", new String[]{dateStr[1]});
                }
            }
        }
        //3.判断MLYGBean表中数据是否当前月份
        lygBeans = lygBeanService.query(ParameterManager.TABLENAME_MLYGBEAN, null, null, null);//LYGBean，MLYGBean,BlackBean,表结构一样，共用一个lygBeanServic和 lygBean
        if (lygBeans != null) {
            for (LYGBean lygBean : lygBeans) {
                dateStr = lygBean.getDate().split("-");
                if (!dateStr[1].equals(calendar.get(Calendar.DAY_OF_MONTH))) {//如果不是当前月份，清空MLYBean表中数据
//                    lygBeanService.delect(ParameterManager.TABLENAME_MLYGBEAN, null, null);
                    lygBeanService.delect(ParameterManager.TABLENAME_MLYGBEAN, "DATE = ?", new String[]{dateStr[1]});
                }
            }
        }
        //4.判断BlackBean表中数据是否当前月份
        lygBeans = lygBeanService.query(ParameterManager.TABLENAME_BLACKBEAN, null, null, null);//LYGBean，MLYGBean,BlackBean,表结构一样，共用一个lygBeanServic和 lygBean
        if (lygBeans != null) {
            for (LYGBean lygBean : lygBeans) {
                dateStr = lygBean.getDate().split("-");
                if (!dateStr[1].equals(calendar.get(Calendar.DAY_OF_YEAR))) {//如果不是当前年份，清空BlackBean表中数据
//                    lygBeanService.delect(ParameterManager.TABLENAME_BLACKBEAN, null, null);
                    lygBeanService.delect(ParameterManager.TABLENAME_BLACKBEAN, "DATE = ?", new String[]{dateStr[1]});
                }
            }
        }
        //5.显示：请刷身份证领取药具
        showIdDialog();

        // }
    }

    /**
     * 弹出对话框,提示请刷身份证领取药具
     */
    private void showIdDialog() {
        builder_identity = new AlertDialog.Builder(MainActivity.this);
        builder_identity.setTitle("提示").
                setMessage(getResources().getString(R.string.Id_show))
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (shimmer != null && shimmer.isAnimating()) {
                            shimmer.cancel();
                        } else {
                            shimmer = new Shimmer();
                            shimmer.start(tv);
                        }
                        /**
                         * 刷身份证
                         */
                        pos("360121199304201427");

                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 刷身份证
     */
    private void pos(String id) {
        //6.监听按键6,按6把DrugButtonBean中的Max的值复制到该条记录的CURRENTAMO中，屏幕显示：恢复最大数量成功！
//        key_6.setOnClickListener(MainActivity.this);
        //7.打开串口，等待获取身份证信息，获取到信息后，关闭串口
        //8.根据身份证号和当前手机时间判断年龄是否15至65之间
        //String id = "360121199304201427";
        int age = MainActivity.this.getAgeforIdCard(id);
        Log.e(TAG, age + "岁");
        if (age < 15 || age > 65) {//显示你不符合领取条件
            showDialog(getResources().getString(R.string.show1));
        } else {
            ifTrueAge(id);
        }

    }

    /**
     * 如果年龄在15到65之间
     *
     * @param id 身份证号
     */
    private void ifTrueAge(String id) {
        //9 判断身份证号是否在LYGBean中
        List<LYGBean> lygBeans = lygBeanService.query(ParameterManager.TABLENAME_LYGBEAN, null, null, null);
        for (LYGBean lygBean : lygBeans) {
            if (id.equals(lygBean.getID())) {// 10 判断身份证号是否在MLYGBean中
                isHasIdInLYGBEAN = true;
                lygBeans = lygBeanService.query(ParameterManager.TABLENAME_MLYGBEAN, null, null, null);
                for (LYGBean lygBean1 : lygBeans) {
                    if (id.equals(lygBean1.getID())) {//11 判断身份证号是否在BLACKBean中
                        isHasIdInMLYGBEAN = true;
                        lygBeans = lygBeanService.query(ParameterManager.TABLENAME_BLACKBEAN, null, null, null);
                        for (LYGBean lygBean2 : lygBeans) {
                            if (id.equals(lygBean2.getID())) {
                                isHasIdInBLACKBEAN = true;

                            }
                        }
                        if (!isHasIdInBLACKBEAN) {
                            showDialog(getResources().getString(R.string.show4));
                        } else {
                            isHasIdInBLACKBEAN = false;
                        }
                    }
                }
                if (!isHasIdInMLYGBEAN) {
                    showDialog(getResources().getString(R.string.show3));
                } else {
                    isHasIdInMLYGBEAN = false;
                }
            }
        }
        if (!isHasIdInLYGBEAN) {
            showDialog(getResources().getString(R.string.show2));
        } else {
            isHasIdInLYGBEAN = false;
        }
        //暂时上传数据到服务器
        MainActivity.this.ReceiveBean("440302197507140016", "1", "风油精", "外用", "2016-07-01 09:39:45", "1", "1", "0", "01", "1", "1", "MIKE", "nan", "han", "19750714", "20320419", "广州市越秀区惠福东路535号");
    }

    private void showDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(str)
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        builder_identity.show();//显示请刷身份证领取药具
                        // pos(" ");
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 初始化表格数据
     */
    private void initTable() {
        //初始化系统设置表
        SystemBean systemBean = new SystemBean(ParameterManager.SYSTEMBEAN_POSNUM_VALUE, ParameterManager.SYSTEMBEAN_ONECODE_VALUE,
                ParameterManager.SYSTEMBEAN_TWOCODE_VALUE, ParameterManager.SYSTEMBEAN_THREECODE_VALUE,
                ParameterManager.SYSTEMBEAN_ADDRESS_VALUE, ParameterManager.SYSTEMBEAN_IP_VALUE,
                ParameterManager.SYSTEMBEAN_PORT_VALUE, ParameterManager.SYSTEMBEAN_DATE_VALUE,
                ParameterManager.SYSTEMBEAN_Min_VALUE, ParameterManager.SYSTEMBEAN_Max_VALUE,
                ParameterManager.SYSTEMBEAN_AREACODE_VALUE);
        systemBeanService.insert(ParameterManager.TABLENAME_SYSTEMBEAN, systemBean);
        systemBeanService.insert(ParameterManager.TABLENAME_SYSTEMBEAN_DUPLICATEFILE, systemBean);

        DrugButtonBean drugButtonBean = new DrugButtonBean("1 键", "1", "藿香正气丸", "藿香正气丸", "内服", "1", "19", "25", "2013-11-11", "1.0", "1");
        ContentValues values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN, values);
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, values);

        drugButtonBean = new DrugButtonBean("2 键", "2", "藿香正气丸", "藿香正气丸", "内服", "1", "25", "25", "2013-11-11", "1.0", "2");
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN, values);
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, values);

        drugButtonBean = new DrugButtonBean("3 键", "3", "风油精", "风油精", "外用", "1", "25", "25", "2013-11-11", "1.0", "3");
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN, values);
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, values);

        drugButtonBean = new DrugButtonBean("4 键", "4", "清凉油", "清凉油", "外用", "1", "25", "25", "2013-11-11", "1.0", "0");
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN, values);
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, values);

        insertLYGBean("362302197507140016", "2016-03-01", "1", "0");
        insertLYGBean("36068119950108901X", "2016-07-28", "1", "0");
        insertLYGBean("360121199304201427", "2016-11-21", "1", "0");
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_6 || keyCode == event.KEYCODE_NUMPAD_6) {
            List<DrugButtonBean> drugButtonBeans = drugButtonBeanService.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, null, null);
            for (DrugButtonBean drugButtonBean : drugButtonBeans) {
                drugButtonBeanService.updata(ParameterManager.TABLENAME_DRUGBUTTONBEAN, "CURRENTAMO", drugButtonBean.getMAXAMOUNT(), null, null);
            }
            showDialog(getResources().getString(R.string.max_show));

        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 根据身份证计算年龄
     */
    private int getAgeforIdCard(String idcard) {
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
     * 把DrugButtonBean中的该通道Max的值置为0,该条记录的CURRENTAMO的值置为0
     *
     * @param BUTTONVALU 键值 对应通道
     */
    private void changeDrugButtonBeanMAX(String BUTTONVALU) {
        drugButtonBeanService.updata(ParameterManager.TABLENAME_DRUGBUTTONBEAN, "CURRENTAMO", "0", "BUTTONVALU = ?", new String[]{BUTTONVALU});
        drugButtonBeanService.updata(ParameterManager.TABLENAME_DRUGBUTTONBEAN, "MAXAMOUNT", "0", "BUTTONVALU = ?", new String[]{BUTTONVALU});
    }

    /**
     * 写LYGBean
     */
    private void insertLYGBean(String ID, String date, String flag, String List) {
        LYGBean lygBean = new LYGBean(ID, date, flag, List);
        ContentValues values = lygBeanService.putContentValues(lygBean);
        lygBeanService.insert(ParameterManager.TABLENAME_LYGBEAN, values);
    }

    /**
     * 写ReceiveBean
     */
    public void ReceiveBean(String IDENTITYNU, String AMOUNT, String CODING, String STYLE, String TIME, String POSNUM,
                            String ONECODE, String TWOCODE, String THREECODE, String PRICE, String AREACODE,
                            String USERNAME, String USERSEX, String USERNATION, String BORNDATE, String PAPERWORKD, String ADDRESS) {
        ReceiveBean receiveBean = new ReceiveBean(IDENTITYNU, AMOUNT, CODING, STYLE, TIME, POSNUM, ONECODE, TWOCODE, THREECODE, PRICE, AREACODE, USERNAME, USERSEX, USERNATION, BORNDATE, PAPERWORKD, ADDRESS);
        ContentValues values = drugButtonBeanService.putContentValuesforReceive(receiveBean);
        drugButtonBeanService.insert(ParameterManager.TABLENAME_RECEIVEBEAN, values);
        //打开GPRS
        CommonTools.gprsEnabled(true, mCM);
        //通过WebService接口上传ReceivBean中的所有数据。上传成功删除ReceivBean中已上传的数据
        uploadData(receiveBean);
    }

    /**
     * 通过WebService接口上传ReceivBean中的所有数据。上传成功删除ReceivBean中已上传的数据
     */
    public void uploadData(ReceiveBean receiveBean) {
        String[] keys = new String[]{"Key", "IDENTITYNU", "AMOUNT", "CODING", "STYLE", "TIME", "POSNUM", "ONECODE", "TWOCODE", "THREECODE", "PRICE", "AREACODE",
                "USERNAME", "USERSEX", "USERNATION", "BORNDATE", "PAPERWORKD", "ADDRESS"};
        Map<String, Object> requestParamsMap = CommonTools.getParameterMap(keys, ParameterManager.KEY, receiveBean.getIDENTITYNU(), receiveBean.getAMOUNT(), receiveBean.getCODING(),
                receiveBean.getSTYLE(), receiveBean.getTIME(), receiveBean.getPOSNUM(), receiveBean.getONECODE(), receiveBean.getTWOCODE(), receiveBean.getTHREECODE(), receiveBean.getPRICE(),
                receiveBean.getAREACODE(), receiveBean.getUSERNAM(), receiveBean.getUSERSEX(), receiveBean.getUSERNATION(), receiveBean.getBORNDATE(), receiveBean.getPAPERWORKD(), receiveBean.getADDRESS());
        new MyAsyncTask(requestParamsMap).execute();
    }

}
