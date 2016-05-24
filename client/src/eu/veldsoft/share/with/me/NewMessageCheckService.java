package eu.veldsoft.share.with.me;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import eu.veldsoft.share.with.me.model.Util;
import eu.veldsoft.share.with.me.storage.MessageHistoryDatabaseHelper;

/**
 * 
 * @author
 */
public class NewMessageCheckService extends Service {
	/**
	 * 
	 */
	MessageHistoryDatabaseHelper helper = null;

	/**
	 * 
	 */
	private void setupAlarm() {
System.err.println("Test point 4 ...");
		/*
		 * Do not set if it is already there.
		 */
		if (PendingIntent.getBroadcast(this, Util.ALARM_REQUEST_CODE,
				new Intent(getApplicationContext(), NewMessageCheckReceiver.class),
				PendingIntent.FLAG_NO_CREATE) != null) {
			return;
		}
System.err.println("Test point 5 ...");

		// TODO It will be better to parameterize weak-up interval.
		((AlarmManager) this.getSystemService(Context.ALARM_SERVICE)).setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 60000/*AlarmManager.INTERVAL_HALF_HOUR*/,
				PendingIntent.getBroadcast(this, Util.ALARM_REQUEST_CODE,
						new Intent(getApplicationContext(), NewMessageCheckReceiver.class),
						PendingIntent.FLAG_UPDATE_CURRENT));
System.err.println("Test point 6 ...");
	}

	/**
	 * 
	 * @param name
	 */
	public NewMessageCheckService() {
		// TODO Find better way to give name of the service.
System.err.println("Test point 7 ...");
	}

	/**
	 * 
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int id) {
System.err.println("Test point 8 ...");
		/*
		 * Check alarm.
		 */
		setupAlarm();
System.err.println("Test point 9 ...");

		/*
		 * Release wake-up lock.
		 */
		if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {
			WakefulBroadcastReceiver.completeWakefulIntent(intent);
		}
System.err.println("Test point 10 ...");

		/*
		 * Check for new message.
		 */
		(new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
System.err.println("Test point 11 ...");
				String host = "";
				try {
					host = getPackageManager().getApplicationInfo(NewMessageCheckService.this.getPackageName(),
							PackageManager.GET_META_DATA).metaData.getString("host");
				} catch (NameNotFoundException exception) {
					System.err.println(exception);
					return null;
				}
System.err.println("Test point 12 ...");

				String script = "";
				try {
					script = getPackageManager().getServiceInfo(
							new ComponentName(NewMessageCheckService.this, NewMessageCheckService.this.getClass()),
							PackageManager.GET_SERVICES | PackageManager.GET_META_DATA).metaData.getString("script");
				} catch (NameNotFoundException exception) {
					System.err.println(exception);
					return null;
				}
System.err.println("Test point 13 ...");

				SharedPreferences preference = PreferenceManager
						.getDefaultSharedPreferences(NewMessageCheckService.this);

				String instanceHash = preference.getString(Util.SHARED_PREFERENCE_INSTNCE_HASH_CODE_KEY, "");
System.err.println("Test point 14 ...");

				if(helper == null) {
					return null;
				}
				// TODO Check in SQLite what is the last message hash and take
				// special care when the local SQLite database is empty.
				String lastMessageHash = helper.getLastMessageHash();
System.err.println("Test point 15 ...");

				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://" + host + "/" + script);
System.err.println("Test point 16 ...");

				JSONObject json = new JSONObject();
				try {
					json.put(Util.JSON_INSTNCE_HASH_CODE_KEY, instanceHash);
					json.put(Util.JSON_LAST_MESSAGE_HASH_CODE_KEY, lastMessageHash);
				} catch (JSONException exception) {
					System.err.println(exception);
				}
System.err.println("Test point 17 ...");

				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("new_message_check", json.toString()));
				try {
					post.setEntity(new UrlEncodedFormEntity(pairs));
				} catch (UnsupportedEncodingException exception) {
					System.err.println(exception);
				}
System.err.println("Test point 18 ...");

				try {
					HttpResponse response = client.execute(post);
System.err.println("Test point 19 ...");

					JSONObject result = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));

					String messageHash = result.getString(Util.JSON_MESSAGE_HASH_CODE_KEY);
					String messageRegistered = result.getString(Util.JSON_REGISTERED_KEY);
					boolean messageFound = result.getBoolean(Util.JSON_FOUND_KEY);
System.err.println("Test point 20 ...");

					/*
					 * If there is a new message open message read activity (by
					 * sending message hash as parameter).
					 */
					if (messageHash != "" && messageFound == true) {
System.err.println("Test point 21 ...");
						Intent intent = new Intent(NewMessageCheckService.this, AboutActivity.class);
						intent.putExtra(Util.PARENT_MESSAGE_HASH_KEY, messageHash);
						intent.putExtra(Util.REGISTERED_KEY, messageRegistered);
System.err.println("Test point 22 ...");
						startActivity(intent);
System.err.println("Test point 23 ...");
					}
				} catch (ClientProtocolException exception) {
					System.err.println(exception);
				} catch (IOException exception) {
					System.err.println(exception);
				} catch (JSONException exception) {
					System.err.println(exception);
				}
System.err.println("Test point 24 ...");

				NewMessageCheckService.this.stopSelf();
System.err.println("Test point 29 ...");
				return null;
			}
		}).execute();
		
		return START_NOT_STICKY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
    public void onCreate() {
		super.onCreate();
System.err.println("Test point 25 ...");
		helper = new MessageHistoryDatabaseHelper(this);
System.err.println("Test point 26 ...");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
    public void onDestroy() {
System.err.println("Test point 27 ...");
		if(helper != null) {
			helper.close();
			helper = null;
		}
System.err.println("Test point 28 ...");
		super.onDestroy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return new Binder() {
		    Service getService() {
		        return NewMessageCheckService.this;
		    }
		};
	}
}
