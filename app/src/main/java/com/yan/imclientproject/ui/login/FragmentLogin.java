package com.yan.imclientproject.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yan.imclientproject.Common.UtilValue;
import com.yan.imclientproject.R;
import com.yan.imclientproject.app.BaseFragment;
import com.yan.imclientproject.app.MApplication;
import com.yan.imclientproject.di.component.DaggerFragmentComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends BaseFragment implements IFragmentLoginView {

    @Inject
    FragmentLoginPresent fragmentLoginPresent;
    @BindView(R.id.fragment_login_input_account)
    EditText mEditTextAccount;
    @BindView(R.id.fragment_login_input_password)
    EditText mEditTextPassword;


    public FragmentLogin() {
    }

    public static FragmentLogin instance() {
        FragmentLogin fragmentLogin = new FragmentLogin();
        Bundle args = new Bundle();
        fragmentLogin.setArguments(args);
        return fragmentLogin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, view);

        MApplication mApplication = (MApplication) getActivity().getApplication();

        DaggerFragmentComponent.builder().appComponent(mApplication.getAppComponent()).build().inject(this);
        fragmentLoginPresent.attachView(this);
        return view;
    }

    @Override
    public String getAccount() {
        return UtilValue.getValue(mEditTextAccount.getText(), "");
    }

    @Override
    public String getPassword() {
        return UtilValue.getValue(mEditTextPassword.getText(), "");
    }

    @OnClick(R.id.fragment_login_btn_login)
    public void onClick() {
        boolean b = fragmentLoginPresent.login();
    }

    @Override
    public void onDestroy() {
        fragmentLoginPresent.detachView();
        super.onDestroy();
    }


}
