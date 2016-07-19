package com.yan.imclientproject.ui.login.di.component;

import com.yan.imclientproject.di.component.AppComponent;
import com.yan.imclientproject.di.scope.PerFragment;
import com.yan.imclientproject.ui.login.module.view.FragmentLogin;

import dagger.Component;

/**
 * Created by Administrator on 2016/7/19.
 */
@PerFragment
@Component(dependencies = AppComponent.class)
public interface FrgmentLoginComponent {
    void inject(FragmentLogin fragmentLogin);
}
