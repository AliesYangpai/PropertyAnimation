package org.alie.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
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
                startObjectAnimationAlpha(iv);
                break;
            case R.id.btn2:
                startObjectAnimationTranslationX(iv);
                break;
            case R.id.btn3:
                startAnimatorSet(iv);
                break;
            case R.id.btn4:
                startEvaluator(iv);
                break;
        }
    }

    /**
     * 属性动画初级，透明度的改变
     *
     * @param imageView
     */
    private void startObjectAnimationAlpha(ImageView imageView) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1F, 0.3F);
        alpha.setDuration(1000);
        alpha.start();
    }

    /**
     * 属性动画初级，横向平移300px
     *
     * @param imageView
     */
    private void startObjectAnimationTranslationX(ImageView imageView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 0, 300);
        animator.setDuration(1000);
        animator.start();
    }

    /**
     * 属性动画初级，animator组合属性动画
     *
     * @param imageView
     */
    private void startAnimatorSet(ImageView imageView) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(imageView, "translationX", 0, 300);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", 0, 300);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(translationX, translationY);
        animatorSet.start();
    }

    private void startEvaluator(final ImageView imageView) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(null, new PointF());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF pointF = new PointF();
                pointF.x = 400 * fraction * 2;
                pointF.y = 400 * fraction * 2 * fraction * 2;
                return pointF;
            }
        });

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

}
