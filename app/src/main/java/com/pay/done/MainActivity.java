
package com.pay.done;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static RecyclerView recyclerView;
    public static String otpValue;
    public static ArrayList<NotifyModel> arrContacts = new ArrayList<>();
    public static AlertDialog.Builder delDialog;
    public static SharedPreferences spref;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //back button in toolbar
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
//        Button onlineMode=findViewById(R.id.onlineMode);
//        onlineMode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this, OnlineMode.class);
//                startActivity(intent);
//            }
//        });
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 100);
        }
        Intent serviceIntent = new Intent(this, bService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
        if (!isNotificationServiceEnabled()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
        }

        recyclerView=findViewById(R.id.recyclerNotify);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OTPReceiver.setRecyclerView(recyclerView);
        spref = getSharedPreferences(OTPReceiver.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =spref.edit();
        delDialog = new AlertDialog.Builder(MainActivity.this);
        delDialog.setTitle("Check ID?");
        delDialog.setIcon(R.drawable.ic_launcher_background);
        delDialog.setMessage("Is the sender ID correct: ");
        delDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putBoolean("key", true);
                editor.putString("id", OTPReceiver.sender);
                editor.apply();
            }
        });
        delDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putBoolean("key", false);
                editor.apply();
            }
        });
        for (int j=0;j<=OTPReceiver.i;j++){
            String amount=spref.getString("amt" + j, null);
            String time=spref.getString("time"+j, null);
            if (amount!=null){
                arrContacts.add(new NotifyModel("Received Rs " + amount, time));
            }
        }
        RecyclerNotifyAdapter adapter=new RecyclerNotifyAdapter(this, arrContacts);
        recyclerView.setAdapter(adapter);
        OTPReceiver receiver=new OTPReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("PaytmNotification");
        filter.addAction("PhonePeNotification");
        filter.addAction("GPayNotification");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId= item.getItemId();
        if(itemId==android.R.id.home){
            super.onBackPressed();
        }
        else{
            AlertDialog.Builder helpDialog = new AlertDialog.Builder(MainActivity.this);
            helpDialog.setTitle("Help & Support");
            helpDialog.setIcon(R.drawable.pd_favicon);
            helpDialog.setMessage("Do you want to call the developer ");
            helpDialog.setPositiveButton("Yes, CALL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent iDial= new Intent(Intent.ACTION_DIAL);
                    iDial.setData(Uri.parse("tel: +917412009196"));
                    startActivity(iDial);
                }
            });
            helpDialog.setNegativeButton("No, Don't CALL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            helpDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isNotificationServiceEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}





