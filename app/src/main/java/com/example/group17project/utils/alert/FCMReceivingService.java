package com.example.group17project.utils.alert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.group17project.utils.model.User;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FCMReceivingService extends FirebaseMessagingService {
  private String channelID;

  @Override
  public void onNewToken(@NonNull String token) {
    super.onNewToken(token);
  }

  @Override
  public void onMessageReceived(@NonNull RemoteMessage message) {
    super.onMessageReceived(message);

    channelID = User.getInstance().getEmail();

    // Extract fields from the notification message.
    final String title = message.getNotification().getTitle();
    final String body = message.getNotification().getBody();

    //getting the data
    final Map<String, String> data = message.getData();
    final String productID = data.get("productID");
    final String productLocation = data.get("productLocation");

    // Create an intent to start activity when the notification is clicked.
    Intent intent = new Intent(this, ViewPushNotificationActivity.class);
    intent.putExtra("title", title);
    intent.putExtra("body", body);
    intent.putExtra("productID", productID);
    intent.putExtra("productLocation", productLocation);

    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 10, intent,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
    NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(this, channelID)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    int id = (int) System.currentTimeMillis();

    NotificationChannel channel = new NotificationChannel(channelID, channelID, NotificationManager.IMPORTANCE_HIGH);
    notificationManager.createNotificationChannel(channel);

    notificationManager.notify(id, notificationBuilder.build());
  }

}
