package com.example.demoscroll;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.demoscroll.R.id.btn_property_animator;

public class PropertyAnimatorScrollActivity extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animator_scroll);
        mButton1 = (Button) findViewById(R.id.btn_property_animator_scroll);
        mButton2 = (Button) findViewById(R.id.btn_property_animator);
        mButton3 = (Button) findViewById(R.id.btn_property_object_animator);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_property_animator_scroll:
            {
                final int startX = 0;
                final int deltaX = 200;
                final View tmp_v = v;
                ValueAnimator anim = ValueAnimator.ofFloat(0f, 500f);
                anim.setDuration(1000);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        float fraction = animator.getAnimatedFraction();
                        // scrollTo()和scrollBy()只能改变内容。根据效果图，我们看到的确是内容位置变了。
                        mButton1.scrollTo(startX + (int) (deltaX * fraction), 0);

                        // 改变view的属性，改变view的位置
                        // float value = animator.getAnimatedValue();
                        // mButton1.setTranslationX(value);
                    }
                });
                anim.start();
                break;
            }
            case R.id.btn_property_animator:
            {
                //final int startX = 0;
                //final int deltaX = 200;
                ValueAnimator anim = ValueAnimator.ofFloat(0f, 500f);
                anim.setDuration(1000);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        float fraction = animator.getAnimatedFraction();
                        // scrollTo()和scrollBy()只能改变内容。根据效果图，我们看到的确是内容位置变了。
                        // mButton2.scrollTo(startX + (int) (deltaX * fraction), 0);

                        // 改变view的属性，改变view的位置
                        float value = (float)animator.getAnimatedValue();
                        mButton2.setTranslationX(value);
                    }
                });
                anim.start();
                break;
            }
            case R.id.btn_property_object_animator:
                // 属性动画
                ObjectAnimator.ofFloat(mButton3, "translationX", 0, 500)
                        .setDuration(1000)
                        .start();
                break;
            default:
                break;
        }

//            mButton.setTranslationX(100);

    }
}
