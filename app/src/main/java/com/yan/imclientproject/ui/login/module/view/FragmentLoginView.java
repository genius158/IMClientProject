package com.yan.imclientproject.ui.login.module.view;

import com.trello.rxlifecycle.FragmentEvent;
import com.yan.imclientproject.app.IBaseView;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface FragmentLoginView extends IBaseView {

    String getAcount();

    String getPasswrod();

    Observable<FragmentEvent> lifecycle();


}
