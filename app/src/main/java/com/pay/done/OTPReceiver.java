package com.pay.done;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Telephony;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class OTPReceiver extends BroadcastReceiver {
    private TextToSpeech tts;
    private static RecyclerView recyclerView;
    public static ArrayList<NotifyModel> arrContacts = new ArrayList<>();
    public static RecyclerNotifyAdapter adapter;
    public static int i=-1;
    public static int j=-1;
//    public static SharedPreferences.Editor editor;
    public static String sender;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String DatenTime;
    public static final String PREF_NAME = "MyPrefs";
    public static void setRecyclerView(RecyclerView recyclerView) {
        OTPReceiver.recyclerView = recyclerView;
        adapter=new RecyclerNotifyAdapter(recyclerView.getContext(), arrContacts);
    }
    public void speak(Context context, String amount){
        tts = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(new Locale("hi", "IN"));
                    tts.setSpeechRate(1.0f);
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                            0);
                    tts.speak("bank me " + amount + "rupees prapt huye", TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }
    public int storenAddtoRecycleView(SharedPreferences.Editor editor, int i, String amount){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MMM, hh:mm a");
        DatenTime = simpleDateFormat.format(calendar.getTime());
        editor.putInt("i", i);
        editor.putString("amt"+i, amount);
        editor.putString("time"+i, DatenTime);
        editor.apply();
        if (amount!=null){
            arrContacts.add(new NotifyModel("Received Rs "+amount, DatenTime));
            recyclerView.setAdapter(adapter);
            i++;
        }
        return i;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences spref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spref.edit();
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null){
            if (networkInfo.isConnected()){
                if (intent.getAction().equals("PaytmNotification")){
                    String amount = intent.getStringExtra("title");
                    MainActivity.otpValue = amount;
                    i = storenAddtoRecycleView(editor, i, amount);
                    speak(context, amount);
                    Toast.makeText(context, "Online", Toast.LENGTH_SHORT).show();
                }
                if (intent.getAction().equals("PhonePeNotification")){
                    String amount = intent.getStringExtra("title");
                    MainActivity.otpValue = amount;
                    i = storenAddtoRecycleView(editor, j, amount);
                    speak(context, amount);
                    Toast.makeText(context, "Online phone pe", Toast.LENGTH_SHORT).show();
                }
                if (intent.getAction().equals("GPayNotification")){
                    String amount = intent.getStringExtra("title");
                    MainActivity.otpValue = amount;
                    i = storenAddtoRecycleView(editor, i, amount);
                    speak(context, amount);
                    Toast.makeText(context, "Online", Toast.LENGTH_SHORT).show();
                }
//                else {
//                    return;
//                }
            }
        }

        else {
            SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            for (SmsMessage smsMessage : smsMessages) {
                sender = smsMessage.getDisplayOriginatingAddress();
                boolean check = spref.getBoolean("key", false);
                if (!check) {
                    MainActivity.delDialog.show();
                    return;
                }
                if (spref.getString("id", null).equals(sender)) {
                    String message_body = smsMessage.getMessageBody();
                    String getOTP = message_body.split("INR ")[1];
                    String amount = getOTP.split("\\.")[0];
//                    String getOTP = message_body.split("\\.")[1];
//                    String amount = getOTP.split("\\.")[0];
                    if (message_body.contains("credited with Rs") || message_body.contains("received") || message_body.contains("Received") || message_body.contains("RECEIVED") || message_body.contains("credited") || message_body.contains("Credited") || message_body.contains("CREDITED")) {
                        MainActivity.otpValue = amount;
                        i = storenAddtoRecycleView(editor, i, amount);
                        Toast.makeText(context, "Offline", Toast.LENGTH_SHORT).show();
                        speak(context, amount);
                    }
                }
                else {
                    return;
                }
            }
        }
    }
}
