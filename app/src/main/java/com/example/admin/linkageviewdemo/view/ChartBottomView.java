package com.example.admin.linkageviewdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.admin.linkageviewdemo.R;
import com.example.admin.linkageviewdemo.model.BottomModel;
import com.example.admin.linkageviewdemo.model.TopModel;

import java.util.List;

/**
 * Created by BearMuonten
 * email:18310035168@163.com
 * date:2018/3/27
 * 描述:绘制k
 */

public class ChartBottomView extends BaseChart {

    private List<BottomModel> bottomList;

    private float evenWidth = 0;
    private float evenHeight = 0;

    private float max;
    private float min;

    private Paint mLinePaint;

    private ValueAnimator mAnimator;

    private float paintWidth = 0;
    private float lineClearance = 0;

    private int showNum = SHOW_DEFAULT_NUM;
    private int scrollCoefficient = 1;

    private int startIndex = 0;


    /**
     * 预留高度可以设置
     */
    private float reserveHeight = 20;

    public ChartBottomView(Context context) {
        super(context);
    }

    public ChartBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChartBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        mLinePaint.setAntiAlias(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bottomList != null && bottomList.size() != 0) {
            max = getMax(bottomList);
            min = getMin(bottomList);
            evenHeight = (mChartHeight - reserveHeight) * 1.0f / max;

            int mAnimaValue = (int) mAnimator.getAnimatedValue();
            evenWidth = mChartWidth * 1.0f / showNum * mAnimaValue / 100;

            paintWidth = evenWidth * 4.0f / 5.0f;
            lineClearance = evenWidth * 1.0f / 5.0f;
            mLinePaint.setStrokeWidth(paintWidth);

            drawBottomLine(canvas);

        }
    }

    /**
     * 绘制柱状图
     *
     * @param canvas
     */
    private void drawBottomLine(Canvas canvas) {
        for (int i = 0; i < showNum; i++) {
            if (bottomList.get(startIndex + i).getBottomY() >= 0) {
                mLinePaint.setColor(getResources().getColor(R.color.colorKRed));
            } else {
                mLinePaint.setColor(getResources().getColor(R.color.colorKGreen));
            }
            canvas.drawLine(evenWidth * i, mChartHeight - Math.abs(evenHeight * bottomList.get(startIndex + i).getBottomY()), evenWidth * i, mChartHeight, mLinePaint);

        }
    }


    /**
     * 设置数据
     *
     * @param list
     */
    public void setBottomData(List<BottomModel> list) {
        this.bottomList = list;
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

    private int getMax(List<BottomModel> list) {
        int max = 0;
        for (int i = 0; i < showNum; i++) {
            if (max < Math.abs(list.get(startIndex+i).getBottomY())) {
                max = Math.abs(list.get(startIndex+i).getBottomY());
            }
        }
        return max;
    }


    private int getMin(List<BottomModel> list) {
        int min = 0;
        for (int i = 0; i < showNum; i++) {
            if (min > list.get(startIndex+i).getBottomY()) {
                min = list.get(startIndex+i).getBottomY();
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
        if (bottomList == null) {
            return;
        }

        if (scrollX >0) {
            int addIndext = (int) (Math.abs(scrollX) * scrollCoefficient / evenWidth);
            startIndex = startIndex + addIndext;
            if (startIndex + showNum > bottomList.size()) {
                startIndex = bottomList.size() - showNum;
            }
        } else {
            int addIndext = 0-(int) (Math.abs(scrollX) * scrollCoefficient / evenWidth);
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
        } else if (showNum <SHOW_DEFAULT_NUM ) {
            showNum = SHOW_DEFAULT_NUM;
        }
        if (showNum + startIndex > bottomList.size()) {

            startIndex = bottomList.size() - showNum;
            if(startIndex<0){
                startIndex=0;
            }

        }


        invalidate();
    }
}
