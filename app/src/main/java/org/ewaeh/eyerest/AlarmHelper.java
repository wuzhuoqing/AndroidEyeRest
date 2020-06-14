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

    public static void startLockTriggerAlarm(Context context) {
        LockSetting lockSetting = Utils.getLockSetting(context);
        // set a new start minute

        Log.d(Log_Tag, "startAlarm in next " + lockSetting.lockSeconds + " seconds");
        final long beginAt = SystemClock.elapsedRealtime() + lockSetting.lockSeconds * 1000;

        try {
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlertHandlerIntentService.class);
            intent.putExtra(EXTRA_PARAM_SOURCE, "AlarmHelper");
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            PendingIntent pIntent = PendingIntent.getForegroundService(context, 0, intent, 0);
            alarm.set(AlarmManager.ELAPSED_REALTIME, beginAt, pIntent);
            Log.d(Log_Tag, "Alarm has been configured successfully");
        } catch (Exception e) {
            Log.d(Log_Tag, "an exception has ocurred while setting the Alarm...");
            e.printStackTrace();
        }
    }
}