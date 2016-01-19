package com.sunzxy.androiduitl.Util;

import android.util.Log;

/**
 * Created by zhengxiaoyong on 16/1/17.
 */
public class AppUnCaughtExceptionManager implements Thread.UncaughtExceptionHandler {

    private static final class Holder {
        private static final AppUnCaughtExceptionManager INSTANCE = new AppUnCaughtExceptionManager();
    }
    public static AppUnCaughtExceptionManager getInstance(){
        return Holder.INSTANCE;
    }
    public void init(){
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.d("zxy",thread.getName()+","+ex.getLocalizedMessage());
    }
}
