package com.example.demodispatchtouchevent.wedgit;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 17-12-7.
 */

public class HoriScrollViewGroup extends ViewGroup {
    private static final String TAG = "HoriScrollViewGroup";
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    /* 页面个数 */
    private int mChildrenSize;
    /* 页面索引 */
    private int mChildIndex;
    /* 页面宽度 */
    private int mChildWidth;

    public HoriScrollViewGroup(Context context) {
        super(context);
        initView();
    }

    public HoriScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HoriScrollViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST
                && heightSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int width = child.getMeasuredWidth() * childCount;
            int height = child.getMeasuredHeight() * childCount;
            setMeasuredDimension(width, height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int width = child.getMeasuredWidth() * childCount;
            setMeasuredDimension(width, heightSpaceSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int height = child.getMeasuredHeight() * childCount;
            setMeasuredDimension(widthSpaceSize, height);
        } else {
            setMeasuredDimension(widthSpaceSize, heightSpaceSize);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childWidth = getChildAt(0).getMeasuredWidth();
        mChildrenSize = childCount;
        mChildWidth = childWidth;
        int left = 0;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childView.layout(left, 0, childWidth+left, childView.getMeasuredHeight());
                left += childWidth;
            }
        }
    }

    /* 用于判别是否拦截 */
    private int mLastInterceptedX = 0;
    private int mLastInterceptedY = 0;
    /* 用于处理事件 */
    private int mLastX = 0;
    private int mLastY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int)ev.getX();
        int y = (int)ev.getRawY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = y - mLastX;
                int deltaY = y -mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        Log.d(TAG, "onInterceptTouchEvent: " + ev.getAction() + "intercepted:" + intercepted);
        mLastInterceptedX = x;
        mLastInterceptedY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 追踪点击事件速度
        mVelocityTracker.addMovement(event);
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                // 计算1000ms内的速度
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50) {
                    // 快速左右滑动时计算页面索引方式
                    // 向左或者向右移动几个页面
                    mChildIndex = xVelocity > 0? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    // 普通速度用滑动距离来计算页面索引
                    mChildIndex = (scrollX + mChildWidth/2)/mChildWidth;
                }

                // 求有效索引。Math.min(mChildIndex, mChildrenSize-1)避免索引超出最大索引。
                // 再进行一次max运算就可以计算出避免越过最低有效索引0的情况。
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize-1));
                int dx = (mChildIndex * mChildWidth) - scrollX;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollBy(int x, int y) {
        mScroller.startScroll(getScrollX(), 0, x, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
