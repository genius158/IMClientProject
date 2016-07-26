package com.yan.imclientproject.app;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.yan.imclientproject.Common.UtilLeakManager;

/**
 * Created by Administrator on 2016/7/21.
 */
public class BaseActivity extends RxAppCompatActivity implements IRxActivityView {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        UtilLeakManager.fixInputMethodManagerLeak(this);

    }

    @Override
    public void makeToast(String str) {

    }
}
