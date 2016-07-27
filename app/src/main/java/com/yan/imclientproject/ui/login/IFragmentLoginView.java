package com.yan.imclientproject.ui.login;

import com.yan.imclientproject.app.IRxFragmentView;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface IFragmentLoginView extends IRxFragmentView {

    String getAccount();

    String getPassword();

      void toFragmentChat();

}
