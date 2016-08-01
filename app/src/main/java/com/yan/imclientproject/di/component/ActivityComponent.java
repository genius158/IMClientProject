package com.yan.imclientproject.di.component;

import com.yan.imclientproject.app.BaseActivity;
import com.yan.imclientproject.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/7/28.
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);
}
