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

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		FirebaseConnector firebaseConnector = new FirebaseConnentorImpl();

		// itt faszan hivhato, ez van a metodusban: context.startActivity(webIntent);
		//openAppRating(this);

		//nekem viszont ez a core modul "nyilik ra" az android appra a screenjeivel, valahogy az initializenek nem lehet metodust atadni parameterben? :)
		//a firebase connector is emiatt került be, egy interface implementacio, ami az androidban tud mas libeket kezelni, a coreban az interface metodusok vannak hivva.
		//mivel a coreban a parameterben szereplö Context sem ismert, nem tudom az interfacet sem hasznalni.
		//ami eszembe jutott, az az, hogy az implementacio is extendalhatna az android appot, viszont mar extendal egy masik osztalyt.

		initialize(new FappyBirdGame(firebaseConnector), config);
		// ez meg igy fest:
/*		public class FappyBirdGame extends Game {

			public FirebaseConnector firebaseConnector;

			public FappyBirdGame(FirebaseConnector firebaseConnector) {
				this.firebaseConnector = firebaseConnector;
			}

			@Override
			public void create () {
				this.setScreen(new HomeScreen(this, firebaseConnector));
			}

			*/
	}


	public static void openAppRating(Context context) {
		// you can also use BuildConfig.APPLICATION_ID
		String appId = context.getPackageName();
		Intent rateIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("market://details?id=" + appId));
		boolean marketFound = false;

		// find all applications able to handle our rateIntent
		final List<ResolveInfo> otherApps = context.getPackageManager()
				.queryIntentActivities(rateIntent, 0);
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
				rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// task reparenting if needed
				rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				// if the Google Play was already open in a search result
				//  this make sure it still go to the app page you requested
				rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// this make sure only the Google Play app is allowed to
				// intercept the intent
				rateIntent.setComponent(componentName);
				context.startActivity(rateIntent);
				marketFound = true;
				break;

			}
		}

		// if GP not present on device, open web browser
		if (!marketFound) {
			Intent webIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id="+appId));
			context.startActivity(webIntent);
		}
	}

}
