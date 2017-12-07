package com.example.demoscroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class ViewAnimatorScrollActivity extends AppCompatActivity {
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animator_scroll);
        mButton = (Button)findViewById(R.id.btn_view_animator);
    }

    public void onClick (View v) {
        if (v.getId() == R.id.btn_view_animator) {
            // 1.当使用xml创建动画时，需要将动画通过loadAnimation()方法加载进java代码，来构建一个
            // Animation的动画对象。
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
            v.startAnimation(animation);
            // 2.btn_view_animator按钮设置了fillAfter属性来保持动画效果，否则在动画完成的那一瞬间，
            // View又会恢复到之前的位置。
            // 3.View动画可以改变View的内容，乃至整个View的内容(即在别的地方再画这个View)，但是
            // 不能改变View的位置等属性。测试发现View动画结束后，虽然设置了保存动画效果，我们依然
            // 可以在View的原始位置点击一下，然后还是可以重新开始一次动画效果，这就说明了View动画
            // 只能改变内容，不能改变View的属性。
        }

    }
}
