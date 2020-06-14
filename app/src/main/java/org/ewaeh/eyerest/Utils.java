package org.ewaeh.eyerest;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Utils {

    public static int getIntHelper(String num, int defaultNum) {
        try {
            return Integer.parseInt(num);
        }
        catch (Exception ex) {
            return defaultNum;
        }
    }

    public static int getCurrentMinute() {
        return (int) (System.currentTimeMillis() / 1000 / 60);
    }

    public static void setLockStartMinute(Context context, int minute) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt("starting_minute", minute);
    }

    public static LockSetting getLockSetting(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context /* Activity context */);
        LockSetting lockSetting = new LockSetting();
        lockSetting.lockMinutes = getIntHelper(sharedPreferences.getString("screen_lock_interval_minutes", "21"), 21);
        lockSetting.restSeconds = getIntHelper(sharedPreferences.getString("eye_rest_second", "31"), 31);
        lockSetting.countDownRefreshSecond = getIntHelper(sharedPreferences.getString("count_down_refresh_second", "11"), 11);
        lockSetting.startingMinute = sharedPreferences.getInt("starting_minute", 0);
        return lockSetting;
    }
}
