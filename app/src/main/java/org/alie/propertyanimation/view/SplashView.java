package org.alie.propertyanimation.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.print.PrinterId;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import org.alie.propertyanimation.R;

/**
 * Created by Alie on 2019/5/28.
 * 类描述  旋转扩散特效的view
 * 版本
 */
public class SplashView extends View {

    private static final String TAG = "SplashView";
    private int[] colors;
    private Paint mPaint;

    private static final int SINGLE_SMALL_CIRCLE_RADIUS = 18; //每个小圆的半径
    private static final int BIG_CIRCLE_RADIUS = 90; // 大轮廓圆的半径
    private float mCurrentRotationAngle = 0F;    //当前大圆旋转角度(弧度)

    private int mCenterX;
    private int mCenterY;

    private ValueAnimator valueAnimator;

    public SplashView(Context context) {
        super(context);
        colors = context.getResources().getIntArray(R.array.circle_colors);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }


    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        colors = context.getResources().getIntArray(R.array.circle_colors);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"===onDraw");
        drawCircle(canvas);
        startAnimator();
    }


    private void drawCircle(Canvas canvas) {
        float rotationAngle = (float) (Math.PI * 2 / colors.length);
        for (int i = 0; i < colors.length; i++) {
            int color = colors[i];

            // 这里需要加上 mCenterX 和 mCenterY
            double currentAngle = rotationAngle * i + mCurrentRotationAngle;
            float cx = (float) (BIG_CIRCLE_RADIUS * Math.cos(currentAngle) + mCenterX);
            float cy = (float) (BIG_CIRCLE_RADIUS * Math.sin(currentAngle) + mCenterY);
            mPaint.setColor(color);
            canvas.drawCircle(cx, cy, SINGLE_SMALL_CIRCLE_RADIUS, mPaint);
        }
    }

    private void startAnimator() {
        // 这里必须要进行非空验证，否则每次都会开启初始动画
        if(valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0F, (float) (2 * Math.PI));
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //计算某个时刻当前的大圆旋转了的角度是多少？
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    Log.i(TAG,"===onAnimationUpdate:"+mCurrentRotationAngle);
                    postInvalidate();
                }
            });
            valueAnimator.setDuration(1200);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.start();
        }
    }
}
