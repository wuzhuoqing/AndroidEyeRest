package org.ewaeh.eyerest;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class FullscreenService extends Service {
    private static final String Log_Tag = FullscreenService.class.getSimpleName();

    private WindowManager mWindowManager;
    private View mFloatingWidget;

    private View mControlsView;

    private TextView mTimeInfoView;
    private TextView mSecondRemainView;
    private Button mCloseButton;

    private LockSetting lockSetting;

    public FullscreenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initFullScreenView();
    }

    private void initFullScreenView() {
        Log.d(Log_Tag, "initFullScreenView");
        // setContentView(R.layout.activity_fullscreen);
        mFloatingWidget = LayoutInflater.from(this).inflate(R.layout.activity_fullscreen, null);
        mFloatingWidget.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingWidget, params);

        mControlsView = mFloatingWidget.findViewById(R.id.bottom_content_controls);
        mTimeInfoView = mFloatingWidget.findViewById(R.id.time_info);
        mSecondRemainView = mFloatingWidget.findViewById(R.id.tvSecondRemain);
        mCloseButton = mFloatingWidget.findViewById(R.id.close_button);

        lockSetting = Utils.getLockSetting(this);
        final String msgTemplate = this.getString(R.string.time_info);
        @SuppressLint("StringFormatMatches") String message = String.format(msgTemplate, String.format("%.1f", lockSetting.lockSeconds / 60.0), lockSetting.restSeconds, lockSetting.countDownRefreshSecond);
        mTimeInfoView.setText(message);

        final String secondRemainTemplate = this.getString(R.string.second_remain);
        @SuppressLint("StringFormatMatches") String initSecondRemain = String.format(secondRemainTemplate, lockSetting.restSeconds);
        mSecondRemainView.setText(initSecondRemain);

        mCloseButton.setText(this.getString(R.string.close_button));
        mCloseButton.setEnabled(false);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start next round
                Log.d(Log_Tag, "finish clicked start next round");
                AlarmHelper.startLockTriggerAlarm(FullscreenService.this);
                mWindowManager.removeView(mFloatingWidget);
                FullscreenService.this.stopSelf();
            }
        });
        CountDownTimer timer = new CountDownTimer(lockSetting.restSeconds * 1000, lockSetting.countDownRefreshSecond * 1000) {

            public void onTick(long millisUntilFinished) {
                @SuppressLint("StringFormatMatches") String secondRemain = String.format(secondRemainTemplate, millisUntilFinished / 1000);
                mSecondRemainView.setText(secondRemain);
            }

            public void onFinish() {
                Log.d(Log_Tag, "timer finish. can close button now");
                @SuppressLint("StringFormatMatches") String secondRemain = String.format(secondRemainTemplate, 0);
                mSecondRemainView.setText(secondRemain);
                mCloseButton.setText(FullscreenService.this.getString(R.string.close_button_ready));
                mCloseButton.setEnabled(true);
            }
        }.start();
    }
}
