package com.example.admin.linkageviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.example.admin.linkageviewdemo.R;
import com.example.admin.linkageviewdemo.model.BottomModel;
import com.example.admin.linkageviewdemo.model.TopModel;

import java.util.List;

/**
 * Created by BearMuonten
 * email:18310035168@163.com
 * date:2018/3/26
 * 描述：容纳上下两个view的group视图
 */

public class ChartGroup extends LinearLayout implements GestureDetector.OnGestureListener,ScaleGestureDetector.OnScaleGestureListener {

    private ChartBottomView chartBottomView;
    private ChartTopView chartTopView;

    private Context context;
    private float touchX;
    private float touchY;
    private int width;
    private int height;
    private Paint crossPaint;
    private boolean isDrawCross=false;
    protected ScaleGestureDetector mScaleDetector;
    protected GestureDetectorCompat mDetector;
    public OverScroller mScroller;
    private float mScaleMax=1.5f;
    private float mScaleMin=0.5f;
    private float mScale=1;
    private boolean isScrolling=false;
    private boolean isScaling=false;

    public ChartGroup(Context context) {
        super(context);
        init(context);
    }

    public ChartGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChartGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChartGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.kline_layout,this);

        chartBottomView=findViewById(R.id.kline_bottom);
        chartTopView=findViewById(R.id.kline_top);

        crossPaint=new Paint();
        crossPaint.setStrokeWidth(2);
        crossPaint.setColor(getResources().getColor(R.color.colorChartCross));

        mScaleDetector=new ScaleGestureDetector(getContext(),this);
        mDetector=new GestureDetectorCompat(getContext(),this);

    }

    /**
     * 设置数据
     * @param topList
     * @param bottomList
     */
    public void setData(List<TopModel> topList, List<BottomModel> bottomList){
        chartTopView.setTopData(topList);
        chartBottomView.setBottomData(bottomList);
    }

    public void startAniam(){
        chartTopView.startAnima();
        chartBottomView.startAnima();
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        width=getWidth();
        height=getHeight();

        Log.e("TagXiong","height"+height+"width"+width);

        if(isDrawCross) {
            drawCrossLine(canvas);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


    private void drawCrossLine(Canvas canvas){
        canvas.drawLine(touchX,0,touchX,height,crossPaint);
        canvas.drawLine(0,touchY,width,touchY,crossPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:
                touchX=event.getX();
                touchY=event.getY();
                Log.e("TagXiong","移动"+touchX+"y"+touchY);
                invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_UP:
                isDrawCross=false;
                invalidate();
                break;


            default:

                break;
        }
        mDetector.onTouchEvent(event);
        mScaleDetector.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }



    /**
     *+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++相关手势的一些操作++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float scrollX, float scrollY) {

        if(chartBottomView!=null){
            chartBottomView.changeStartIndext(scrollX);
            chartTopView.changeStartIndext(scrollX);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

        isDrawCross=true;
        touchX=motionEvent.getX();
        touchY=motionEvent.getY();
        invalidate();
        Log.e("TagXiong","长按了");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.e("TagXiong","快速滑动v"+v);
        Log.e("TagXiong","快速滑动v1"+v1);
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {


        Log.e("TagXiong","缩放"+scaleGestureDetector.getScaleFactor());
        if(scaleGestureDetector.getScaleFactor()< mScaleMin){
            mScale=mScaleMin;
        }else if(scaleGestureDetector.getScaleFactor()> mScaleMax){
            mScale=mScaleMax;
        }else{
            mScale=scaleGestureDetector.getScaleFactor();
        }
        chartTopView.scaleView(1-mScale);
        chartBottomView.scaleView(1-mScale);
        return true;

    }

    /**
     * 此处要返回true,onScale方法才能执行
     * @param scaleGestureDetector
     * @return
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    /**
     *+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++相关手势的一些操作++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
}
