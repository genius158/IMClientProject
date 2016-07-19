package com.yan.imclientproject.ui.login.present;

import android.content.SharedPreferences;

import com.yan.imclientproject.app.BasePresent;
import com.yan.imclientproject.app.IBaseView;
import com.yan.imclientproject.repository.XmppConnctionImpl;
import com.yan.imclientproject.ui.login.module.view.FragmentLoginView;
import com.yan.imclientproject.ui.login.repository.AccountRepository;

import javax.inject.Inject;

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
        return false;
    }

    public void testShow() {
        fragmentLoginView.meakToast(fragmentLoginView.getAcount() + "==" + fragmentLoginView.getPasswrod());
    }


}
