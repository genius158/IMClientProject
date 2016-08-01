package com.yan.imclientproject.app;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.yan.imclientproject.Common.UtilLeakManager;
import com.yan.imclientproject.di.component.DaggerActivityComponent;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2016/7/21.
 */
public class BaseActivity extends SupportActivity implements ActivityLifecycleProvider, IRxActivityView {

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Inject
    Toast toast;

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MApplication mApplication = (MApplication) getApplication();
        DaggerActivityComponent.builder().appComponent(mApplication.getAppComponent())
                .build().inject(this);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        UtilLeakManager.fixInputMethodManagerLeak(this);
    }

    @Override
    public void makeToast(String str) {
        toast.setText(str);
        toast.show();
    }


    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public void onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            makeToast("the application will exit if you click again");
        }
    }

}
