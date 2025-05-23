//package com.pay.done;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.ActivityCompat;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.telephony.TelephonyManager;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class LoginPage extends AppCompatActivity {
//    private EditText etMobileNumber;
//    private Button loginBtn;
//    String mob;
//
//    private void getMobileNumber() {
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mob=telephonyManager.getLine1Number();
//        Toast.makeText(this, mob, Toast.LENGTH_SHORT).show();
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_page);
//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if(getSupportActionBar()!=null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //back button in toolbar
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }
//        etMobileNumber=findViewById(R.id.etMobileNumber);
//        String[] permission= new String[]{
//                Manifest.permission.READ_PHONE_NUMBERS
//        };
//        requestPermissions(permission, 102);
//        getMobileNumber();
//        loginBtn=findViewById(R.id.loginBtn);
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String no=etMobileNumber.getText().toString();
//                Long noInt=Long.parseLong(no);
//                Long key= 212121212121L;
//                noInt=noInt-key;
//                String noIntS=Long.toString(noInt);
//                Toast.makeText(LoginPage.this, noIntS, Toast.LENGTH_SHORT).show();
//                if (noIntS.equals(mob)){
//                    Toast.makeText(LoginPage.this, "Login Successfull", Toast.LENGTH_SHORT).show();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            SharedPreferences pref=getSharedPreferences("login", MODE_PRIVATE);
//                            SharedPreferences.Editor editor=pref.edit();
//                            editor.putBoolean("flag", true);
//                            editor.apply();
//                            Intent intent=new Intent(LoginPage.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    }, 3000);
//                }
//                else {
//                    Toast.makeText(LoginPage.this, "You are not a registered user", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int itemId= item.getItemId();
//        if(itemId==android.R.id.home){
//            super.onBackPressed();
//        }
//        else{
//            AlertDialog.Builder helpDialog = new AlertDialog.Builder(LoginPage.this);
//            helpDialog.setTitle("Help & Support");
//            helpDialog.setIcon(R.drawable.ic_launcher_background);
//            helpDialog.setMessage("Do you want to call the developer ");
//            helpDialog.setPositiveButton("Yes, CALL", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent iDial= new Intent(Intent.ACTION_DIAL);
//                    iDial.setData(Uri.parse("tel: +917412009196"));
//                    startActivity(iDial);
//                }
//            });
//            helpDialog.setNegativeButton("No, Don't CALL", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    return;
//                }
//            });
//            helpDialog.show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}