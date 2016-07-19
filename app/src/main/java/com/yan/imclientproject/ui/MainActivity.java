package com.yan.imclientproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yan.imclientproject.R;
import com.yan.imclientproject.ui.login.module.view.FragmentLogin;
import com.yan.imclientproject.util.LeakManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.view_content, FragmentLogin.instance()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeakManager.fixInputMethodManagerLeak(this);

    }
}
