package com.example.acer_pc.googleplay.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

/**
 * Created by acer-pc on 2017/6/2.
 */

public class BaseActivity extends ActionBarActivity {

    private ArrayList<Activity> mActivityList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mActivityList = new ArrayList<>();
        mActivityList.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityList.remove(this);
    }
    public void finishAll(){
        for (Activity activity:mActivityList){
            activity.finish();
        }
    }
}
