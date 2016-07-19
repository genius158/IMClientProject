package com.yan.imclientproject.app;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface IPresent {
    void attachView(IBaseView iBaseView);

    void onDestroy();
}
