package com.app.dbrah_Provider.notifications;

import static android.content.Intent.getIntent;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.dbrah_Provider.tags.Tags;


public class BroadcastCancelNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (manager != null) {
            manager.cancel(intent.getIntExtra("not",0));
        }
    }
}
