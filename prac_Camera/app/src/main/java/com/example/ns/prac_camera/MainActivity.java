package com.example.ns.prac_camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    String filename; //파일이름
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_camera = findViewById(R.id.camera1_bt_camera);
        iv = findViewById(R.id.camera1_iv);

        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); //카메라앱키기

                File pictureFileDir = getDir();
                if(!pictureFileDir.exists() && !pictureFileDir.mkdirs()){
                    return; //이미폴더가존재함
                }
                SimpleDateFormat dateFormat =new SimpleDateFormat("yyyyMMddhhmmss");
                String date = dateFormat.format(new Date());  //사진이름을 시간으로구분
                String photoFile = "테스트"+date+".jpg";   //사진이름
                filename = pictureFileDir.getPath()+File.separator+photoFile;
                uri = FileProvider.getUriForFile(getApplicationContext(), "com.example.ns.prac_camera.fileprovider", new File(filename));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
               //사진저장
                startActivityForResult(intent, 100);

            }
        });

    }

    private File getDir(){  //폴더없는경우 폴더생성해줌
        File sdDir = new File(Environment.getExternalStorageDirectory() + "/DCIM/test");
        return sdDir;   //2번째인자 : 폴더이름 filepath랑 같아야함
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100 ) {
            if(resultCode == RESULT_OK) {

                iv.setImageURI(uri);    //이미지뷰에 사진넣기

                /* 갤러리에 보이게해주는 코드  */
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filename);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            }
        }
    }
}
