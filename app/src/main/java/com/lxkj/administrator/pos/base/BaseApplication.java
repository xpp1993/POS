package com.lxkj.administrator.pos.base;

import android.app.Application;
import android.content.Context;

import com.lxkj.administrator.pos.utils.CommonTools;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2016/11/1.
 */
public class BaseApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        /**
         * 如果App使用了多进程且各个进程都会初始化Bugly（例如在Application类onCreate()中初始化Bugly），
         * 那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费。
         因此，为了节省流量、内存等资源，建议初始化的时候对上报进程进行控制，只在主进程下上报数据：
         判断是否是主进程（通过进程名是否为包名来判断），并在初始化Bugly时增加一个上报进程的策略配置。
         */
        //获取当前报名
        String packageName=context.getPackageName();
        //获取当前进程名
        String processName= CommonTools.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        /**
         * 第三个参数为sdk调试的模式开关调试模式的行为特性如下：
         输出详细的Bugly SDK的Log；
         每一条Crash都会被立即上报；
         自定义日志将会在Logcat中输出。
         建议在测试阶段建议设置成true，发布时设置为false。
         */
        CrashReport.initCrashReport(getApplicationContext(), "900060242", true,strategy);
    }
}
