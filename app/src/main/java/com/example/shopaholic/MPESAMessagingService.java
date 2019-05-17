package com.example.shopaholic;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class MPESAMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MPESAMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            handleNow(remoteMessage.getData().toString());

        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }




}
    private void handleNow(String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            int code = data.getInt("code");
            //TODO read more attributes here
            sendNotification(title, message);

            Intent pushNotification = new Intent(LipaNaMpesa.NOTIFICATION);
            pushNotification.putExtra("message", message);
            pushNotification.putExtra("title", title);
            pushNotification.putExtra("code", code);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void sendNotification(String title, String message) {
        Intent intent = new Intent(this, LipaNaMpesa.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.offer)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }
}
