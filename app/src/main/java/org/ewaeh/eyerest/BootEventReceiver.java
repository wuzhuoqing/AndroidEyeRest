package org.ewaeh.eyerest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootEventReceiver extends BroadcastReceiver {
    private static  final String Log_Tag = BootEventReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // assumes WordService is a registered service
        Log.d(Log_Tag, "BootEventReceiver Getting Intent");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(Log_Tag, "BOOT_COMPLETED action has been received.");
            AlarmHelper.startLockTriggerAlarm(context);
        }
    }
}