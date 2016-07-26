package com.yan.imclientproject.app.mvp;

/**
 * Created by Administrator on 2016/7/26.
 */
public interface IMvpPresenter<V extends IMvpView > {

    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    void detachView();

}
