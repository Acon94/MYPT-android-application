package com.example.andrew.my_pt;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Andrew on 10/12/2016.
 */

public class MyBroadcastReciver extends BroadcastReceiver {

    MediaPlayer mymedia;


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

        String sample = intent.getExtras().getString("string");
        String mydata = sample;
        String value = UserAct._utfValue ;

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("REMINDER")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("from"))
                .setContentText("FILL OUT TODAYS DIET ")
                .setAutoCancel(true)
                .setSound(defaultSoundUri);


        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            notificationBuilder.setContentIntent(pendingIntent);
        }


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());


        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

    }
}




