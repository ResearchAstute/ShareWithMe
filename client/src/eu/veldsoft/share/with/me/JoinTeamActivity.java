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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author Ventsislav Medarov
 */
public class JoinTeamActivity extends Activity {

	/**
	 * 
	 * @param savedInstanceState
	 * 
	 * @author Ventsislav Medarov
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_team);

		findViewById(R.id.join_team_send).setOnClickListener(
				/**
				 * 
				 */
				new View.OnClickListener() {
					/**
					 * 
					 */
					@Override
					public void onClick(View view) {
						(new AsyncTask<Void, Void, Void>() {
							@Override
							protected Void doInBackground(Void... params) {
								String host = "";
								try {
									host = getPackageManager().getActivityInfo(JoinTeamActivity.this.getComponentName(),
											PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA).metaData
													.getString("host");
								} catch (NameNotFoundException exception) {
									// TODO Better exception notification should
									// be applied.
									return null;
								}

								SharedPreferences preference = PreferenceManager
										.getDefaultSharedPreferences(JoinTeamActivity.this);

								String instanceHash = preference.getString(Util.SHARED_PREFERENCE_INSTNCE_HASH_CODE_KEY,
										"");
								String names = ((EditText) findViewById(R.id.join_team_names)).getText().toString();
								String email = ((EditText) findViewById(R.id.join_team_email)).getText().toString();
								String phone = ((EditText) findViewById(R.id.join_team_phone)).getText().toString();

								HttpClient client = new DefaultHttpClient();
								HttpPost post = new HttpPost(host);

								JSONObject json = new JSONObject();
								try {
									json.put(Util.JSON_INSTNCE_HASH_CODE_KEY, instanceHash);
									json.put(Util.JSON_EMAIL_KEY, email);
									json.put(Util.JSON_PHONE_KEY, phone);
								} catch (JSONException exception) {
									// TODO Do better exception handling.
								}

								List<NameValuePair> pairs = new ArrayList<NameValuePair>();
								pairs.add(new BasicNameValuePair("request", json.toString()));
								try {
									post.setEntity(new UrlEncodedFormEntity(pairs));
								} catch (UnsupportedEncodingException e) {
								}

								try {
									HttpResponse response = client.execute(post);
								} catch (ClientProtocolException excetpion) {
								} catch (IOException exception) {
								}

								return null;
							}
						}).execute();

						JoinTeamActivity.this.finish();
					}
				});
	}
}
