package com.yan.imclientproject.di.model;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.thread.ThreadEnforcer;
import com.yan.imclientproject.app.MApplication;
import com.yan.imclientproject.app.PreferencesManager;
import com.yan.imclientproject.di.scope.PerApp;
import com.yan.imclientproject.repository.XmppConnctionImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/7/19.
 */
@Module
public class AppModel {
    private MApplication context;
    private PreferencesManager preferencesManager;

    public AppModel(MApplication context) {
        this.context = context;
        preferencesManager = new PreferencesManager(context);
    }

    @Provides
    @PerApp
    Context getContext() {
        return context;
    }

    @Provides
    @PerApp
    MApplication getMApplication() {
        return context;
    }

    @Provides
    @PerApp
    XmppConnctionImpl getXmppConnctionImpl() {
        return new XmppConnctionImpl(preferencesManager);
    }

    @Provides
    @PerApp
    Toast getToast() {
        return Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    @Provides
    @PerApp
    PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

}
