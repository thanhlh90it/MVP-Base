package vmodev.base.utils;

public class LogObj {
	private String tag;
	private boolean showLog;

	public LogObj(String tag, boolean showLog) {
		this.tag = tag;
		this.showLog = showLog;
	}

	public void i(String msg) {
		if (showLog && Log.debug) {
			android.util.Log.i(tag, msg != null ? msg : "Null");
		}
	}

	public void d(String msg) {
		if (showLog && Log.debug) {
			android.util.Log.d(tag, msg != null ? msg : "Null");
		}
	}

	public void w(String msg) {
		if (showLog && Log.debug) {
			android.util.Log.w(tag, msg != null ? msg : "Null");
		}
	}

	public void e(String msg) {
		if (showLog && Log.debug) {
			android.util.Log.e(tag, msg != null ? msg : "Null");
		}
	}

	public void v(String msg) {
		if (showLog && Log.debug) {
			Log.v(tag, msg != null ? msg : "Null");
		}
	}
}
