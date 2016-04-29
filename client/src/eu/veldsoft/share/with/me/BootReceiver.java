package eu.veldsoft.share.with.me;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class BootReceiver extends WakefulBroadcastReceiver  {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, NewMessageCheckService.class));
	}
}
