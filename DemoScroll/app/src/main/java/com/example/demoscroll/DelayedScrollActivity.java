package com.example.demoscroll;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DelayedScrollActivity extends AppCompatActivity implements
        View.OnLongClickListener, View.OnClickListener{
    //private static final String TAG = "DelayedScrollActivity";
    private static final String TAG = "DelayedTestButton";
    private final int MESSAGE_SCROLL_TO_LEFT = 1;
    private final int MESSAGE_SCROLL_TO_RIGHT = 2;
    /* this sample divide one moving per 1000ms into 30 times moving and every moving cost 33ms*/
    /* translation distance from original position to the new one is 100dpi*/
    private final int FRAME_COUNT = 30;
    private final int DELAYED_TIME = 33;
    private int mCount = 0;
    private boolean scrollToLeft = true;
    private boolean scrolledDone = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayed_scroll);
        initView();
    }

    private TextView tv_delayed_scroll;
    private Button btn2;
    private void initView() {
        tv_delayed_scroll = (TextView) findViewById(R.id.customized_btn);
        tv_delayed_scroll.setOnLongClickListener(this);
        btn2 = (Button) findViewById(R.id.btn_delayed_scroll_test);
        btn2.setOnClickListener(this);

    }

    @Override
    public boolean onLongClick(View v) {
        //return false;
        if (v.getId() == R.id.customized_btn)
            Log.d(TAG, "onLongClick");
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delayed_scroll_test:
                /* 延迟方式实现滑动效果，滑动使用属性动画的setTranslationX来实现的 */
                Log.d(TAG, "onClick event");
                if (scrollToLeft) {
                    mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO_LEFT, DELAYED_TIME);
                }
                else
                    mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO_RIGHT, DELAYED_TIME);
                break;
            default:
                break;
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLL_TO_LEFT:
                case MESSAGE_SCROLL_TO_RIGHT:
                    if (!scrolledDone)
                        return;
                    mCount++;
                    if (mCount <= FRAME_COUNT) {
                        Log.d(TAG, "handleMessage sequence NO.:" + mCount);
                        float fraction = mCount / (float) FRAME_COUNT;
                        /* 500dpi */
                        int scrollX = (int) (fraction * 500);
                        if (msg.what == MESSAGE_SCROLL_TO_LEFT) {
                            tv_delayed_scroll.scrollTo(scrollX, 0);
                            mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO_LEFT, DELAYED_TIME);
                        } else {
                            tv_delayed_scroll.scrollTo(-scrollX, 0);
                            mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO_RIGHT, DELAYED_TIME);
                        }
                    } else {
                        scrolledDone = true;
                        mCount = 0;
                        scrollToLeft = !scrollToLeft;
                    }
                    break;

                default:
                    //super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /* 做个试验：尝试获取视图的坐标 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.d(TAG, "button1.left=" + tv_delayed_scroll.getLeft());
            Log.d(TAG, "button1.x=" + tv_delayed_scroll.getX());
        }
    }
}
