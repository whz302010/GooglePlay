package com.example.acer_pc.googleplay.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.acer_pc.googleplay.R;
import com.example.acer_pc.googleplay.utils.UIUtils;

/**
 * Created by acer-pc on 2017/6/2.
 */

public abstract class LoadingPage extends FrameLayout {
    private static int STATE_UNDO = 0;
    private static int STATE_ERROR = 2;
    private static int STATE_EMPTY = 3;
    private static int STATE_SUCCESS = 4;
    private static int STATE_ONLOAD = 5;
    private static int mCurrentState = 0;
    private View mLoadingView;
    private View mLoadErrorView;
    private View mEmptyView;
    private View mLoadSuccessView;

    public LoadingPage(@NonNull Context context) {
        super(context);
        initView();
    }


    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (mLoadingView == null) {
            mLoadingView = UIUtils.inflate(R.layout.state_undo_view);
            addView(mLoadingView);
        }
        if (mLoadErrorView == null) {
            mLoadErrorView = UIUtils.inflate(R.layout.state_fail_view);
            addView(mLoadErrorView);
        }
        if (mEmptyView == null) {
            mEmptyView = UIUtils.inflate(R.layout.state_empty_view);
            addView(mEmptyView);
        }
        if (mLoadSuccessView == null && mCurrentState == STATE_SUCCESS) {
            mLoadSuccessView = onCreateSuccessView();
            if (mLoadingView != null) {
                addView(mLoadingView);
            }
        }
        showRightPage();


    }

    private void showRightPage() {
//        if (mCurrentState == STATE_UNDO || mCurrentState == STATE_ONLOAD) {
//            mLoadingView.setVisibility(VISIBLE);
//        } else {
//            mLoadingView.setVisibility(GONE);
//        }
        mLoadingView.setVisibility(mCurrentState == STATE_UNDO || mCurrentState == STATE_ONLOAD ? VISIBLE : GONE);
        if (mCurrentState == STATE_ERROR) {
            mLoadErrorView.setVisibility(VISIBLE);
        } else {
            mLoadErrorView.setVisibility(GONE);
        }
        if (mCurrentState == STATE_EMPTY) {
            mEmptyView.setVisibility(VISIBLE);
        } else {
            mEmptyView.setVisibility(GONE);
        }
        mLoadSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? VISIBLE : GONE);
    }

    //开始加载数据，在子线程中
    public void loadData() {
        new Thread(new Runnable() {
            ResultState resultState;
            @Override
            public void run() {
                if (resultState == null) {
                    resultState = initData();
                    mCurrentState = resultState.getState();
                    showRightPage();
                }

            }
        }).start();
    }

    protected abstract ResultState initData();

    public abstract View onCreateSuccessView();

    public enum ResultState {
        RESULT_SUCCESS(STATE_SUCCESS),
        RESULT_EMPTY(STATE_EMPTY),
        RESULT_FAIL(STATE_ERROR);
        private final int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
