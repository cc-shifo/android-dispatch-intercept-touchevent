package com.example.demodispatchtouchevent.wedgit;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

import com.example.demodispatchtouchevent.utils.MyUtils;

/**
 * Created by Administrator on 17-12-8.
 */

public class MyListView extends ListView {
    private static final String TAG = "MyListView2";
    private HoriScrollViewGroup2 parentView;

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParentView(HoriScrollViewGroup2 parentView) {
        this.parentView = parentView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    private int mLastX = 0;
    private int mLastY = 0;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int)ev.getX();
        int y = (int)ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = 0;
                mLastY = 0;
                /**
                 * 请求父视图屏蔽拦截，也就是父视图不再拦截。即接着当ACTION_MOVE从
                 * OutterInterceptedActivity到HoriScrollViewGroup2时，ViewGroup.java会
                 * 执行2062行intercepted = false，不再执行询问操作onInterceptTouchEvent()。
                 */
                parentView.requestDisallowInterceptTouchEvent(true);
                Log.d(TAG, "dispatchTouchEvent: request true " + ev.getAction());
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y -mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // requestDisallowInterceptTouchEvent(false);
                    Log.d(TAG, "dispatchTouchEvent: request false");
                    parentView.requestDisallowInterceptTouchEvent(false);
                    //return false;
                }
                Log.d(TAG, "dispatchTouchEvent: " + ev.getAction());
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                Log.d(TAG, "onInterceptTouchEvent: default" + ev.getAction());
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
