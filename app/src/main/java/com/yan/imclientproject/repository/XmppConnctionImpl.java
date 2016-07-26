package com.yan.imclientproject.repository;

import com.yan.imclientproject.Common.UtilValue;
import com.yan.imclientproject.Common.ConfigXmpp;

import org.jivesoftware.smack.AbstractConnectionListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/19.
 */
public class XmppConnctionImpl implements IXmppConnction {

    private XMPPTCPConnection mXmpptcpConnection;
    private String account, password;
    private AbstractConnectionListener connectionListener;


    public XmppConnctionImpl(String account, String password) {
        mXmpptcpConnection = new XMPPTCPConnection(initXMPPTCPConnectionConfiguration());
        this.account = account;
        this.password = password;

    }

    public XmppConnctionImpl() {
        mXmpptcpConnection = new XMPPTCPConnection(initXMPPTCPConnectionConfiguration());

    }

    @Override
    public boolean connect() {
        if (mXmpptcpConnection.isConnected()) {
            return true;
        } else {
            try {
                mXmpptcpConnection.connect();
                if (connectionListener != null)
                    mXmpptcpConnection.addConnectionListener(connectionListener);
                return true;
            } catch (SmackException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    private XMPPTCPConnectionConfiguration initXMPPTCPConnectionConfiguration() {
        return XMPPTCPConnectionConfiguration.builder()
                .setHost(ConfigXmpp.SERVER_IP)
                .setPort(ConfigXmpp.SERVER_PORT)
                .setServiceName(ConfigXmpp.SERVER_NAME)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setCompressionEnabled(true)
                .setDebuggerEnabled(true)
                .build();
    }

    @Override
    public boolean login(String account, String password) {
        return loginCommon(account, password);
    }

    private boolean loginCommon(String accountTemp, String passwordTemp) {

        if (UtilValue.isEmpty(accountTemp) || UtilValue.isEmpty(passwordTemp))
            return false;

        if (!mXmpptcpConnection.isConnected()) {
            try {
                mXmpptcpConnection.connect();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        if (mXmpptcpConnection.isAuthenticated()) {
            return true;
        } else {
            try {
                mXmpptcpConnection.login(accountTemp, passwordTemp, ConfigXmpp.LOGIN_RESOURSE);
                getRosters();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean login() {
        return loginCommon(account, password);
    }

    Roster roster;

    private void getRosters() {
        roster = Roster.getInstanceFor(mXmpptcpConnection);

        if (mRosterListener != null) {
            roster.addRosterListener(mRosterListener);

        }

        if (roster != null && roster.getEntries().size() > 0) {
            for (RosterEntry entry : roster.getEntries()) {
                entry.getName();
                entry.getUser();
            }
        }
    }

    ChatManagerListener chatManagerListener;

    public void setChatManagerListener() {
        ChatManager.getInstanceFor(mXmpptcpConnection).addChatListener(chatManagerListener);
    }

    @Override
    public boolean disconnect() {
        if (mXmpptcpConnection != null && mXmpptcpConnection.isConnected()) {
            mXmpptcpConnection.disconnect();
        }
        return true;
    }

    public void setConnectionListener(AbstractConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    RosterListener mRosterListener;

    public void setmRosterListener(RosterListener mRosterListener) {
        this.mRosterListener = mRosterListener;
    }


}
