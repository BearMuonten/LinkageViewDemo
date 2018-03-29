package com.example.admin.linkageviewdemo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.admin.linkageviewdemo.R;
import com.example.admin.linkageviewdemo.model.TopModel;

import java.util.List;


/**
 * Created by BearMuonten
 * email:18310035168@163.com
 * date:2018/3/26
 * 描述：绘制k线上半部分图
 */

public class ChartTopView extends BaseChart {

    private List<TopModel> topList;

    private float evenWidth = 0;
    private float evenHeight = 0;

    private float max;
    private float min;

    private Paint mLinePaint;

    private ValueAnimator mAnimator;

    private int scrollCoefficient = 1;
    private int startIndex = 0;
    private int showNum = SHOW_DEFAULT_NUM;

    public ChartTopView(Context context) {
        super(context);
    }

    public ChartTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChartTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void init() {
        super.init();

        mAnimator = ValueAnimator.ofInt(0, 100);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(500);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });

        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(getResources().getColor(R.color.colorKLine));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (topList != null && topList.size() != 0) {
            max = getMax(topList);
            min = getMin(topList);
            evenHeight = mChartHeight * 1.0f / ((max - min) * 1.0f);
            evenWidth = mChartWidth * 1.0f / (showNum) * 1.0f;

            drawLine(canvas);
            drawArea(canvas);
        }

    }

    /**
     * 画线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        int mAnimaValue = (int) mAnimator.getAnimatedValue();
        for (int i = 0; i < showNum - 1; i++) {

            canvas.drawLine(evenWidth * i * 1.0f * mAnimaValue / 100, mChartHeight-(topList.get(startIndex + i).getTopY() - min) * evenHeight, evenWidth * (i + 1) * 1.0f * mAnimaValue / 100, (mChartHeight-topList.get(startIndex + i + 1).getTopY() - min) * evenHeight, mLinePaint);

        }
    }

    /**
     * 画区域(路径封装主要用于设置阴影)
     *
     * @param canvas
     */
    private void drawArea(Canvas canvas) {
        Path path = new Path();
        int mAnimaValue = (int) mAnimator.getAnimatedValue();
        for (int i = 0; i < showNum - 1; i++) {
            if (i == 0) {
                path.moveTo(evenWidth * i * 1.0f * mAnimaValue / 100, mChartHeight-(topList.get(startIndex + i).getTopY() - min) * evenHeight);
            } else {
                path.lineTo(evenWidth * i * 1.0f * mAnimaValue / 100, mChartHeight-(topList.get(startIndex + i).getTopY() - min) * evenHeight);
            }
        }

        LinearGradient gradient = new LinearGradient(0.0f,
                (0),
                0,
                mChartHeight,
                getResources().getColor(R.color.colorKLine),
                Color.WHITE,
                Shader.TileMode.CLAMP);

        mLinePaint.setShader(gradient);
        path.lineTo(mChartWidth * mAnimaValue / 100, mChartHeight);
        path.lineTo(0, mChartHeight);
        canvas.drawPath(path, mLinePaint);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setTopData(List<TopModel> list) {
        this.topList = list;
        mAnimator.start();
        invalidate();

    }

    /**
     * 提供给外部调用动画的方法
     */
    public void startAnima() {
        if (mAnimator != null) {
            mAnimator.start();
        }
        invalidate();
    }

    private int getMax(List<TopModel> list) {
        int max = 0;
        for (int i = 0; i < showNum; i++) {
            if (max < list.get(startIndex + i).getTopY()) {
                max = list.get(startIndex + i).getTopY();
            }
        }
        return max;
    }


    private int getMin(List<TopModel> list) {
        int min = 0;
        for (int i = 0; i < showNum; i++) {
            if (min > list.get(startIndex + i).getTopY()) {
                min = list.get(startIndex + i).getTopY();
            }
        }
        return min;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    /**
     * 手势滑动时会调用该方法
     */
    public void changeStartIndext(float scrollX) {
        if (topList == null) {
            return;
        }

        if (scrollX > 0) {
            int addIndext = (int) (Math.abs(scrollX) * scrollCoefficient / evenWidth);
            startIndex = startIndex + addIndext;
            if (startIndex + showNum > topList.size()) {
                startIndex = topList.size() - showNum;
            }
        } else {
            int addIndext = 0 - (int) (Math.abs(scrollX) * scrollCoefficient / evenWidth);
            startIndex = (int) (startIndex + addIndext);
            if (startIndex < 0) {
                startIndex = 0;
            }
        }

        invalidate();

    }

    public void scaleView(float scale) {
        showNum = (int) (showNum+showNum * scale);
        if (showNum > SHOW_MAX_NUM) {
            showNum = SHOW_MAX_NUM;
        } else if (showNum < SHOW_DEFAULT_NUM) {
            showNum = SHOW_DEFAULT_NUM;
        }
        if (showNum + startIndex > topList.size()) {

            startIndex = topList.size() - showNum;
            if(startIndex<0){
                startIndex=0;
            }

        }


        invalidate();
    }
}
