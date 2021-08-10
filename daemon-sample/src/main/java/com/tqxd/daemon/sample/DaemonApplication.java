package com.tqxd.daemon.sample;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tqxd.daemon.KeepLive;
import com.tqxd.daemon.config.ForegroundNotification;
import com.tqxd.daemon.config.ForegroundNotificationClickListener;
import com.tqxd.daemon.config.KeepLiveService;

public class DaemonApplication extends Application {
    private static final String TAG = "DaemonApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //定义前台服务的默认样式。即标题、描述和图标
        ForegroundNotification foregroundNotification = new ForegroundNotification("测试", "描述", R.mipmap.ic_launcher,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {

                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                        Log.i(TAG, "foregroundNotificationClick: You clicked the Notification!");
                    }
                });
        //启动保活服务
        KeepLive.startWork(this, KeepLive.RunMode.ROGUE, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {
                    /**
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     */
                    @Override
                    public void onWorking() {
                        Log.i(TAG, "onWorking: 运行中...");
                    }

                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {
                        Log.i(TAG, "onWorking: 服务终止");
                    }
                }
        );
    }
}
