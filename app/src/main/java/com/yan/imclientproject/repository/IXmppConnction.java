package com.yan.imclientproject.repository;

import org.jivesoftware.smack.AbstractConnectionListener;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.roster.RosterListener;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface IXmppConnction {

    boolean connect();

    boolean login(String account, String password);

    boolean login();

    boolean disconnect();

    void setmRosterListener(RosterListener mRosterListener);

    void setConnectionListener(AbstractConnectionListener connectionListener);

    void setChatListener(ChatManagerListener chatListener);

}
