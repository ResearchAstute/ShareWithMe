package eu.veldsoft.share.with.me;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author
 */
public class NewMessageCheckService extends IntentService {
	/**
	 * 
	 */
	private void setupAlarm() {
		/*
		 * Do not set if it is already there.
		 */
		if (PendingIntent.getBroadcast(this, Util.ALARM_REQUEST_CODE,
				new Intent(getApplicationContext(), NewMessageCheckReceiver.class),
				PendingIntent.FLAG_NO_CREATE) != null) {
			return;
		}

		// TODO It will be better to parameterize weak-up interval.
		((AlarmManager) this.getSystemService(Context.ALARM_SERVICE)).setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), AlarmManager.INTERVAL_HALF_HOUR,
				PendingIntent.getBroadcast(this, Util.ALARM_REQUEST_CODE,
						new Intent(getApplicationContext(), NewMessageCheckReceiver.class),
						PendingIntent.FLAG_UPDATE_CURRENT));
	}

	/**
	 * 
	 * @param name
	 */
	public NewMessageCheckService() {
		super("NewMessageCheckService");
	}

	/**
	 * 
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		/*
		 * Check alarm.
		 */
		setupAlarm();

		/*
		 * Release wake-up lock.
		 */
		if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {
			WakefulBroadcastReceiver.completeWakefulIntent(intent);
		}

		/*
		 * Check for new message.
		 */
		(new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				String host = "";
				try {
					host = getPackageManager().getApplicationInfo(NewMessageCheckService.this.getPackageName(),
							PackageManager.GET_META_DATA).metaData.getString("host");
				} catch (NameNotFoundException exception) {
					System.err.println(exception);
					return null;
				}

				String script = "";
				try {
					script = getPackageManager().getServiceInfo(
							new ComponentName(NewMessageCheckService.this, NewMessageCheckService.this.getClass()),
							PackageManager.GET_SERVICES | PackageManager.GET_META_DATA).metaData.getString("script");
				} catch (NameNotFoundException exception) {
					System.err.println(exception);
					return null;
				}

				SharedPreferences preference = PreferenceManager
						.getDefaultSharedPreferences(NewMessageCheckService.this);

				String instanceHash = preference.getString(Util.SHARED_PREFERENCE_INSTNCE_HASH_CODE_KEY, "");

				// TODO Check in SQLite what is the last message hash.
				String lastMessageHash = "";

				// TODO device-hash, last-message-hash
				// TODO Local SQLite database.
				// TODO Remote MySQL select queries.

				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://" + host + "/" + script);

				JSONObject json = new JSONObject();
				try {
					json.put(Util.JSON_INSTNCE_HASH_CODE_KEY, instanceHash);
					json.put(Util.JSON_LAST_MESSAGE_HASH_CODE_KEY, lastMessageHash);
				} catch (JSONException exception) {
					System.err.println(exception);
				}

				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("request", json.toString()));
				try {
					post.setEntity(new UrlEncodedFormEntity(pairs));
				} catch (UnsupportedEncodingException exception) {
					System.err.println(exception);
				}

				try {
					HttpResponse response = client.execute(post);
					// TODO If there is a new message open message read activity (by sending
					// message hash as parameter).
				} catch (ClientProtocolException exception) {
					System.err.println(exception);
				} catch (IOException exception) {
					System.err.println(exception);
				}

				return null;
			}
		}).execute();
	}

}
