package com.victoriaserect.fappybird;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.List;
import java.util.Objects;

public class AndroidLauncher extends AndroidApplication {

	final String NOTIFICATION_FOR_UPDATE = "update";

	static class ContextCommandsImpl {
		private Context context;

		ContextCommandsImpl(Context context) {
			this.context = context;
		}

		void openAppInPlayStore() {
			String appId = context.getPackageName();
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + appId));
			boolean marketFound = false;

			// find all applications able to handle the intent
			final List<ResolveInfo> otherApps = context.getPackageManager()
					.queryIntentActivities(intent, 0);
			for (ResolveInfo otherApp: otherApps) {
				// look for Google Play application
				if (otherApp.activityInfo.applicationInfo.packageName
						.equals("com.android.vending")) {

					ActivityInfo otherAppActivity = otherApp.activityInfo;
					ComponentName componentName = new ComponentName(
							otherAppActivity.applicationInfo.packageName,
							otherAppActivity.name
					);
					// make sure it does NOT open in the stack of your activity
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// task reparenting if needed
					intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					// if the Google Play was already open in a search result
					// this make sure it still go to the app page you requested
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// this make sure only the Google Play app is allowed to
					// intercept the intent
					intent.setComponent(componentName);
					context.startActivity(intent);
					marketFound = true;
					break;

				}
			}

			// if GP not present on device, open web browser
			if (!marketFound) {
				Intent webIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://play.google.com/store/apps/details?id=" + appId));
				context.startActivity(webIntent);
			}
		}

	}

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		FirebaseConnector firebaseConnector = new FirebaseConnentorImpl(new ContextCommandsImpl(this));
		//if the user open the app from a notification, we check the extra data in it
		//if the click_action referes to the update purpose, open play store to update the application
		if (checkIntentsClickAction(getIntent())) {
			new ContextCommandsImpl(this).openAppInPlayStore();
		}
		initialize(new FappyBirdGame(firebaseConnector), config);

	}

	public boolean checkIntentsClickAction(Intent intent) {
		if (intent.hasExtra("click_action")) {
			return Objects.equals(intent.getStringExtra("click_action"), NOTIFICATION_FOR_UPDATE);
		}
		return false;
	}

}
