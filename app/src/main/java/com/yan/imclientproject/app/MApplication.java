package com.yan.imclientproject.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yan.imclientproject.di.component.AppComponent;
import com.yan.imclientproject.di.component.DaggerAppComponent;
import com.yan.imclientproject.di.model.AppModel;


public class MApplication extends Application {

    private RefWatcher refWatcher;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        appComponent = DaggerAppComponent.builder().appModel(new AppModel(this)).build();
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
