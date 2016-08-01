package com.yan.imclientproject.app;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.yan.imclientproject.di.component.DaggerFragmentComponent;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportFragment;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2016/7/19.
 */
public class BaseFragment extends SupportFragment implements IRxFragmentView, FragmentLifecycleProvider {
    protected MApplication mApplication;

    @Inject
    Toast toast;

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
        mApplication = (MApplication) getActivity().getApplication();
        DaggerFragmentComponent.builder().appComponent(mApplication.getAppComponent()).build().inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        toastCancel();
        super.onDestroy();
        mApplication.getRefWatcher().watch(this);
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }


    private void toastCancel() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    public static <T extends Fragment> T newInstance(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T extends Fragment> T newInstance(Class<T> clazz, Hashtable hashtable) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle bArgs = new Bundle();

        Set<Map.Entry> entrys = hashtable.entrySet();

        for (Map.Entry entry : entrys) {
            putValue(bArgs, entry);
        }

        t.setArguments(bArgs);
        return t;
    }

    private static void putValue(Bundle bundle, Map.Entry entry) {
        if (entry.getValue() instanceof String) {
            bundle.putString((String) entry.getKey(), (String) entry.getValue());
        } else if (entry.getValue() instanceof Integer) {
            bundle.putInt((String) entry.getKey(), (Integer) entry.getValue());
        }
    }

    @Override
    public void makeToast(String str) {
        toast.setText(str);
        toast.show();
    }
}
