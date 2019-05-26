package org.alie.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "MainActivity";
    private ImageView iv;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        iv = findViewById(R.id.iv);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
    }

    private void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startObjectAnimation(iv);
                break;
            case R.id.btn2:
                startValueAnimation(iv);
                break;
            case R.id.btn3:
                startAnimationSet(iv);
                break;
            case R.id.btn4:
                startPropertyValueHolderAnim(iv);
                break;
        }
    }

    /**
     * 属性动画初级，透明度的改变
     *
     * @param imageView
     */
    private void startObjectAnimation(ImageView imageView) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1F, 0.3F);
        alpha.setDuration(1000);
        alpha.setStartDelay(300);
        alpha.start();
    }

    /**
     * 属性动画动画监听
     *
     * @param imageView
     */
    private void startValueAnimation(final ImageView imageView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "pingyiba", 0F, 100F);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                Log.i(TAG, "===animatedValue:" + animatedValue);
                imageView.setScaleX(0.5f + animatedValue / 200);
                imageView.setScaleY(0.5f + animatedValue / 200);
            }
        });
        animator.start();
    }

    private void startAnimationSet(ImageView imageView) {
        // 平移的动画，0F,和200F 分辨代表平移起点，和终点
        ObjectAnimator animatorTraslationX = ObjectAnimator.ofFloat(imageView, "translationX", 0F, 200F);
        ObjectAnimator aninatorAlpha = ObjectAnimator.ofFloat(imageView, "alpha", 1F, 0.3F);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1F, 2F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1500);
        animatorSet.playSequentially(animatorTraslationX,aninatorAlpha,animatorScaleX);
        animatorSet.start();

    }

    public void startPropertyValueHolderAnim(ImageView imageView) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0.5f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holder1, holder2, holder3);
        animator.setDuration(200);
        animator.start();

    }
}
