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

    public static String getLockPasscode(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context /* Activity context */);
        String passcode = sharedPreferences.getString("eye_lock_passcode", "");
        return passcode;
    }

    public static long getNextAlarmTime(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context /* Activity context */);
        Long nextAlarmTime = sharedPreferences.getLong("eye_lock_nextAlarm", 0);
        return nextAlarmTime;
    }

    public static void setNextAlarmTime(Context context, long nextAlarmTime) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context /* Activity context */);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("eye_lock_nextAlarm", nextAlarmTime);
        editor.apply();
    }

    public static LockSetting getLockSetting(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context /* Activity context */);
        LockSetting lockSetting = new LockSetting();
        lockSetting.lockIntervalSeconds = (int) (getDoubleHelper(sharedPreferences.getString("screen_lock_interval_minutes", "21"), 21) * 60 + .5);
        // hack don't allow less than 5 second lock
        if (lockSetting.lockIntervalSeconds <= 0) {
            lockSetting.lockIntervalSeconds = 0;
        } else if (lockSetting.lockIntervalSeconds < 5) {
            lockSetting.lockIntervalSeconds = 5;
        }
        lockSetting.restSeconds = getIntHelper(sharedPreferences.getString("eye_rest_second", "20"), 20);
        lockSetting.countDownRefreshSecond = getIntHelper(sharedPreferences.getString("count_down_refresh_second", "11"), 11);
        lockSetting.eyeLookAwayNum = getIntHelper(sharedPreferences.getString("eye_look_alway_num", "20"), 20);
        lockSetting.customReminder = sharedPreferences.getString("custom_reminder_string", "");
        lockSetting.useOverlay = sharedPreferences.getBoolean("eye_look_use_overlay_widget", false);
        // lockSetting.startingLockCheckTime = sharedPreferences.getLong("starting_lock_check", 0);
        return lockSetting;
    }
}
