package com.example.ns.prac_cameraserfaceview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    File file;

    ImageView imageView2;
    CameraSurfaceView cameraSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ////////////////카메라인텐트이용한 사진찍기///////////////////////
        File sdcard = Environment.getExternalStorageDirectory(); //sdcard쪽
        file = new File(sdcard, "capture.jpg");

        imageView = findViewById(R.id.imageView);
        Button bt = findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });

        ///////////////////////////////////////////////////////////////

        imageView2 = findViewById(R.id.imageView2);
        cameraSurfaceView = findViewById(R.id.surface);

        Button bt2 = findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureSurface();
            }
        });
    }

    public void captureSurface(){
        cameraSurfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;  //   1/8크기로 제작
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

                imageView2.setImageBitmap(bitmap);

                camera.startPreview();  //사진찍으면 미리보기중지됌 다시실행시켜줘야함
            }
        });
    }





    public void capture() {//카메라인텐트이용한 사진찍기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //카메라 띄우기
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)); // 어떤파일로저장할것인가
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 101){   //카메라인텐트이용한 사진찍기
            if(resultCode == RESULT_OK){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8; //   1/8크기로 비트맵만들어줌
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
