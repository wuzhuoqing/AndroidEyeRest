package org.ewaeh.eyerest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class MainActivity extends AppCompatActivity {

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 2323;

    private EditText mPassCodeText;
    private Button mTestButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPassCodeText = this.findViewById(R.id.editInputPasscode);
        mTestButton = this.findViewById(R.id.passcode_ok_button);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passCodeExpected = Utils.getLockPasscode(MainActivity.this);
                String inputPasscode = mPassCodeText.getText().toString();
                mPassCodeText.setText("");
                if (passCodeExpected.equals(inputPasscode)) {
                    startSettingActivity();
                }
            }
        });

        String passCodeExpected = Utils.getLockPasscode(MainActivity.this);
        if (passCodeExpected.equals("")) {
            startSettingActivity();
        }
    }

    private void startSettingActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        setContentView(R.layout.main_activity);
    }
}