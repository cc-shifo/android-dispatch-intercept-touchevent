package com.example.demoscroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn1:
                intent = new Intent(this, LayoutParamScrollActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent = new Intent(this, PropertyAnimatorScrollActivity.class);
                startActivity(intent);
                break;
            case R.id.btn3:
                intent = new Intent(this, ViewAnimatorScrollActivity.class);
                startActivity(intent);
                break;
            case R.id.btn4:
                intent = new Intent(this, DelayedScrollActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
