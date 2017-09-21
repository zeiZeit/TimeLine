package com.wind.zuozhuang.timeline;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wind.zuozhuang.timeline.View.CustomProgressBar;

public class MainActivity extends Activity {
    private boolean isOpen;
    private boolean isCameraModule;
    private boolean  isRecording;
    CustomProgressBar sb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sb=findViewById(R.id.SB);
        isCameraModule = true;
        isOpen = false;
        isRecording=false;
    }



    public void littleButton(View view){
        if (!isCameraModule){
            if (!isRecording){
                sb.setVideoStart(false);
                sb.setVideoStop(true);
                isRecording = true;
                Toast.makeText(this,"开始录像了，点击停止",Toast.LENGTH_SHORT).show();
            }else{
                sb.setVideoStart(true);
                sb.setVideoStop(false);
                isRecording=false;
                Toast.makeText(this,"录像结束，点击开始",Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(this,"拍照了",Toast.LENGTH_SHORT).show();
        }


    }

    public void switchModule(View view){
        if (isCameraModule){
            Log.i("zz", "switchModule:111111111 "+isCameraModule);
            sb.setVideoStart(true);
            isCameraModule=false;
            sb.setCameraModule(isCameraModule);
        }else{
            if(!isRecording){
                Log.i("zz", "switchModule:2222222222 "+isCameraModule);
                sb.setVideoStart(false);
                isCameraModule=true;
                sb.setCameraModule(isCameraModule);
            }

        }
    }

    public void open(View view){
        if (!isOpen){

            Log.i("zz", "switchModule:111111111 "+isCameraModule);
            sb.setOpen(true);
            isOpen=true;
        }else{
            Log.i("zz", "switchModule:2222222222 "+isCameraModule);
            sb.setOpen(false);
            isOpen=false;
        }
    }
}
