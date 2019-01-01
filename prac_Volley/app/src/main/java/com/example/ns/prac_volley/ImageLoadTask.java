package com.example.ns.prac_volley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String urlStr;
    private ImageView imageView;

    //이전정보 삭제하기
    private static HashMap<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();


    public ImageLoadTask(String urlStr, ImageView imageView) {
        this.urlStr = urlStr;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        imageView.setImageBitmap(bitmap);
        imageView.invalidate();  //다시그리기
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = null;
        try {

            if(bitmapHash.containsKey(urlStr)){  //url요청정보잇다면
                Bitmap oldBitmap = bitmapHash.remove(urlStr);  //해쉬맵에서 삭제
                if(oldBitmap != null){
                    oldBitmap.recycle();//메모리상 삭제
                    oldBitmap=null;  // <-없어도됌
                }
            }

            URL url = new URL(urlStr);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            //주소접속해서 이미지면 이미지그대로 넘어옴

            bitmapHash.put(urlStr, bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;  //onPostExecute로반환
    }
}
