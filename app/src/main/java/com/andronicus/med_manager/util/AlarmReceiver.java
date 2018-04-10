package com.andronicus.med_manager.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.andronicus.med_manager.R;
import com.andronicus.med_manager.medication.MedicationActivity;

/**
 * Created by andronicus on 4/9/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Received!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Alarm received!");
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        Intent notificationIntent = new Intent(context, MedicationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build the notification
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_notification)
                .setContentTitle("MedManager")
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .setContentText("Time to take medication")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_HIGH);
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.setSmallIcon(R.drawable.small_icon);
//            builder.setColor(context.getResources().getColor(R.color.colorPrimary));
//        } else {
//            builder.setSmallIcon(R.drawable.small_icon);
//        }
        //Deliver the notification
        notificationManager.notify(0, notification.build());
    }
}
