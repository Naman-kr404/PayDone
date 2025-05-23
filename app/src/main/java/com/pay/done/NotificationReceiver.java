package com.pay.done;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationReceiver extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals("net.one97.paytm")) {
            Notification notification = sbn.getNotification();
            Bundle extras = notification.extras;
            CharSequence notificationTitle = extras.getCharSequence(Notification.EXTRA_TITLE);
            if (notificationTitle!=null){
                String str1=notificationTitle.toString();
                if (str1.contains("Received ₹")){
                    String str2=str1.split("from")[0];
                    String str3=str2.substring(10);
                    Intent intent = new Intent("PaytmNotification");
                    intent.putExtra("title", str3);
                    sendBroadcast(intent);
                }
//                else {
//                    return;
//                }
            }
//            else {
//                return;
//            }
        }
        if (sbn.getPackageName().equals("com.phonepe.app")) {
            Notification notification = sbn.getNotification();
            Bundle extras = notification.extras;
            CharSequence notificationTitle = extras.getCharSequence(Notification.EXTRA_TITLE);
            CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
            CharSequence bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT);
            CharSequence[] notificationTextLines = extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);
            String str3=notificationText.toString();
            if (str3.contains("sent ₹")){
                String str4=str3.split("to")[0];
                String str5=str4.substring(6);
                Intent intent = new Intent("PhonePeNotification");
                intent.putExtra("title", str5);
                sendBroadcast(intent);
            }
//                    else{
//                        return;
//                    }
//                }
//                else {
//                    return;
//                }
//            }
//            else {
//                return;
//            }
        }
        if (sbn.getPackageName().equals("com.google.android.apps.nbu.paisa.user")) {
            Notification notification = sbn.getNotification();
            Bundle extras = notification.extras;
            CharSequence notificationTitle = extras.getCharSequence(Notification.EXTRA_TITLE);
            if (notificationTitle != null) {
                String str1 = notificationTitle.toString();
                if (str1.contains("paid you ₹")) {
                    String str2 = str1.split("₹")[1];
                    String str3 = str2.split("\\.")[0];
                    Intent intent = new Intent("GPayNotification");
                    intent.putExtra("title", str3);
                    sendBroadcast(intent);
                }
            }
        }
//        else {
//            return;
//        }
    }

}
//net.one97.paytm
//com.phonepe.app
