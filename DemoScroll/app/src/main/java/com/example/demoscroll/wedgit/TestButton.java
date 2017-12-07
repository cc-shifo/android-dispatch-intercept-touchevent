package com.example.demoscroll.wedgit;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Created by Administrator on 17-12-6.
 */

public class TestButton extends AppCompatTextView {
    private static final String TAG = "DelayedTestButton";
    
    public TestButton(Context context) {
        this(context, null);
    }

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private int mScaledTouchSlop;
    private void init() {
        int mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.d(TAG, "init: " + mScaledTouchSlop);
    }

    // 分别记录上次滑动的坐标。getX()和getRawX()区别：getX获取的坐标是相对于当前这个View的左上
    // 角的坐标, getRawX()获取的是相对于手机屏幕左上角的坐标(艺术探索P125)。
    private int mLastX = 0;
    private int mLastY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // return super.onTouchEvent(event);
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        Log.d(TAG, "onTouchEvent: rawX=" + x + ", lastX=" + mLastX);
        Log.d(TAG, "onTouchEvent: rawY=" + y + ", lastY=" + mLastY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d(TAG, "onTouchEvent: action down");
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d(TAG, "onTouchEvent: action move");
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
                Log.d(TAG, "translationX=" + this.getTranslationX());
                Log.d(TAG, "translationY=" + this.getTranslationY());
                // ViewHelper是nineoldandroids中的类，android3.0提供了View.get/setTranslationX/Y()
                /*int translationX = (int)ViewHelper.getTranslationX(this) + deltaX;
                int translationY = (int)ViewHelper.getTranslationY(this) + deltaY;
                ViewHelper.setTranslationX(this, translationX);
                ViewHelper.setTranslationY(this, translationY);*/
                int translationX = (int)this.getTranslationX() + deltaX;
                int translationY = (int)this.getTranslationY() + deltaY;
                this.setTranslationX((float)translationX);
                this.setTranslationY((float)translationY);
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.d(TAG, "onTouchEvent: action up");
                break;
            }
            default:
                Log.d(TAG, "onTouchEvent: action default");
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }
}
