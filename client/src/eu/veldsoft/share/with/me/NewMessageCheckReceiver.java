package eu.veldsoft.share.with.me;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author
 */
public class NewMessageCheckReceiver extends BroadcastReceiver {
	/**
	 * 
	 */
	public NewMessageCheckReceiver() {
	}

	/**
	 * @param context
	 * @param intent
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, NewMessageCheckService.class));
	}
}
