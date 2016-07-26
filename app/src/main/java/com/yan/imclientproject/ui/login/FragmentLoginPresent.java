package com.yan.imclientproject.ui.login;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.yan.imclientproject.app.PreferencesManager;
import com.yan.imclientproject.app.mvp.BaseMvpPresenter;
import com.yan.imclientproject.repository.XmppConnctionImpl;
import com.yan.imclientproject.bean.Account;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/19.
 */
public class FragmentLoginPresent extends BaseMvpPresenter<IFragmentLoginView> {

    private XmppConnctionImpl xmppConnction;
    private PreferencesManager preferencesManager;
    private Account account;

    @Inject
    public FragmentLoginPresent(XmppConnctionImpl xmppConnction, PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
        this.xmppConnction = xmppConnction;
    }

    public boolean login() {
        checkViewAttached();
        Observable.create((Subscriber<? super Boolean> subscriber) -> {
            subscriber.onNext(xmppConnction.login(mMvpView.getAcount(), mMvpView.getPasswrod()));
        }).subscribeOn(Schedulers.io())
                .compose(RxLifecycle.bindUntilEvent(mMvpView.lifecycle(), FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    mMvpView.makeToast("islogin-" + o + "");
                });
        return true;
    }

}
