package com.yan.imclientproject.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.yan.imclientproject.R;
import com.yan.imclientproject.app.BaseActivity;
import com.yan.imclientproject.ui.chat.FragmentChat;
import com.yan.imclientproject.ui.login.FragmentLogin;

import java.util.List;

public class MainActivity extends BaseActivity implements FragmentChange {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.view_content, FragmentLogin.newInstance(FragmentLogin.class))
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void toFragmentChat() {
        fragmentManager.beginTransaction()
                .replace(R.id.view_content, FragmentChat.newInstance(FragmentChat.class))
                .commit();
    }

}
