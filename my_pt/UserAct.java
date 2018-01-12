package com.example.andrew.my_pt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Time;
import java.util.Date;


public class UserAct extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private PendingIntent pendingIntent;
    AlarmManager alarmManager;
    private TextView useremail;

    private Button buttonLogout;
    private Button buttonUserInfo;
    private Button buttonPdf;
    private Button tracker;
    private Button contact;
    private Button train_video;
    public static String _utfValue = "";
    String localTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        MyBroadcastReciver mAppReceiver = new MyBroadcastReciver();//declare the broadcast reciver
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));


        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");

        date.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

        Calendar currentcalendar = Calendar.getInstance();
        Calendar taretcalendar = Calendar.getInstance();

        taretcalendar.set(Calendar.SECOND, 0);
        taretcalendar.set(Calendar.MINUTE, 31);
        taretcalendar.set(Calendar.HOUR, 0);
        taretcalendar.set(Calendar.AM_PM, Calendar.AM);
        taretcalendar.add(Calendar.DAY_OF_MONTH, 1);

        long intendedTime = taretcalendar.getTimeInMillis();
        long currentTime = currentcalendar.getTimeInMillis();

        Log.d("TARGET" + intendedTime, "");

        Log.d("TCURRENT" + currentTime, "");

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");// set a date format to use it as a string
        String test = sdf.format(taretcalendar.getTime());
        String testx = sdf.format(currentcalendar.getTime());
        String xy = sdf.format(currentLocalTime);

        if (intendedTime >= currentTime) { // check if the intended time compared to the actual time


            int i = 8;
            Intent intent = new Intent(this, MyBroadcastReciver.class);
            pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE); //call the alarm service
            registerReceiver(mAppReceiver, new IntentFilter("MyReceiver"));
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, currentcalendar.getTimeInMillis(), 1000 * 60 * 60 * 12, pendingIntent); //fire alarm and wake device up every 12 hrs
            registerReceiver(mAppReceiver, new IntentFilter("MyReceiver"));
            Intent passintent = new Intent("MyReceiver");//
            passintent.putExtra("string", "HELP");//pass the intent withthe string
            sendBroadcast(passintent);//send the intent to the broadcast reciver

        }
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginAct.class));
        }

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();


        useremail = (TextView) findViewById(R.id.useremail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonUserInfo = (Button) findViewById(R.id.buttonUserInfo);
        buttonPdf = (Button) findViewById(R.id.buttonPdf);
        tracker = (Button) findViewById(R.id.tracker);
        contact = (Button) findViewById(R.id.contact_trainer);
        train_video = (Button) findViewById(R.id.training_video);

        useremail.setText("Welcome" + currentuser.getEmail());
        buttonLogout.setOnClickListener(this);
        buttonUserInfo.setOnClickListener(this);
        buttonPdf.setOnClickListener(this);
        tracker.setOnClickListener(this);
        contact.setOnClickListener(this);
        train_video.setOnClickListener(this);


    }


    @Override
    // Check for button clicks
    public void onClick(View v) {
        if (v == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginAct.class));//start the activity
        } else if (v == buttonUserInfo) {
            Log.d("HIT", "METHOD");
            startActivity(new Intent(this, userinfo.class));//start the activity
        } else if (v == buttonPdf) {
            startActivity(new Intent(this, pdf.class));//start the activity

        } else if (v == tracker) {
            //Toast.makeText(getApplicationContext(),"TRACKER HIT",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, tracker.class));//start the activity
        } else if (v == contact) {

            startActivity(new Intent(this, contact.class));//start the activity

        } else if (v == train_video) {

            startActivity(new Intent(this, training_videos.class));//start the activity
        }

    }

}
