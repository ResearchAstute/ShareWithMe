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
System.err.println("Test point 1 ...");
	}

	/**
	 * @param context
	 * @param intent
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
System.err.println("Test point 2 ...");
		context.startService(new Intent(context, NewMessageCheckService.class));
System.err.println("Test point 3 ...");
	}
}
