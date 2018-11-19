package com.example.ns.pro_exchangerate;

import android.provider.BaseColumns;

public final class CreatDB implements BaseColumns{
    public static final String CATEGORY = "category";
    public static final String MEMO = "memo";
    public static final String MONEY = "money";
    public static final String EXCHANGE = "exchange";
    public static final String EXCHANGEMONEY = "exchangemoney";
    public static final String _TABLENAME0 = "maintable";
    public static final String _CREATE0 = "create table if not exists "+_TABLENAME0
            +"(id integer primary key autoincrement, "
            +CATEGORY+" integer not null , "
            +MEMO+" text not null , "
            +MONEY+" text not null , "
            +EXCHANGE+" text not null , "
            +EXCHANGEMONEY+" text not null );";

}
