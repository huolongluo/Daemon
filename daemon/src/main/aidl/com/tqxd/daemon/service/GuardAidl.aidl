// GuardAidl.aidl
package com.tqxd.daemon.service;

interface GuardAidl {
    //相互唤醒服务
    void wakeUp(String title, String discription, int iconRes);
}