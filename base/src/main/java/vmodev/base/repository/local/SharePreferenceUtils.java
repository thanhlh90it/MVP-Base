package vmodev.base.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by thanhle on 11/1/16.
 */
public class SharePreferenceUtils {

    public static final String APP_SHARE_PRE = "APP_SHARE_PRE";
    private static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";

    public static void savePreferences(Context activity, String key, String value) {
        SharedPreferences sp = activity.getSharedPreferences(SharePreferenceUtils.APP_SHARE_PRE, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readPreferences(Context activity, String key, String defaultValue) {
        SharedPreferences sp = activity.getSharedPreferences(SharePreferenceUtils.APP_SHARE_PRE, Context.MODE_APPEND);
        return sp.getString(key, defaultValue);
    }

    public static void removePreference(Context activity, String string) {
        SharedPreferences sp = activity.getSharedPreferences(string, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(string);
        editor.commit();
    }

    public static void saveEmailPassword(Context context, String email, String password) {
        SharedPreferences sp = context.getSharedPreferences(APP_SHARE_PRE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public static String getEmail(Context context) {
        return context.getSharedPreferences(APP_SHARE_PRE, Context.MODE_PRIVATE).getString(EMAIL, null);
    }

    public static String getPassword(Context context) {
        return context.getSharedPreferences(APP_SHARE_PRE, Context.MODE_PRIVATE).getString(PASSWORD, null);
    }
}

