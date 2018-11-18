package com.example.ns.pro_exchangerate;


import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class category_ex  extends AppCompatActivity{
    String s;
    int catenum = -1;
    public category_ex(String s){
        this.s = s;
    }
    public int getCategoryId() {
    //카테고리 입력값을 id로전환
        switch (s) {
            case "식사":
                catenum = 1;
                break;
            case "술":
                catenum = 2;
                break;
            default:
                catenum = -1;
                break;
        }

        return catenum;
    }

}
