package com.yan.imclientproject.ui.login.view;

import com.trello.rxlifecycle.FragmentEvent;
import com.yan.imclientproject.app.mvp.IMvpView;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface IFragmentLoginView extends IMvpView {

    String getAcount();

    String getPasswrod();

    Observable<FragmentEvent> lifecycle();


}
