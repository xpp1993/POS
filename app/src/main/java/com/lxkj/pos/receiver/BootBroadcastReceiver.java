package com.lxkj.pos.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.lxkj.pos.CommActivity;

/**
 * Created by Administrator on 2016/9/18.
 * Android手机在启动的过程中会触发一个Standard Broadcast Action，名字叫android.intent.action.BOOT_COMPLETED(记得只会触发一次呀),在这里我们可以通过构建一个广播接收者来接收这个这个action.
 * 第一步：首先创建一个广播接收者,重构其抽象方法 onReceive(Context context, Intent intent)，在其中启动你想要启动的Service或app。
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //启动服务包
        Intent i = new Intent(context, CommActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
