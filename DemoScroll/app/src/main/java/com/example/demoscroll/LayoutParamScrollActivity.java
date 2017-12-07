package com.example.demoscroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LayoutParamScrollActivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_param_scroll);
        mButton = (Button)findViewById(R.id.btn_layout_param);
    }

    public void onClick(View v) {
        ViewGroup.MarginLayoutParams params =
             (ViewGroup.MarginLayoutParams) mButton.getLayoutParams();
        // params.width += 100;
        params.leftMargin += 100;
        mButton.requestLayout();
        // 或者mButton.setLayoutParams(params);
    }
}
