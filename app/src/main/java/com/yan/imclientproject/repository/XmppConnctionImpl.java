package com.yan.imclientproject.repository;

import org.jivesoftware.smack.AbstractConnectionListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntries;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.util.Collection;

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
                .setHost(XmppConfig.SERVER_IP)
                .setPort(XmppConfig.SERVER_PORT)
                .setServiceName(XmppConfig.SERVER_NAME)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setCompressionEnabled(true)
                .setDebuggerEnabled(true)
                .build();
    }

    @Override
    public boolean login(String account, String password) {

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
                mXmpptcpConnection.login(account, password);

                Roster roster = Roster.getInstanceFor(mXmpptcpConnection);

                if (mRosterListener != null) {
                    roster.addRosterListener(mRosterListener);

                }

                if (roster != null && roster.getEntries().size() > 0) {
                    for (RosterEntry entry : roster.getEntries()) {
                        entry.getName();
                        entry.getUser();
                    }
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean login() {
        if (!mXmpptcpConnection.isConnected()) {
            return false;
        }
        if (mXmpptcpConnection.isAuthenticated()) {
            return true;
        } else {
            try {
                mXmpptcpConnection.login(account, password);
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
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

    MRosterListener mRosterListener;

    public void setmRosterListener(MRosterListener mRosterListener) {
        this.mRosterListener = mRosterListener;
    }

    public class MRosterListener implements RosterListener {
        @Override
        public void entriesAdded(Collection<String> collection) {

        }

        @Override
        public void entriesUpdated(Collection<String> collection) {

        }

        @Override
        public void entriesDeleted(Collection<String> collection) {

        }

        @Override
        public void presenceChanged(Presence presence) {

        }
    }

}
