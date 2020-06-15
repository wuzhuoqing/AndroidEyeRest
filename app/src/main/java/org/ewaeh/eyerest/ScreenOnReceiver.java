package org.ewaeh.eyerest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenOnReceiver extends BroadcastReceiver {
    private static  final String Log_Tag = ScreenOnReceiver.class.getSimpleName();

    private FullscreenActivity parent;
    public ScreenOnReceiver(FullscreenActivity parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // assumes WordService is a registered service
        Log.d(Log_Tag, "ScreenOnReceiver Getting Intent");
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            Log.d(Log_Tag, "ScreenOnReceiver action has been received.");
            if (parent != null) {
                parent.onScreenOn();
            }
        }
    }
}