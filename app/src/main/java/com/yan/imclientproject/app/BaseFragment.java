package com.yan.imclientproject.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.yan.imclientproject.app.mvp.IMvpView;
import com.yan.imclientproject.di.component.DaggerBaseFragmentComponent;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/7/19.
 */
public class BaseFragment extends RxFragment implements IMvpView {

    protected MApplication mApplication;

    @Inject
    Toast toast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MApplication) getActivity().getApplication();
        DaggerBaseFragmentComponent.builder().appComponent(mApplication.getAppComponent()).build().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toastCancel();
        mApplication.getRefWatcher().watch(this);
    }

    private void toastCancel() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    @Override
    public void makeToast(String str) {
        toast.setText(str);
        toast.show();
    }
}
