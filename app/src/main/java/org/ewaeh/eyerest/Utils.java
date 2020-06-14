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

    public static double getDoubleHelper(String num, double defaultNum) {
        try {
            return Double.parseDouble(num);
        }
        catch (Exception ex) {
            return defaultNum;
        }
    }

    public static LockSetting getLockSetting(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context /* Activity context */);
        LockSetting lockSetting = new LockSetting();
        lockSetting.lockSeconds = (int) (getDoubleHelper(sharedPreferences.getString("screen_lock_interval_minutes", "21"), 21) * 60 + .5);
        lockSetting.restSeconds = getIntHelper(sharedPreferences.getString("eye_rest_second", "31"), 31);
        lockSetting.countDownRefreshSecond = getIntHelper(sharedPreferences.getString("count_down_refresh_second", "11"), 11);
        // lockSetting.startingLockCheckTime = sharedPreferences.getLong("starting_lock_check", 0);
        return lockSetting;
    }
}
