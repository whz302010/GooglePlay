package com.example.acer_pc.googleplay.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.example.acer_pc.googleplay.global.GooglePlayApplication;

/**
 * Created by acer-pc on 2017/6/2.
 */

public class UIUtils {
    public static Context getContext(){
        return GooglePlayApplication.getContext();
    }
    public static Handler getHandler(){
        return GooglePlayApplication.getHandler();
    }
    public static int getMainTheadId(){
        return GooglePlayApplication.getMainTheadId();
    }
    public static String getSting(int id){
        return  getContext().getResources().getString(id);
    }
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }
    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }
    //获取颜色
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }
    //获取尺寸,直接返回像素大小
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);
    }
    public static int dip2px(float dip){
        //像素密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density*dip +0.5f);
    }
    public static int px2dip(int px){
        //像素密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density);
    }
    //加载布局文件
    public static View inflate(int id){
      return   View.inflate(getContext(),id,null);
    }

    //是否运行在主线程
    public static boolean isRunningUiThread(){
      int myThreadid=android.os.Process.myTid();
        if (myThreadid!=getMainTheadId()){
            return false;
        }
        return true;
    }


    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getContext().getResources().getColorStateList(mTabTextColorResId);
    }
}
