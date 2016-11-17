package com.lxkj.administrator.pos;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.lxkj.administrator.pos.bean.DrugButtonBean;
import com.lxkj.administrator.pos.bean.LYGBean;
import com.lxkj.administrator.pos.bean.SystemBean;
import com.lxkj.administrator.pos.service.DrugButtonBeanService;
import com.lxkj.administrator.pos.service.LYGBeanService;
import com.lxkj.administrator.pos.service.SystemBeanService;
import com.lxkj.administrator.pos.utils.AppUtils;
import com.lxkj.administrator.pos.utils.MySqliteHelper;
import com.lxkj.administrator.pos.utils.ParameterManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MySqliteHelper mySqliteHelper;
    private SystemBeanService systemBeanService;
    private DrugButtonBeanService drugButtonBeanService;
    private LYGBeanService lygBeanService;
    private Calendar calendar;
    private Button key_6;
    private static final String TAG = MainActivity.class.getSimpleName();
    private AlertDialog.Builder builder_identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySqliteHelper = new MySqliteHelper(this, ParameterManager.DATABASENAME, null, 1);
        systemBeanService = new SystemBeanService(mySqliteHelper);
        drugButtonBeanService = new DrugButtonBeanService(mySqliteHelper);
        lygBeanService = new LYGBeanService(mySqliteHelper);
        calendar = Calendar.getInstance();
        key_6 = (Button) findViewById(R.id.key_6);
        //判断apk是否是第一次登录
        if (AppUtils.isFirstInstall()) {
            AppUtils.firstInstall();
            //初始化数据表,插入数据
            initTable();
            Log.e(TAG, "initTable");
        }
        //1.读取system表、DrugButtonBean表、获取手机当前时间
        SystemBean systemBean = systemBeanService.findAll(ParameterManager.TABLENAME_SYSTEMBEAN);
        DrugButtonBean drugButtonBean = drugButtonBeanService.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, null, null);
        if (systemBean == null) {//删除表中的数据，把备份表中的数据拷贝过来,这里用一个事务比较好，删除和插入是一个事务，要么都不发生，要么都发生
            systemBeanService.delect(ParameterManager.TABLENAME_SYSTEMBEAN, null, null);
            systemBean = systemBeanService.findAll(ParameterManager.TABLENAME_SYSTEMBEAN_DUPLICATEFILE);
            systemBeanService.insert(ParameterManager.TABLENAME_SYSTEMBEAN, systemBean);
        }
        if (drugButtonBean == null) {
            drugButtonBeanService.delect(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, null);
            drugButtonBean = drugButtonBeanService.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, null, null, null);
            drugButtonBeanService.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN, drugButtonBeanService.putContentValues(drugButtonBean));
        }
        //  if (systemBean != null && drugButtonBean != null) {
        //2.判断LYGBean表中数据是否当前月份
        String[] dateStr;
        LYGBean lygBean = lygBeanService.query(ParameterManager.TABLENAME_LYGBEAN, null, null, null);
        if (lygBean != null) {
            dateStr = lygBean.getDate().split("-");
            if (!dateStr[1].equals(calendar.get(Calendar.DAY_OF_MONTH))) {//如果不是当前月份，清空LYBean表中数据
                lygBeanService.delect(ParameterManager.TABLENAME_LYGBEAN, null, null);
            }
        }
        //3.判断MLYGBean表中数据是否当前月份
        lygBean = lygBeanService.query(ParameterManager.TABLENAME_MLYGBEAN, null, null, null);//LYGBean，MLYGBean,BlackBean,表结构一样，共用一个lygBeanServic和 lygBean
        if (lygBean != null) {
            dateStr = lygBean.getDate().split("-");
            if (!dateStr[1].equals(calendar.get(Calendar.DAY_OF_MONTH))) {//如果不是当前月份，清空MLYBean表中数据
                lygBeanService.delect(ParameterManager.TABLENAME_MLYGBEAN, null, null);
            }
        }
        //4.判断BlackBean表中数据是否当前月份
        lygBean = lygBeanService.query(ParameterManager.TABLENAME_BLACKBEAN, null, null, null);//LYGBean，MLYGBean,BlackBean,表结构一样，共用一个lygBeanServic和 lygBean
        if (lygBean != null) {
            dateStr = lygBean.getDate().split("-");
            if (!dateStr[1].equals(calendar.get(Calendar.DAY_OF_YEAR))) {//如果不是当前年份，清空BlackBean表中数据
                lygBeanService.delect(ParameterManager.TABLENAME_BLACKBEAN, null, null);
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
                        //6.监听按键6,按6把DrugButtonBean中的Max的值复制到该条记录的CURRENTAMO中，屏幕显示：恢复最大数量成功！
                        key_6.setOnClickListener(MainActivity.this);
                        //7.打开串口，等待获取身份证信息，获取到信息后，关闭串口
                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 初始化表格数据
     */
    private void initTable() {
        //SystemBean systemBean = new SystemBean("1", "0", "0", "01", "4", "120.24.86.194", "5009", "20151012", "15", "65", "1");
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

        LYGBean lygBean = new LYGBean("362302197507140016", "2016-03-01", "1", "0");
        values = lygBeanService.putContentValues(lygBean);
        lygBeanService.insert(ParameterManager.TABLENAME_LYGBEAN, values);

        lygBean = new LYGBean("36068119950108901X", "2016-07-28", "1", "0");
        values = lygBeanService.putContentValues(lygBean);
        lygBeanService.insert(ParameterManager.TABLENAME_LYGBEAN, values);
    }

    //按了按键6之后
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.key_6://6把DrugButtonBean中的Max的值复制到该条记录的CURRENTAMO中，屏幕显示：恢复最大数量成功！
                DrugButtonBean drugButtonBean = drugButtonBeanService.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, null, null);
                drugButtonBeanService.updata(ParameterManager.TABLENAME_DRUGBUTTONBEAN, "MAXAMOUNT", drugButtonBean.getMAXAMOUNT(), null, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(getResources().getString(R.string.max_show))
                        .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder_identity.show();//显示请刷身份证领取药具
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_6 || keyCode == event.KEYCODE_NUMPAD_6) {
            DrugButtonBean drugButtonBean = drugButtonBeanService.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, null, null);
            drugButtonBeanService.updata(ParameterManager.TABLENAME_DRUGBUTTONBEAN, "MAXAMOUNT", drugButtonBean.getMAXAMOUNT(), null, null);
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(getResources().getString(R.string.max_show))
                    .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.show();//显示请刷身份证领取药具
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();

        }
        return super.onKeyUp(keyCode, event);
    }
    //    static {
//        System.loadLibrary("libhy_gpio_jni");
//        System.loadLibrary("libhy_uart_jni");
//    }
}
