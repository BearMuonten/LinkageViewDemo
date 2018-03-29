package com.example.admin.linkageviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.admin.linkageviewdemo.model.BottomModel;
import com.example.admin.linkageviewdemo.model.TopModel;
import com.example.admin.linkageviewdemo.view.ChartBottomView;
import com.example.admin.linkageviewdemo.view.ChartGroup;
import com.example.admin.linkageviewdemo.view.ChartTopView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

//    private ChartTopView mChartTopView;
//    private ChartBottomView mChartBottomView;
    private TextView mTvAniam;
    private List<TopModel> topList;
    private List<BottomModel> bottomList;
    private ChartGroup chartGroup;
    private WebView wb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mChartTopView = (ChartTopView) findViewById(R.id.charttop);
//        mChartBottomView= (ChartBottomView) findViewById(R.id.chartbottom);
        chartGroup= (ChartGroup) findViewById(R.id.chartgroup);
        mTvAniam = (TextView) findViewById(R.id.tv_anima);


        initData();
        initEvents();
    }


    private void initData() {
        topList = new ArrayList<>();
        bottomList=new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            TopModel model = new TopModel();
            model.setTopY(random.nextInt(50));
            topList.add(model);
        }
        for (int i = 0; i < 100; i++) {
            TopModel model = new TopModel();
            model.setTopY(random.nextInt(60));
            topList.add(model);
        }
        for (int i = 0; i < 50; i++) {
            TopModel model = new TopModel();
            model.setTopY(random.nextInt(10));
            topList.add(model);
        }
        for (int i = 0; i < 100; i++) {
            TopModel model = new TopModel();
            model.setTopY(random.nextInt(70));
            topList.add(model);
        }
        for (int i = 0; i < 100; i++) {
            TopModel model = new TopModel();
            model.setTopY(random.nextInt(100));
            topList.add(model);
        }
        for (int i = 0; i < 100; i++) {
            TopModel model = new TopModel();
            model.setTopY(random.nextInt(50));
            topList.add(model);
        }


        for(int i=0;i<100;i++){
            BottomModel model=new BottomModel();
            model.setBottomY(random.nextInt(100)-50);
            bottomList.add(model);
        }
        for(int i=0;i<100;i++){
            BottomModel model=new BottomModel();
            model.setBottomY(random.nextInt(120)-60);
            bottomList.add(model);
        }
        for(int i=0;i<50;i++){
            BottomModel model=new BottomModel();
            model.setBottomY(random.nextInt(20)-10);
            bottomList.add(model);
        }
        for(int i=0;i<100;i++){
            BottomModel model=new BottomModel();
            model.setBottomY(random.nextInt(140)-70);
            bottomList.add(model);
        }
        for(int i=0;i<100;i++){
            BottomModel model=new BottomModel();
            model.setBottomY(random.nextInt(200)-100);
            bottomList.add(model);
        }
        for(int i=0;i<100;i++){
            BottomModel model=new BottomModel();
            model.setBottomY(random.nextInt(100)-50);
            bottomList.add(model);
        }

        chartGroup.setData(topList,bottomList);
//        mChartTopView.setTopData(topList);
//        mChartBottomView.setBottomData(bottomList);
    }

    private void initEvents() {
        mTvAniam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mChartTopView.startAnima();
//                mChartBottomView.startAnima();
                chartGroup.startAniam();
            }
        });
    }
}
