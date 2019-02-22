package com.example.ns.prac_cameraserfaceview;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    SurfaceHolder holder;
    Camera camera = null;

    public CameraSurfaceView(Context context) {
        super(context);

        init(context);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        holder = getHolder();
        holder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {  //만들어지는시점 콜백함수
        camera = Camera.open();

        try {
            camera.setPreviewDisplay(holder);  //카메라객체에 이 surfaceview를 미리보기로쓸것

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override   //변경되는 시점
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        camera.startPreview();  //크기가결정되는시점에 미리보기화면에 픽셀을뿌림

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview(); //미리보기중지
        camera.release();   //자원해제
        camera = null;
    }

    public boolean capture(Camera.PictureCallback callback){  //사진찍기
        if(camera != null){
            camera.takePicture(null, null, callback);
            return true;
        }else
            return false;
    }
}
