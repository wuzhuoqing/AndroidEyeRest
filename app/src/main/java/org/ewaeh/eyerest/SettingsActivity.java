package org.ewaeh.eyerest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 2323;

    private TextView mOverlayInfoView;
    private Button mOverlayPermButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mOverlayInfoView = this.findViewById(R.id.tv_overlay_info);
        mOverlayPermButton = this.findViewById(R.id.goto_overlay_perm_button);
        mOverlayPermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoOverlayPermSetting();
            }
        });
        if (!Settings.canDrawOverlays(this)) {
            gotoOverlayPermSetting();
        } else {
            mOverlayInfoView.setVisibility(View.GONE);
            mOverlayPermButton.setVisibility(View.GONE);
        }
    }

    private void gotoOverlayPermSetting() {
        //If the draw over permission is not available open the settings screen
        //to grant the permission.
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
    }

    private void initView() {
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, FullscreenService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                } else {
                    mOverlayInfoView.setVisibility(View.GONE);
                    mOverlayPermButton.setVisibility(View.GONE);
                }
            }
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}