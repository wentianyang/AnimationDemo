package com.ytw.example.animationdemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ytw on 2017/5/27.
 */

public class MyDrawingView extends View {

  private static final String MESSAGE = "收藏成功";

  private static final int START_RADIUS = 0;
  private int END_RADIUS;
  private float mStartX, mStartY;
  private float mRadius;
  private int mDisplayHeight;

  private float mStartTextX, mStartTextY;
  private float mTextWidth, mTextHeight;

  private Paint mRadiusPaint;
  private Paint mTextPaint;

  {
    //初始化圆点和半径
    mStartX = 0;
    mStartY = 0;
    mRadius = 0;

    //初始化画笔
    mRadiusPaint = new Paint();
    //设置画笔颜色
    mRadiusPaint.setColor(Color.parseColor("#FF4081"));
    //设置抗锯齿
    mRadiusPaint.setAntiAlias(true);
    //设置填充
    mRadiusPaint.setStyle(Paint.Style.FILL);

    //初始化文字画笔
    mTextPaint = new Paint();
    //设置画笔颜色
    mTextPaint.setColor(Color.WHITE);
    //设置抗锯齿
    mTextPaint.setAntiAlias(true);
    //设置大小
    mTextPaint.setTextSize(100);
    //设置透明度
    mTextPaint.setAlpha(0);
  }

  //该构造方法能保证在java代码中创建该View
  public MyDrawingView(Context context) {
    super(context);
    init(context);
  }

  //保证该控件能够在xml中使用
  public MyDrawingView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  void init(Context context) {
    //获取屏幕高度
    mDisplayHeight = context.getResources().getDisplayMetrics().heightPixels;
    END_RADIUS = mDisplayHeight;

    //计算文字的width height
    Rect rect = new Rect();
    mTextPaint.getTextBounds(MESSAGE, 0, MESSAGE.length(), rect);
    mTextWidth = rect.width();
    mTextHeight = rect.height();
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawCircle(mStartX, mStartY, mRadius, mRadiusPaint);
    canvas.drawText("收藏成功", mStartTextX, mStartTextY, mTextPaint);
  }

  /**
   * 初始化圆心位置
   *
   * @param startX x坐标
   * @param startY y坐标
   */
  public void initStartPosition(float startX, float startY) {
    this.mStartX = startX;
    this.mStartY = startY;

    this.mStartTextX = startX - mTextWidth / 2;
    this.mStartTextY = startY + mTextHeight / 2;
  }

  /**
   * 开启动画
   */
  public void startAnima() {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(START_RADIUS, END_RADIUS);
    valueAnimator.setDuration(1000);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        mRadius = value;
        //请求重绘
        invalidate();
      }
    });
    valueAnimator.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {

      }

      @Override public void onAnimationEnd(Animator animation) {
        startTextAnima();
      }

      @Override public void onAnimationCancel(Animator animation) {

      }

      @Override public void onAnimationRepeat(Animator animation) {

      }
    });
    valueAnimator.start();
  }

  /**
   * 开启文字显示动画
   */
  private void startTextAnima() {
    ValueAnimator textAnimation = ValueAnimator.ofFloat(0, 255);
    textAnimation.setDuration(600);
    textAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        mTextPaint.setAlpha((int) value);
        invalidate();
      }
    });
    textAnimation.start();
  }

  public void endAnima() {
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(END_RADIUS, START_RADIUS);
    valueAnimator.setDuration(1000);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        mRadius = value;
        //请求重绘
        invalidate();
      }
    });

    valueAnimator.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {
        mTextPaint.setAlpha(0);
        invalidate();

      }

      @Override public void onAnimationEnd(Animator animation) {

      }

      @Override public void onAnimationCancel(Animator animation) {

      }

      @Override public void onAnimationRepeat(Animator animation) {

      }
    });
    valueAnimator.start();
  }
}
