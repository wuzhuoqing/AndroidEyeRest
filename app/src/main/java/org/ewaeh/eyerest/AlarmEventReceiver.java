package org.ewaeh.eyerest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

public class AlarmEventReceiver extends BroadcastReceiver {
    private static  final String Log_Tag = AlarmEventReceiver.class.getSimpleName();
    public static final String ACTION_LOCK_ALARM = "org.ewaeh.eyerest.action.LOCK_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Log_Tag, "AlarmEventReceiver Getting Intent");
        if (ACTION_LOCK_ALARM.equals(intent.getAction())) {
            final String alarmExtra = intent.getStringExtra(AlarmHelper.EXTRA_PARAM_SOURCE);
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (pm.isInteractive()) {
                Log.d(Log_Tag, "onHandleIntent lock screen");
                Intent lockScreenIntent = new Intent(context, FullscreenService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(lockScreenIntent);
                } else {
                    context.startService(lockScreenIntent);
                }
            } else {
                Log.d(Log_Tag, "isInteractive false. just schedule next one");
                AlarmHelper.startLockTriggerAlarm(context);
            }
        }
    }
}