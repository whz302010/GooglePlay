package com.example.acer_pc.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by acer-pc on 2017/6/2.
 */

public class GooglePlayApplication extends Application {

    private static Context context;
    private  static Handler handler;
    private static int mainTheadId;

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainTheadId() {
        return mainTheadId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainTheadId = android.os.Process.myTid();
    }

}
