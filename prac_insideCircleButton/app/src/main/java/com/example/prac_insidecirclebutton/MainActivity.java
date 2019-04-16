package com.example.prac_insidecirclebutton;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

public class MainActivity extends AppCompatActivity {
    BoomMenuButton bmb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bmb= findViewById(R.id.bmb);

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            switch(i){
                case 0:
                case 1:
                case 2:
                    bmbMake(R.color.yellow,i);
                    break;
                case 3:
                    bmbMake(R.color.orange,i);
                    break;
                case 4:
                case 5:
                case 6:
                    bmbMake(R.color.yellow,i);
                    break;
                default:
                    return;
            }

        }
    }

    public void bmbMake(int resColor, int index){
        TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                //.normalImageRes(R.mipmap.ic_launcher)
                .normalText("example"+index)
                .normalTextColor(Color.BLACK)
                .textSize(12)
                .normalColorRes(resColor)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Toast.makeText(MainActivity.this, "select: "+index, Toast.LENGTH_LONG).show();
                    }
                });
        bmb.addBuilder(builder);
    }
}
