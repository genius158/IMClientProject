package com.yan.imclientproject.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.yan.imclientproject.di.component.DaggerBaseFragmentComponent;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/7/19.
 */
public class BaseFragment extends Fragment implements IBaseView {

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
    public void meakToast(String msg) {
        toast.setText(msg);
        toast.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApplication.getRefWatcher().watch(this);
    }
}
