package com.example.demodispatchtouchevent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private Button btnInterceptInner;
    private Button btnInterceptOutter;
    private void initView() {
        btnInterceptInner = (Button)findViewById(R.id.btn_horizontal_1);
        btnInterceptOutter = (Button) findViewById(R.id.btn_horizontal_2);
    }

    public void btnOnClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_horizontal_1:
                break;
            case R.id.btn_horizontal_2:
                break;
            default:
                break;
        }
    }
}
