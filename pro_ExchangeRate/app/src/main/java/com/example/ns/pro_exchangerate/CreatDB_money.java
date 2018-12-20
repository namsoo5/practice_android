package com.example.ns.pro_exchangerate;

import android.provider.BaseColumns;

public class CreatDB_money implements BaseColumns {
    public static final String EXCHANGE = "exchange";
    public static final String EXCHANGEMONEY = "exchangemoney";
    public static final String _TABLENAME1 = "subtable";
    public static final String _CREATE1 = "create table if not exists "+_TABLENAME1
            +"("+EXCHANGE+" text not null, "
            +EXCHANGEMONEY+ " integer primary key not null);";
}
