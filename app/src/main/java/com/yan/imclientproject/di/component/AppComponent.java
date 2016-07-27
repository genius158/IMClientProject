package com.yan.imclientproject.di.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.hwangjr.rxbus.Bus;
import com.yan.imclientproject.app.PreferencesManager;
import com.yan.imclientproject.di.model.AppModel;
import com.yan.imclientproject.di.scope.PerApp;
import com.yan.imclientproject.repository.XmppConnctionImpl;

import dagger.Component;

/**
 * Created by Administrator on 2016/7/19.
 */
@PerApp
@Component(modules = AppModel.class)
public interface AppComponent {
    Context getContext();

    XmppConnctionImpl getXmppConnctionImpl();

    PreferencesManager getPreferencesManager();

    Toast getToast();
}
