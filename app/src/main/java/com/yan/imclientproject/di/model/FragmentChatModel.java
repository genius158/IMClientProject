package com.yan.imclientproject.di.model;

import com.yan.imclientproject.di.scope.PerFragment;
import com.yan.imclientproject.repository.XmppConnctionImpl;

import org.jivesoftware.smack.chat.Chat;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/7/19.
 */
@Module
public class FragmentChatModel {

    String userName;

    public FragmentChatModel(String userName) {
        this.userName = userName;
    }

    @Provides
    @PerFragment
    Chat getChat(XmppConnctionImpl xmppConnction) {
        return xmppConnction.createChat(xmppConnction.getChatJidByUser(userName));
    }

}
