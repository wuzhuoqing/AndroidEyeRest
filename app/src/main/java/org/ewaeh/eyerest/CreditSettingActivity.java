package org.ewaeh.eyerest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreditSettingActivity extends AppCompatActivity {

    private Button mCloseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_settings);
        mCloseButton = this.findViewById(R.id.close_credit_button);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreditSettingActivity.this.finish();
            }
        });
    }
}