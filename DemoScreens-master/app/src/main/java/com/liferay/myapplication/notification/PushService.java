package com.liferay.myapplication.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import com.liferay.mobile.screens.push.AbstractPushService;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.myapplication.R;
import com.liferay.myapplication.activities.MapActivity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class PushService extends AbstractPushService {

	public static final int NOTIFICATION_ID = 2;
	public static final String CHANNEL_ID = "CHANNEL_ID_1";
	public static final String CHANNEL_NAME = "CHANNEL_NAME_1";

	public void createGlobalNotification(String title, String description, Context context) {
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder builder;

		NotificationManager notificationManager =
			(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder = buildNotificationO(context, notificationManager);
		} else {
			builder = buildNotificationPreO(context);
		}

		builder.setContentTitle(title)
			.setContentText(description)
			.setSmallIcon(R.mipmap.ic_launchertrans)
			.setSmallIcon(R.drawable.notification_icon);

		builder.setContentIntent(
			createPendingIntentForNotifications(context));

		Notification notification = builder.build();

		notificationManager.notify(NOTIFICATION_ID, notification);
	}

	private NotificationCompat.Builder buildNotificationPreO(Context context) {
		NotificationCompat.Builder builder;
		builder = new NotificationCompat.Builder(context);
		builder.setAutoCancel(true)
			.setVibrate(new long[] { 2000, 1000, 2000, 1000 });
		return builder;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	@NonNull
	private NotificationCompat.Builder buildNotificationO(Context context,
		NotificationManager notificationManager) {
		NotificationCompat.Builder builder;NotificationChannel
			channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
			NotificationManager.IMPORTANCE_DEFAULT);

		channel.setVibrationPattern(new long[] { 2000, 1000, 2000, 1000 });

		notificationManager.createNotificationChannel(channel);

		builder = new NotificationCompat.Builder(context, CHANNEL_ID);
		return builder;
	}

	private PendingIntent createPendingIntentForNotifications(Context context) {
		Intent resultIntent = new Intent(context, MapActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addNextIntent(resultIntent);
		return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	protected void processJSONNotification(final JSONObject json) throws JSONException {

		LiferayLogger.e("Push notification!" + json.toString());

		String title =
			json.has("title") ? json.getString("title") : getString(R.string.notification_title);
		String body = json.has("notificationMessage") ? json.getString("notificationMessage")
				: getString(R.string.notification_description);

		createGlobalNotification(title, body, this);
	}
}
