package com.frontcamera.zhousong.frontcamera;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.SurfaceView;

public class MainActivity extends Activity {
    Context context = MainActivity.this;
    SurfaceView surfaceView;
    CameraSurfaceHolder mCameraSurfaceHolder = new CameraSurfaceHolder();

    public static Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vib = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
        initView();
    }

    public void initView()
    {
        setContentView(R.layout.activity_main);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        mCameraSurfaceHolder.setCameraSurfaceHolder(context,surfaceView);
    }

}
