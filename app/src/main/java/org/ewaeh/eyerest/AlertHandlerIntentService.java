package org.ewaeh.eyerest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlertHandlerIntentService extends IntentService {
    private static final String Log_Tag = AlertHandlerIntentService.class.getSimpleName();
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
//    private static final String ACTION_FOO = "org.ewaeh.eyerest.action.FOO";
//    private static final String ACTION_BAZ = "org.ewaeh.eyerest.action.BAZ";
//
//    private static final String EXTRA_PARAM1 = "org.ewaeh.eyerest.extra.PARAM1";
//    private static final String EXTRA_PARAM2 = "org.ewaeh.eyerest.extra.PARAM2";

    public AlertHandlerIntentService() {
        super("AlertHandlerIntentService");
    }

//    /**
//     * Starts this service to perform action Foo with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    public static void startActionFoo(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, AlertHandlerIntentService.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }
//
//    /**
//     * Starts this service to perform action Baz with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, AlertHandlerIntentService.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(Log_Tag, "onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
//            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            } else if (ACTION_BAZ.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
            final String alarmExtra = intent.getStringExtra(AlarmHelper.EXTRA_PARAM_SOURCE);
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (pm.isInteractive()) {
                Log.d(Log_Tag, "onHandleIntent lock screen");
                Intent lockScreenIntent = new Intent(this, FullscreenService.class);
                startForegroundService(lockScreenIntent);
            } else {
                Log.d(Log_Tag, "isInteractive false. just schedule next one");
                AlarmHelper.startLockTriggerAlarm(this);
            }
        }
    }
//
//    /**
//     * Handle action Foo in the provided background thread with the provided
//     * parameters.
//     */
//    private void handleActionFoo(String param1, String param2) {
//        // TODO: Handle action Foo
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    /**
//     * Handle action Baz in the provided background thread with the provided
//     * parameters.
//     */
//    private void handleActionBaz(String param1, String param2) {
//        // TODO: Handle action Baz
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
