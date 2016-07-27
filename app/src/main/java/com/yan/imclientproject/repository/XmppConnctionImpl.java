package com.yan.imclientproject.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.yan.imclientproject.Common.RxBusAction;
import com.yan.imclientproject.Common.UtilValue;
import com.yan.imclientproject.Common.ConfigXmpp;
import com.yan.imclientproject.app.PreferencesManager;

import org.jivesoftware.smack.AbstractConnectionListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/19.
 */
public class XmppConnctionImpl implements IXmppConnction {

    private XMPPTCPConnection mXmpptcpConnection;
    private String account, password;
    private AbstractConnectionListener connectionListener;
    private RosterListener mRosterListener;

    private PreferencesManager preferencesManager;

    public XmppConnctionImpl(String account, String password) {
        constarctCommon();
        this.account = account;
        this.password = password;
    }

    public XmppConnctionImpl() {
        constarctCommon();
    }

    public XmppConnctionImpl(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
        constarctCommon();
    }

    private void constarctCommon() {
        mXmpptcpConnection = new XMPPTCPConnection(initXMPPTCPConnectionConfiguration());
        Roster.getInstanceFor(mXmpptcpConnection).setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
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
                .setSendPresence(false)
                .setConnectTimeout(30000)
                .setDebuggerEnabled(true)
                .build();
    }

    public String getAccount() {
        return mXmpptcpConnection.getUser();
    }


    public boolean updateUserState(int code) throws SmackException.NotConnectedException {
        if (!mXmpptcpConnection.isConnected()) {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
        try {
            Presence presence;
            switch (code) {
                case 0://设置在线
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.available);
                    mXmpptcpConnection.sendStanza(presence);
                    break;
                case 1://设置Q我吧
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.chat);
                    mXmpptcpConnection.sendStanza(presence);
                    break;
                case 2://设置忙碌
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.dnd);
                    mXmpptcpConnection.sendStanza(presence);
                    break;
                case 3://设置离开
                    presence = new Presence(Presence.Type.available);
                    presence.setMode(Presence.Mode.away);
                    mXmpptcpConnection.sendStanza(presence);
                    break;
                case 4://设置隐身
                    //                Roster roster = connection.getRoster();
                    //                Collection<RosterEntry> entries = roster.getEntries();
                    //                for (RosterEntry entry : entries) {
                    //                    presence = new Presence(Presence.Type.unavailable);
                    //                    presence.setStanzaId(Stanza.ID_NOT_AVAILABLE);
                    //                    presence.setFrom(connection.getUser());
                    //                    presence.setTo(entry.getUser());
                    //                    connection.sendStanza(presence);
                    //                }
                    //                // 向同一用户的其他客户端发送隐身状态
                    //                presence = new Presence(Presence.Type.unavailable);
                    //                presence.setStanzaId(Packet.ID_NOT_AVAILABLE);
                    //                presence.setFrom(connection.getUser());
                    //                presence.setTo(StringUtils.parseBareAddress(connection.getUser()));
                    //                connection.sendStanza(presence);
                    break;
                case 5://设置离线
                    presence = new Presence(Presence.Type.unavailable);
                    mXmpptcpConnection.sendStanza(presence);
                    break;
                default:
                    break;
            }
            return true;
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }


    private class MChatManagerListener implements ChatManagerListener {
        @Override
        public void chatCreated(Chat chat1, boolean b) {
            chat1.addMessageListener((chat2, message) -> {
                if (message.getType() == Message.Type.chat && !UtilValue.isEmpty(message.getBody()))
                    RxBus.get().post(RxBusAction.ACRTION_FRAGMENT_CHAT, message);
            });
        }
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
                ChatManager.getInstanceFor(mXmpptcpConnection).addChatListener(new MChatManagerListener());

                OfflineMessageManager offlineMessageManager = new OfflineMessageManager(mXmpptcpConnection);
                List<Message> messages = offlineMessageManager.getMessages();

                if (messages != null && !messages.isEmpty()) {
                    List<String> stringList = new ArrayList<>();
                    for (Message message : messages) {
                        stringList.add(message.getBody());
                        Log.i("message", message.getBody());
                    }
                    String offlineMessage = new Gson().toJson(stringList);

                    preferencesManager.addValue("offlineMessage", offlineMessage);
                    offlineMessageManager.deleteMessages();
                }

                updateUserState(0);

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

    @Override
    public void setChatListener(ChatManagerListener chatListener) {
        ChatManager.getInstanceFor(mXmpptcpConnection).addChatListener(chatListener);
    }

    @Override
    public boolean disconnect() {
        if (mXmpptcpConnection != null && mXmpptcpConnection.isConnected()) {
            mXmpptcpConnection.disconnect();
        }
        return true;
    }

    @Override
    public void setConnectionListener(AbstractConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }


    @Override
    public void setmRosterListener(RosterListener mRosterListener) {
        this.mRosterListener = mRosterListener;
    }


    /**
     * 创建聊天窗口
     *
     * @param jid 好友的JID
     * @return
     */
    public Chat createChat(String jid) {
        if (mXmpptcpConnection.isConnected()) {
            ChatManager chatManager = ChatManager.getInstanceFor(mXmpptcpConnection);
            return chatManager.createChat(jid);
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }


    /**
     * 获取指定账号的好友信息
     *
     * @param user 账号
     * @return
     */
    public RosterEntry getFriend(String user) {
        if (mXmpptcpConnection.isConnected()) {
            return Roster.getInstanceFor(mXmpptcpConnection).getEntry(user);
        }
        throw new NullPointerException("服务器连接失败，请先连接服务器");
    }

    /**
     * 获取聊天对象的Fully的jid值
     *
     * @param rosterUser 用户账号
     * @return
     */
    public String getChatJidByUser(String rosterUser) {
        if (!mXmpptcpConnection.isConnected()) {
            throw new NullPointerException("服务器连接失败，请先连接服务器");
        }
        return rosterUser + "@" + mXmpptcpConnection.getServiceName();
    }

    /**
     * 获取聊天对象的Fully的jid值
     *
     * @param nickname 用户昵称
     * @return
     */
    public String getChatJidByName(String nickname) {
        RosterEntry friend = getFriend(nickname);
        return getChatJidByUser(friend.getUser());
    }


    public void sendChatMessage(Chat chat, String message) {
        try {
            chat.sendMessage(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }

}
