package org.ewaeh.eyerest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class AlarmHelper {
    private static final String Log_Tag = AlarmHelper.class.getSimpleName();
    public static final String EXTRA_PARAM_SOURCE = "org.ewaeh.eyerest.extra.SOURCE";

    private static PendingIntent getAlarmPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmEventReceiver.class);
        intent.setAction(AlarmEventReceiver.ACTION_LOCK_ALARM);
        intent.putExtra(EXTRA_PARAM_SOURCE, "AlarmHelper");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        return pIntent;
    }

    public static void cancelLockTriggerAlarm(Context context) {
        try {
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pIntent = getAlarmPendingIntent(context);
            alarm.cancel(pIntent);
            Log.v(Log_Tag, "Alarm has been cancelled successfully");
        } catch (Exception e) {
            Log.e(Log_Tag, "an exception has occurred while cancel the Alarm...", e);
            e.printStackTrace();
        }
    }

    public static void startLockTriggerAlarm(Context context) {
        LockSetting lockSetting = Utils.getLockSetting(context);
        // set a new start minute

        Log.v(Log_Tag, "startLockTriggerAlarm in next " + lockSetting.lockIntervalSeconds + " seconds");
        if (lockSetting.lockIntervalSeconds <= 0) {
            Log.v(Log_Tag, "not starting startLockTriggerAlarm");
            return;
        }

        final long beginAt = System.currentTimeMillis() + lockSetting.lockIntervalSeconds * 1000;

        try {
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pIntent = getAlarmPendingIntent(context);
            alarm.set(AlarmManager.RTC, beginAt, pIntent);
            Utils.setNextAlarmTime(context, beginAt);
            Log.v(Log_Tag, "Alarm has been configured successfully");
        } catch (Exception e) {
            Log.e(Log_Tag, "an exception has occurred while setting the Alarm...", e);
            e.printStackTrace();
        }
    }
}