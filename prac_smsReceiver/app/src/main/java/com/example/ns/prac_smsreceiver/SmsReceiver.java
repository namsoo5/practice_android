package com.example.ns.prac_smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {

    private static final String Tag = "SmsReceiver";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Tag, "리시버호출");

        Bundle bundle = intent.getExtras(); //bundle
        SmsMessage[] messages = parseSmsMessage(bundle);
        //sms데이터를받아왓음

        if(messages.length>0){
            String sender = messages[0].getOriginatingAddress();//발신번호
            Log.d(Tag, "sender: "+sender);

            String contents = messages[0].getMessageBody().toString();
            Log.d(Tag,"contents: "+contents);

            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.d(Tag, "time: "+receivedDate);

            sendToActivity(context, sender, contents, receivedDate);
        }
    }

    private void sendToActivity(Context context, String sender, String contents, Date receivedDate){
        Intent intent = new Intent(context, SmsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.putExtra("receivedDate", format.format(receivedDate));


        context.startActivity(intent);
    }


    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Object[] objs = (Object[]) bundle.get("pdus"); //표준단어, sms관련데이터가들어잇음
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0; i<objs.length; i++){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            }else{
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }
}
