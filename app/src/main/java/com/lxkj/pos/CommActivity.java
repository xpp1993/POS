package com.lxkj.pos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.lxkj.pos.bean.DrugButtonBean;
import com.lxkj.pos.bean.IdCardBean;
import com.lxkj.pos.bean.LYGBean;
import com.lxkj.pos.bean.ReceiveBean;
import com.lxkj.pos.bean.SystemBean;
import com.lxkj.pos.comm.IDCardReadUtils;
import com.lxkj.pos.dao.IDCardListener;
import com.lxkj.pos.gpio.Platform;
import com.lxkj.pos.service.DrugButtonBeanService;
import com.lxkj.pos.service.LYGBeanService;
import com.lxkj.pos.service.SystemBeanService;
import com.lxkj.pos.utils.AppUtils;
import com.lxkj.pos.utils.CommonTools;
import com.lxkj.pos.utils.MySqliteHelper;
import com.lxkj.pos.utils.ParameterManager;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.Calendar;
import java.util.List;

public class CommActivity extends AppCompatActivity {
    private MySqliteHelper mySqliteHelper;
    private SystemBeanService systemBeanService;
    private DrugButtonBeanService drugButtonBeanService;
    private LYGBeanService lygBeanService;
    private Calendar calendar;
    ShimmerTextView tv;
    Shimmer shimmer;
    private static final String TAG = CommActivity.class.getSimpleName();
    private AlertDialog.Builder builder_identity;
    private boolean isHasIdInLYGBEAN = false;
    private boolean isHasIdInMLYGBEAN = false;
    private boolean isHasIdInBLACKBEAN = false;
    private ConnectivityManager mCM;
    private SystemBean systemBean;
    private List<DrugButtonBean> drugButtonBeans;

    static {
        System.loadLibrary("hy_uart_jni");
    }

    private IDCardReadUtils IDCardRead;

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
        // 判断apk是否是第一次登录
        if (AppUtils.isFirstInstall()) {
            AppUtils.firstInstall();
            // 初始化数据表,插入数据
            initTable();
            Log.e(TAG, "initTable");
        }
        // 1.读取system表、DrugButtonBean表、获取手机当前时间
        systemBean = systemBeanService.findAll(ParameterManager.TABLENAME_SYSTEMBEAN);
        drugButtonBeans = drugButtonBeanService.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, null, null);
        if (systemBean == null) {// 删除表中的数据，把备份表中的数据拷贝过来,这里用一个事务比较好，删除和插入是一个事务，要么都不发生，要么都发生
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
        // 2.判断LYGBean表中数据是否当前月份
        List<LYGBean> lygBeans = lygBeanService.query(ParameterManager.TABLENAME_LYGBEAN, null, null, null);
        isJugde(lygBeans, ParameterManager.TABLENAME_LYGBEAN, String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        // 3.判断MLYGBean表中数据是否当前月份
        lygBeans = lygBeanService.query(ParameterManager.TABLENAME_MLYGBEAN, null, null, null);// LYGBean，MLYGBean,BlackBean,表结构一样，共用一个lygBeanServic和
        // lygBean
        isJugde(lygBeans, ParameterManager.TABLENAME_MLYGBEAN, String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        // 4.判断BlackBean表中数据是否当前年份
        lygBeans = lygBeanService.query(ParameterManager.TABLENAME_BLACKBEAN, null, null, null);// LYGBean，MLYGBean,BlackBean,表结构一样，共用一个lygBeanServic和
        // lygBean
        isJugde(lygBeans, ParameterManager.TABLENAME_BLACKBEAN, String.valueOf(calendar.get(Calendar.DAY_OF_YEAR)));
        // 5.显示：请刷身份证领取药具
        showIdDialog();
    }

    /**
     * 判断 LYGBean表中数据是否当前月份 判断MLYGBean表中数据是否当前月份 判断BlackBean表中数据是否当前年份
     *
     * @param lygBeans
     * @param tableName
     * @param args
     */
    private void isJugde(List<LYGBean> lygBeans, String tableName, String args) {
        String[] dateStr;
        if (lygBeans != null) {
            for (LYGBean lygBean : lygBeans) {
                dateStr = lygBean.getDate().split("-");
                if (!dateStr[1].equals(args)) {// 如果不是当前月份，清空LYBean表中数据
                    // lygBeanService.delect(ParameterManager.TABLENAME_LYGBEAN,
                    // null, null);
                    lygBeanService.delect(tableName, "DATE = ?",
                            new String[]{dateStr[1]});
                }
            }
        }
    }

    /**
     * 弹出对话框,提示请刷身份证领取药具
     */
    private void showIdDialog() {
        builder_identity = new AlertDialog.Builder(CommActivity.this);
        builder_identity.setTitle("提示")
                .setMessage(getResources().getString(R.string.Id_show))
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (shimmer != null && shimmer.isAnimating()) {
                            shimmer.cancel();
                        } else {
                            shimmer = new Shimmer();
                            shimmer.start(tv);
                        }
                       // 刷身份证
                        open();
                    }
                }).create().show();
    }

    /**
     * 刷身份证
     */
    private void pos(IdCardBean idCardBean) {
        // 7.打开串口，等待获取身份证信息，获取到信息后，关闭串口
        // 8.根据身份证号和当前手机时间判断年龄是否15至65之间
        int age = CommonTools.getAgeforIdCard(idCardBean.getIdCard());
        Log.e(TAG, age + "岁");
        if (age < 15 || age > 65) {// 显示你不符合领取条件
            showDialog(getResources().getString(R.string.show1));
        } else {
            ifTrueAge(idCardBean);
        }
    }

    /**
     * 如果年龄在15到65之间
     */
    private void ifTrueAge(IdCardBean idCardBean) {
        // 9 判断身份证号是否在LYGBean中
        String id = idCardBean.getIdCard();
        List<LYGBean> lygBeans = lygBeanService.query(
                ParameterManager.TABLENAME_LYGBEAN, null, null, null);
        for (LYGBean lygBean : lygBeans) {
            if (id.equals(lygBean.getID())) {// 10 判断身份证号是否在MLYGBean中
                isHasIdInLYGBEAN = true;
                lygBeans = lygBeanService.query(
                        ParameterManager.TABLENAME_MLYGBEAN, null, null, null);
                for (LYGBean lygBean1 : lygBeans) {
                    if (id.equals(lygBean1.getID())) {// 11 判断身份证号是否在BLACKBean中
                        isHasIdInMLYGBEAN = true;
                        lygBeans = lygBeanService.query(
                                ParameterManager.TABLENAME_BLACKBEAN, null,
                                null, null);
                        for (LYGBean lygBean2 : lygBeans) {
                            if (id.equals(lygBean2.getID())) {
                                isHasIdInBLACKBEAN = true;
                                //接下来在这里写代码
                                Platform.SetGpioOutput(1);
                                Platform.SetGpioDataLow(1);
                            }
                        }
                        trueOrfalse(isHasIdInBLACKBEAN, getResources()
                                .getString(R.string.show4));
                    }
                }
                trueOrfalse(isHasIdInMLYGBEAN,
                        getResources().getString(R.string.show3));
            }
        }
        trueOrfalse(isHasIdInLYGBEAN, getResources().getString(R.string.show2));
        // 暂时上传数据到服务器
        DrugButtonBean drugButtonBean = CommonTools.getDrugButtonBeanforBtn(drugButtonBeanService, "1");
        CommonTools.insertLYGBean(lygBeanService, ParameterManager.TABLENAME_LYGBEAN, idCardBean.getIdCard(), "2016-07-01 09:39:45", "1", String.valueOf(drugButtonBean.getPKID()));
        ReceiveBean receiveBean = CommonTools.ReceiveBean(drugButtonBeanService, idCardBean.getIdCard(), "1", drugButtonBean.getDRUGNAME(), drugButtonBean.getDRUGSTYLE(),
                "2016-07-01 09:39:45", systemBean.getPOSNUM(), systemBean.getONECODE(), systemBean.getTWOCODE(), systemBean.getTHREECODE(),
                "1.0", systemBean.getAREACODE(), idCardBean.getName(), idCardBean.getGender(), idCardBean.getNation(), idCardBean.getBirthday(), idCardBean.getStartopTime(), idCardBean.getAddress());
        // 打开GPRS
        CommonTools.gprsEnabled(true, mCM);
        // 通过WebService接口上传ReceivBean中的所有数据。上传成功删除ReceivBean中已上传的数据
        CommonTools.uploadData(receiveBean);
    }

    /**
     * 是否在表中,如果不在表中弹出对话框
     */
    private void trueOrfalse(boolean trueOrfalse, String showWhat) {
        if (!trueOrfalse) {
            showDialog(showWhat);
        } else {
            trueOrfalse = false;
        }
    }

    private void showDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CommActivity.this);
        builder.setMessage(str)
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //显示请刷身份证领取药具
                        open();

                    }
                }).setCancelable(false).create().show();
    }

    /**
     * 初始化表格数据
     */
    private void initTable() {
        // 初始化系统设置表
        CommonTools.initSystemBean(systemBeanService);

        CommonTools.insertDrugButtonBean(drugButtonBeanService, ParameterManager.TABLENAME_DRUGBUTTONBEAN, "1 键", "1", "藿香正气丸", "藿香正气丸", "内服", "1", "19", "25", "2013-11-11", "1.0", "1");
        CommonTools.insertDrugButtonBean(drugButtonBeanService, ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, "1 键", "1", "藿香正气丸", "藿香正气丸", "内服", "1", "19", "25", "2013-11-11", "1.0", "1");
        CommonTools.insertDrugButtonBean(drugButtonBeanService, ParameterManager.TABLENAME_DRUGBUTTONBEAN, "2 键", "2", "藿香正气丸", "藿香正气丸", "内服", "1", "25", "25", "2013-11-11", "1.0", "2");
        CommonTools.insertDrugButtonBean(drugButtonBeanService, ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, "2 键", "2", "藿香正气丸", "藿香正气丸", "内服", "1", "25", "25", "2013-11-11", "1.0", "2");
        CommonTools.insertDrugButtonBean(drugButtonBeanService, ParameterManager.TABLENAME_DRUGBUTTONBEAN, "3 键", "3", "风油精", "风油精", "外用", "1", "25", "25", "2013-11-11", "1.0", "3");
        CommonTools.insertDrugButtonBean(drugButtonBeanService, ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, "3 键", "3", "风油精", "风油精", "外用", "1", "25", "25", "2013-11-11", "1.0", "3");
        CommonTools.insertDrugButtonBean(drugButtonBeanService, ParameterManager.TABLENAME_DRUGBUTTONBEAN, "4 键", "4", "清凉油", "清凉油", "外用", "1", "25", "25", "2013-11-11", "1.0", "0");
        CommonTools.insertDrugButtonBean(drugButtonBeanService, ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE, "4 键", "4", "清凉油", "清凉油", "外用", "1", "25", "25", "2013-11-11", "1.0", "0");

        CommonTools.insertLYGBean(lygBeanService, ParameterManager.TABLENAME_LYGBEAN, "362302197507140016", "2016-03-01", "1", "0");
        CommonTools.insertLYGBean(lygBeanService, ParameterManager.TABLENAME_LYGBEAN, "36068119950108901X", "2016-07-28", "1", "0");
        CommonTools.insertLYGBean(lygBeanService, ParameterManager.TABLENAME_LYGBEAN, "360121199304201427", "2016-11-21", "1", "0");
    }

    /**
     * 6.监听按键6,按6把DrugButtonBean中的Max的值复制到该条记录的CURRENTAMO中，屏幕显示：恢复最大数量成功！
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_6 || keyCode == event.KEYCODE_NUMPAD_6) {
            List<DrugButtonBean> drugButtonBeans = drugButtonBeanService
                    .query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null,
                            null, null);
            for (DrugButtonBean drugButtonBean : drugButtonBeans) {
                drugButtonBeanService
                        .updata(ParameterManager.TABLENAME_DRUGBUTTONBEAN,
                                "CURRENTAMO", drugButtonBean.getMAXAMOUNT(),
                                null, null);
            }
            showDialog(getResources().getString(R.string.max_show));

        }
        return super.onKeyUp(keyCode, event);
    }

    private void open() {
        if (IDCardRead == null) {
            IDCardRead = new IDCardReadUtils(CommActivity.this, "/dev/ttyMT0", 19200, new IDCardListener() {
                @Override
                public void onInfo(IdCardBean bean) {
                    Log.i(TAG, "获取身份文本信息成功，姓名：" + bean.getName());
                    Log.i(TAG, "获取身份文本信息成功，身份证号：" + bean.getIdCard());
                    showDialog("姓名：" + bean.getName() +
                            "\n性别：" + bean.getGender() +
                            "\n民族：" + bean.getNation() +
                            "\n出生日期：" + bean.getBirthday() +
                            "\n住址：" + bean.getAddress() +
                            "\n身份证号：" + bean.getIdCard() +
                            "\n签发机关：" + bean.getIssuingAuthority() +
                            "\n有效期限：" + bean.getStartTime() + "-" + bean.getStartopTime()
                    );
                    pos(bean);
                }

                @Override
                public void unInfo(String str) {
                }
            });
        }
        IDCardRead.readCheckState();
    }
}
