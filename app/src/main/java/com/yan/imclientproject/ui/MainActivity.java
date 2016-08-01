package com.yan.imclientproject.ui;

import android.os.Bundle;

import com.yan.imclientproject.R;
import com.yan.imclientproject.app.BaseActivity;
import com.yan.imclientproject.ui.chat.FragmentChat;
import com.yan.imclientproject.ui.login.FragmentLogin;


public class MainActivity extends BaseActivity implements FragmentChange {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.view_content, FragmentLogin.newInstance(FragmentLogin.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void toFragmentChat() {
        start(FragmentChat.newInstance(FragmentChat.class));
    }


}
