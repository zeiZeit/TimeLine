package com.wind.zuozhuang.timeline.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wind.zuozhuang.timeline.R;

/**
 * Created by zuozhuang on 2017/8/10.
 */
public class CustomProgressBar extends View implements SensorEventListener {
    private final SensorManager mSensorManager;

    private  Boolean mIsOpen;
    private  Boolean mIsVideoStart;
    private  Boolean mIsVideoStop;
    private  Boolean mIsCameraModule;

    private int mCircleWidth = 10;

    private Paint mPaint;
    private float currentDegree;

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        Log.i("zzz", "111111111为什么会空指针："+a.getBoolean(R.styleable.CustomProgressBar_isOpen,false));
        mIsOpen = a.getBoolean(R.styleable.CustomProgressBar_isOpen,false);
        mIsVideoStart = a.getBoolean(R.styleable.CustomProgressBar_isVideoStart,false);
        mIsVideoStop = a.getBoolean(R.styleable.CustomProgressBar_isVideoStop,false);
        mIsCameraModule = a.getBoolean(R.styleable.CustomProgressBar_mIsCameraModule,true);
        int n = a.getIndexCount();//这里取得是设了值的数量
        a.recycle();
    }

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    /**
     * 必要的初始化，获得一些自定义的值
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);

        mPaint = new Paint();
        //绘图线程
        new Thread() {
            public void run() {
                while (true) {

                    postInvalidate();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            ;
        }.start();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                double d1 = sensorEvent.values[1];
                double d2 = sensorEvent.values[2];


                double tan = Math.sin(d1 * Math.PI / 180) / Math.sin(d2 * Math.PI / 180);
                Log.i("zuozhuang", "tan值为：" + tan);
                float degree1 = ((float) Math.atan(tan));

                float degree = (float) (degree1 * 180 / Math.PI) + 90;

                if ((Math.abs(d1) > 5) || (Math.abs(d2) > 5)) {
                    if (d2 < 0) {
                        currentDegree = degree - 180;
                    } else {
                        currentDegree = degree;
                    }

                    Log.i("zz", "第一个角度为：" + d1 + "，第二个角度为：" + d2);
                    Log.i("zz", "onSensorChanged:角度degree为： " + degree + "      currentDegree：" + currentDegree);
                } else {
                    currentDegree = 0;
                }

                break;

        }
    }

    @Override
    protected void onDraw( Canvas canvas) {

         int centreX = getWidth() / 2; // 获取圆心的x坐标
         int centreY = getHeight() / 2;
         int radius = centreX - mCircleWidth / 2;// 半径

        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心

         Paint paint2 = new Paint();
         int radius2 = centreX - mCircleWidth;
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);

        RectF r2 = new RectF();
        r2.left = (float) (centreX-0.5*radius2);
        r2.right = (float) (centreY-0.5*radius2);
        r2.top = (float) (centreX+0.5*radius2);
        r2.bottom = (float) (centreY+0.5*radius2);

        if (mIsCameraModule){
            paint2.setColor(Color.BLACK);
            canvas.drawCircle(centreX, centreY, radius2 - 8, paint2);
        }else {
            if (mIsVideoStart){
                paint2.setColor(Color.RED);
                canvas.drawCircle(centreX, centreY, radius2 - 8, paint2);
            } else if(mIsVideoStop){
                paint2.setColor(Color.RED);
               // canvas.drawRoundRect(r2,5f,5f,paint2);
               // canvas.drawRect(r2,paint2);
                canvas.drawRect((float) (centreX-0.5*radius2),(float) (centreY-0.5*radius2),
                        (float) (centreX+0.5*radius2),(float) (centreY+0.5*radius2),paint2);
            }
        }

        RectF oval = new RectF(centreX - radius, centreY - radius, centreX + radius, centreY + radius); // 用于定义的圆弧的形状和大小的界限

        mPaint.setColor(Color.GRAY); // 设置圆环的颜色
        canvas.drawCircle(centreX, centreY, radius, mPaint); // 画出圆环
        mPaint.setColor(Color.BLACK); // 设置圆环的颜色
        if (mIsOpen){
            Log.i("zzz", "222222222为什么会空指针："+mIsOpen);
            canvas.drawArc(oval, -currentDegree, 180, false, mPaint); // 根据进度画圆弧
        }


    }

    public boolean getCameraModule(){
        return mIsCameraModule;
    }
    public void setOpen(Boolean isOpen){
        mIsOpen = isOpen;
    }

    public void setVideoStart(Boolean isVideoStart){
        mIsVideoStart = isVideoStart;
    }

    public void setVideoStop(Boolean isVideoStop){
        mIsVideoStop = isVideoStop;
    }

    public void setCameraModule(Boolean isCameraModule){
        mIsCameraModule = isCameraModule;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
