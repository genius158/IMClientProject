package com.yan.imclientproject.ui.login.present;

import android.content.SharedPreferences;
import android.util.Log;

import com.yan.imclientproject.app.BasePresent;
import com.yan.imclientproject.app.IBaseView;
import com.yan.imclientproject.repository.XmppConnctionImpl;
import com.yan.imclientproject.ui.login.module.view.FragmentLoginView;
import com.yan.imclientproject.ui.login.repository.AccountRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/19.
 */
public class FragmentLoginPresent extends BasePresent {

    private XmppConnctionImpl xmppConnction;
    private SharedPreferences sharedPreferences;
    private AccountRepository accountRepository;
    private FragmentLoginView fragmentLoginView;

    @Override
    public void attachView(IBaseView iBaseView) {
        fragmentLoginView = (FragmentLoginView) iBaseView;

    }

    @Override
    public void onDestroy() {
        fragmentLoginView = null;
    }

    @Inject
    public FragmentLoginPresent(XmppConnctionImpl xmppConnction, SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.xmppConnction = xmppConnction;
        accountRepository = AccountRepository.getAccountRepository(sharedPreferences);
    }

    public boolean login() {
        Observable.create((Subscriber<? super Boolean> subscriber) -> {
            subscriber.onNext(xmppConnction.login(fragmentLoginView.getAcount(), fragmentLoginView.getPasswrod()));
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (o) {
                        fragmentLoginView.meakToast("islogin-" + o + "");
                    }
                });
        return true;
    }

}
