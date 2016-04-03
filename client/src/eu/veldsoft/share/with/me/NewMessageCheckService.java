package eu.veldsoft.share.with.me;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

public class NewMessageCheckService extends Service {
	/**
	 * 
	 * @author
	 */
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message message) {
		}
	}

	/**
	 * 
	 */
	private ServiceHandler handler;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		HandlerThread thread = new HandlerThread("", Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		handler = new ServiceHandler(thread.getLooper());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int id) {
		Message message = handler.obtainMessage();
		message.arg1 = id;
		handler.sendMessage(message);

		return START_STICKY;
	}
}
