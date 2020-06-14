package org.ewaeh.eyerest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class AlarmHelper {
    private static final String Log_Tag = AlarmHelper.class.getSimpleName();

    public static void startAlarm(Context context, boolean restartNew) {
        LockSetting lockSetting = Utils.getLockSetting(context);
        int remainMinute = lockSetting.lockMinutes;
        int currentMinute = Utils.getCurrentMinute();
        if (!restartNew) {
            remainMinute = lockSetting.lockMinutes - (currentMinute - lockSetting.startingMinute);
            if (remainMinute < 0) {
                remainMinute = 0;
            }
        } else {
            // set a new start minute
            Log.d(Log_Tag, "setLockStartMinute to " + currentMinute);
            Utils.setLockStartMinute(context, currentMinute);
        }

        Log.d(Log_Tag, "startAlarm next minute " + remainMinute);
        final long beginAt = SystemClock.elapsedRealtime() + remainMinute * 60 * 1000;

        try {
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlertHandlerIntentService.class);
            PendingIntent pIntent = PendingIntent.getService(context, 0, intent, 0);
            alarm.set(AlarmManager.ELAPSED_REALTIME, beginAt, pIntent);
            Log.d(Log_Tag, "Alarm has been configured successfully");
        } catch (Exception e) {
            Log.d(Log_Tag, "an exception has ocurred while setting the Alarm...");
            e.printStackTrace();
        }
    }
}
