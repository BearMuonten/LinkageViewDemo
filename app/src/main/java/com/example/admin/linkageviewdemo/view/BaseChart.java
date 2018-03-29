package com.example.admin.linkageviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.admin.linkageviewdemo.R;

/**
 * Created by BearMuonten
 * email:18310035168@163.com
 * date:2018/3/26
 * 描述：所有图标的基础类
 */

public class BaseChart extends View {

    private final int TRANSVERSE_DEFAULT_NUM=2;
    private final int LONGITUDINAL_DEFAULT_NUM=2;
    //滑动系数
    public final int COEFFICIENT=2;

    //一屏显示数据个数
    public final int SHOW_DEFAULT_NUM=100;

    public final int SHOW_MAX_NUM=200;


    private Paint chartPaint;
    private Paint textPaint;

    public int mChartWidth;
    public int mChartHeight;

    /**
     * 默认横纵坐标个数
     */
    private int mChartDefaultTransverse=TRANSVERSE_DEFAULT_NUM;
    private int mChartDefaultLongitudinal=LONGITUDINAL_DEFAULT_NUM;

    public BaseChart(Context context) {
        super(context);
        init();
    }

    public BaseChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 相关初始化操作
     */
    public void init(){
        chartPaint=new Paint();
        chartPaint.setColor(getResources().getColor(R.color.colorChartPaint));
        chartPaint.setStrokeWidth(2);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mChartHeight=getHeight();
        mChartWidth=getWidth();

        Log.i("TagXiong","高："+mChartHeight);


        drawLongitudinal(canvas);
        drawTransverse(canvas);
    }

    /**
     * 绘制基础表格的横线
     * @param canvas
     */
    private void drawTransverse(Canvas canvas){
        for(int i=0;i<mChartDefaultTransverse;i++){
          if(i==0){
              canvas.drawLine(0, 1.0f * mChartHeight / (mChartDefaultTransverse-1) * i+2, mChartWidth, 1.0f * mChartHeight / (mChartDefaultTransverse-1) * i+2, chartPaint);
            }else if(i==mChartDefaultTransverse-1){
                canvas.drawLine(0, 1.0f * mChartHeight / (mChartDefaultTransverse-1) * i-2, mChartWidth, 1.0f * mChartHeight / (mChartDefaultTransverse-1) * i-2, chartPaint);
           }else{
              canvas.drawLine(0, 1.0f * mChartHeight / (mChartDefaultTransverse-1) * i, mChartWidth, 1.0f * mChartHeight / (mChartDefaultTransverse-1) * i, chartPaint);
          }
        }
    }

    /**
     * 绘制基础表格的纵线
     * @param canvas
     */
    private void drawLongitudinal(Canvas canvas){
        for(int i=0;i<mChartDefaultLongitudinal;i++){
            if(i==0) {
                canvas.drawLine(1.0f * mChartWidth / (mChartDefaultLongitudinal-1) * i+2, 0, 1.0f * mChartWidth /(mChartDefaultLongitudinal-1) * i+2, mChartHeight, chartPaint);
            }else if(i==mChartDefaultLongitudinal-1){
                canvas.drawLine(1.0f * mChartWidth / (mChartDefaultLongitudinal-1) * i-2, 0, 1.0f * mChartWidth / (mChartDefaultLongitudinal-1)* i-2, mChartHeight, chartPaint);
            }else{
                canvas.drawLine(1.0f * mChartWidth / (mChartDefaultLongitudinal-1) * i, 0, 1.0f * mChartWidth / (mChartDefaultLongitudinal-1) * i, mChartHeight, chartPaint);
            }
        }
    }


    /**
     * 设置横坐标的条数
     * @param num
     */
    public void setTransverse(int num){
        this.mChartDefaultTransverse=num;
    }

    /**
     * 设置纵坐标的条数
     * @param num
     */
    public void setLongitudinal(int num){
        this.mChartDefaultLongitudinal=num;
    }


}
