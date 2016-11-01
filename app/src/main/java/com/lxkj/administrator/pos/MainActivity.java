package com.lxkj.administrator.pos;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lxkj.administrator.pos.bean.DrugButtonBean;
import com.lxkj.administrator.pos.bean.SystemBean;
import com.lxkj.administrator.pos.service.DrugButtonBeanService;
import com.lxkj.administrator.pos.service.SystemBeanService;
import com.lxkj.administrator.pos.utils.AppUtils;
import com.lxkj.administrator.pos.utils.MySqliteHelper;
import com.lxkj.administrator.pos.utils.ParameterManager;

public class MainActivity extends AppCompatActivity {
    private MySqliteHelper mySqliteHelper;
    private SystemBeanService systemBeanService;
    private DrugButtonBeanService drugButtonBeanService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySqliteHelper = new MySqliteHelper(this, ParameterManager.DATABASENAME, null, 1);
        systemBeanService = new SystemBeanService(mySqliteHelper);
        drugButtonBeanService = new DrugButtonBeanService(mySqliteHelper);
        //SystemBean systemBean = new SystemBean("1", "0", "0", "01", "4", "120.24.86.194", "5009", "20151012", "15", "65", "1");
        //判断apk是否是第一次登录
        if (AppUtils.isFirstInstall()) {
            AppUtils.firstInstall();
            //初始化数据表,插入数据
            initTable();
        }
    }

    private void initTable() {
        SystemBean systemBean = new SystemBean(ParameterManager.SYSTEMBEAN_POSNUM_VALUE, ParameterManager.SYSTEMBEAN_ONECODE_VALUE,
                ParameterManager.SYSTEMBEAN_TWOCODE_VALUE, ParameterManager.SYSTEMBEAN_THREECODE_VALUE,
                ParameterManager.SYSTEMBEAN_ADDRESS_VALUE, ParameterManager.SYSTEMBEAN_IP_VALUE,
                ParameterManager.SYSTEMBEAN_PORT_VALUE, ParameterManager.SYSTEMBEAN_DATE_VALUE,
                ParameterManager.SYSTEMBEAN_Min_VALUE, ParameterManager.SYSTEMBEAN_Max_VALUE,
                ParameterManager.SYSTEMBEAN_AREACODE_VALUE);
        systemBeanService.insert(systemBean);

        DrugButtonBean drugButtonBean = new DrugButtonBean("1 键", "1", "藿香正气丸", "藿香正气丸", "内服", "1", "19", "25", "2013-11-11", "1.0", "1");
        ContentValues values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(values);

        drugButtonBean = new DrugButtonBean("2 键", "2", "藿香正气丸", "藿香正气丸", "内服", "1", "25", "25", "2013-11-11", "1.0", "2");
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(values);

        drugButtonBean = new DrugButtonBean("3 键", "3", "风油精", "风油精", "外用", "1", "25", "25", "2013-11-11", "1.0", "3");
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(values);

        drugButtonBean = new DrugButtonBean("4 键", "4", "清凉油", "清凉油", "外用", "1", "25", "25", "2013-11-11", "1.0", "0");
        values = drugButtonBeanService.putContentValues(drugButtonBean);
        drugButtonBeanService.insert(values);
    }
//    static {
//        System.loadLibrary("libhy_gpio_jni");
//        System.loadLibrary("libhy_uart_jni");
//    }
}
