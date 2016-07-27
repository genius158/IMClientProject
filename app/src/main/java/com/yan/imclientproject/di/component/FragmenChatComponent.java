package com.yan.imclientproject.di.component;

import com.yan.imclientproject.di.model.FragmentChatModel;
import com.yan.imclientproject.di.scope.PerFragment;
import com.yan.imclientproject.ui.chat.FragmentChat;

import dagger.Component;

/**
 * Created by Administrator on 2016/7/19.
 */
@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentChatModel.class)
public interface FragmenChatComponent {
    void inject(FragmentChat chatFragment);
}
