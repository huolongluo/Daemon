# Daemon
## Android 保活组件

## Be careful:
双进程守护策略，为了避免出现一些莫名其妙的问题，请确保在Application的onCreate方法中书写的启动保活服务代码，是在主进程当中执行！！！

## 用法
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```
dependencies {
	      implementation 'com.github.huolongluo:Daemon:v1.0'
}
```
## 在application中启动保活服务
```
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
```
