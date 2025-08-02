package com.example.intent_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.util.Log;

public class MySmsReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        processReceive(context, intent);
    }

    public void processReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Object[] smsExtra = (Object[]) extras.get("pdus");
            String format = extras.getString("format");

            String body = "";
            String address = "";
            String message = "";

            for (int i = 0; i < smsExtra.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i], format);
                body = sms.getMessageBody();
                address = sms.getOriginatingAddress();
                message += "Tin nhắn từ " + address + ":\n" + body + "\n";

                Log.d("MySmsReceive", "Tin nhắn từ " + address + ": " + body);
            }

            // Hiển thị thông báo
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
