package com.example.acer_pc.googleplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer_pc.googleplay.utils.UIUtils;
import com.example.acer_pc.googleplay.view.LoadingPage;

/**
 * Created by acer-pc on 2017/6/2.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPage mLoadPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView textView=new TextView(UIUtils.getContext());
//        textView.setText(getClass().getSimpleName());

//        return textView;
        mLoadPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            protected ResultState initData() {
                return BaseFragment.this.onLoad();
            }

            @Override
            public View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();
            }
        };
        return mLoadPage;

    }

    public abstract View onCreateSuccessView();

    public abstract LoadingPage.ResultState onLoad();

    public void loadData() {
        if (mLoadPage!=null){
            mLoadPage.loadData();
        }
    }
}
