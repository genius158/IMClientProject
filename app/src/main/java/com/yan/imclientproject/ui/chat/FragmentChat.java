package com.yan.imclientproject.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.yan.imclientproject.Common.RxBusAction;
import com.yan.imclientproject.Common.UtilValue;
import com.yan.imclientproject.R;
import com.yan.imclientproject.app.BaseFragment;
import com.yan.imclientproject.app.MApplication;
import com.yan.imclientproject.app.PreferencesManager;
import com.yan.imclientproject.di.component.DaggerFragmenChatComponent;
import com.yan.imclientproject.di.model.FragmentChatModel;
import com.yan.imclientproject.repository.XmppConnctionImpl;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentChat extends BaseFragment implements IFramgmentChatView {

    @Inject
    XmppConnctionImpl xmppConnction;

    @Inject
    PreferencesManager preferencesManager;

    @Inject
    Chat chat;
    @BindView(R.id.fragment_chat_tv_content)
    TextView chatContent;
    @BindView(R.id.fragment_chat_edt_content)
    EditText chatEdtContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        MApplication mApplication = (MApplication) getActivity().getApplication();

        DaggerFragmenChatComponent.builder().appComponent(mApplication.getAppComponent())
                .fragmentChatModel(new FragmentChatModel("admin"))
                .build().inject(this);

        String offlineMessage = preferencesManager.getStrValue("offlineMessage");
        preferencesManager.clearValue("offlineMessage");
        if (!UtilValue.isEmpty(offlineMessage)) {
            List<String> messages = new Gson().fromJson(offlineMessage, new TypeToken<List<String>>() {
            }.getType());
            for (String message : messages) {
                chatContent.append("\n" + message);
            }
        }
        return view;
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag(RxBusAction.ACRTION_FRAGMENT_CHAT)}
    )
    public void eatMore(Message message) {
        chatContent.append("\n" + message.getFrom().substring(
                0, message.getFrom().indexOf('@')
        ) + ":" + message.getBody());
    }

    @Override
    public void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }

    @OnClick(R.id.fragment_chat_btn_content)
    public void onClick() {
        xmppConnction.sendChatMessage(chat, chatEdtContent.getText().toString());
        chatContent.append("\n" + xmppConnction.getAccount().substring(
                0, xmppConnction.getAccount().indexOf('@')
        ) + ":" + chatEdtContent.getText().toString());

        chatEdtContent.setText("");
    }
}
