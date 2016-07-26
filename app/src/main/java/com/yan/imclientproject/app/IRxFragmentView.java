package com.yan.imclientproject.app;

import com.trello.rxlifecycle.FragmentEvent;
import com.yan.imclientproject.app.mvp.IMvpView;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/26.
 */
public interface IRxFragmentView extends IMvpView {
    Observable<FragmentEvent> lifecycle();
}
