package com.yan.imclientproject.di.model;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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
    private Context context;

    public AppModel(Application context) {
        this.context = context;
    }

    @Provides
    @PerApp
    Context getContext() {
        return context;
    }

    @Provides
    @PerApp
    XmppConnctionImpl getXmppConnctionImpl() {
        return new XmppConnctionImpl();
    }

    @Provides
    @PerApp
    Toast getToast() {
        return Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    @Provides
    @PerApp
    PreferencesManager getPreferencesManager() {
        return new PreferencesManager(context);
    }

}
