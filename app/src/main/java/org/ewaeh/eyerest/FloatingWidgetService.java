package org.ewaeh.eyerest;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class FloatingWidgetService extends Service {
    private static final String Log_Tag = FloatingWidgetService.class.getSimpleName();
    WindowManager windowManager;
    View floatingView;
    View collapsedView;
    View expandedView;
    WindowManager.LayoutParams params ;
    TextView tvTimeLeft;

    public FloatingWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_view, null);

        int layoutFlag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        windowManager.addView(floatingView, params);

        collapsedView = floatingView.findViewById(R.id.Floating_Layout_Collapsed);
        expandedView = floatingView.findViewById(R.id.Floating_Layout_Expanded);
        tvTimeLeft = floatingView.findViewById(R.id.tv_time_left);

        collapsedView.setOnTouchListener(new View.OnTouchListener() {
            int X_Axis, Y_Axis;
            float TouchX, TouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        X_Axis = params.x;
                        Y_Axis = params.y;
                        TouchX = event.getRawX();
                        TouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (TouchX == event.getRawX() && TouchY == event.getRawY()) {
                            if (expandedView.getVisibility() != View.VISIBLE) {
                                long nextAlarmTime = Utils.getNextAlarmTime(FloatingWidgetService.this);
                                String timeLeft = "" + ((nextAlarmTime - System.currentTimeMillis()) / 1000) + " second left";
                                tvTimeLeft.setText(timeLeft);
                                expandedView.setVisibility(View.VISIBLE);
                            } else {
                                expandedView.setVisibility(View.GONE);
                            }
                        }
                        return true;

                    case MotionEvent.ACTION_MOVE:

                        params.x = X_Axis + (int) (event.getRawX() - TouchX);
                        params.y = Y_Axis + (int) (event.getRawY() - TouchY);
                        windowManager.updateViewLayout(floatingView, params);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) windowManager.removeView(floatingView);
    }
}
