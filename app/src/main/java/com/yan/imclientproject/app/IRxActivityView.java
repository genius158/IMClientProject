package com.yan.imclientproject.app;

import com.trello.rxlifecycle.ActivityEvent;
import com.yan.imclientproject.app.mvp.IMvpView;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/26.
 */
public interface IRxActivityView extends IMvpView {
    Observable<ActivityEvent> lifecycle();
}
