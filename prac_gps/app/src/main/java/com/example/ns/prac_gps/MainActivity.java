package com.example.ns.prac_gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    ToggleButton tb;
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1); //권한허용창
        }

        tv2 = findViewById(R.id.tv2);
        tv2.setText("위치정보 미수신중");

        tb = findViewById(R.id.toggleButton);

        //LocationManager객체얻어옴
        final LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (tb.isChecked()) {
                        tv2.setText("수신중...");
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, //위치제공자
                                0, //최소시간간격
                                0,   //최소반경거리
                                mLocationListener);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, //위치제공자
                                0, //최소시간간격
                                0,   //최소반경거리
                                mLocationListener);
                    } else {
                        tv2.setText("위치정보 미수신중");
                    }
                }catch (SecurityException e){

                }
            }
        });

    }


    //콜백 메소드
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //위치값 갱신시 이벤트발생
            //값은 location 형태로 리턴

            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();//위도
            double altitude = location.getAltitude(); //고도
            float accuracy = location.getAccuracy(); //정확도
            String provider = location.getProvider(); //위치제공자
            // gps가 network위치보다 정확도가 좋다

            tv2.setText("위치정보 : "+provider
            +"\n위도 : "+longitude
            +"\n경도 : "+latitude
            +"\n고도 : "+altitude
            +"\n정확도 : "+accuracy);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //disable시
        }

        @Override
        public void onProviderEnabled(String provider) {
            //Enable시
        }

        @Override
        public void onProviderDisabled(String provider) {
            //변경시
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, "gps권한 승인", Toast.LENGTH_SHORT).show();
                }

        }
    }
}
