package com.yan.imclientproject.ui;

import android.os.Bundle;

import com.yan.imclientproject.R;
import com.yan.imclientproject.app.BaseActivity;
import com.yan.imclientproject.ui.login.module.view.FragmentLogin;
import com.yan.imclientproject.util.LeakManager;

public class MainActivity extends BaseActivity {

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
