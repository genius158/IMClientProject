package com.yan.imclientproject.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.yan.imclientproject.di.component.DaggerFragmentComponent;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/7/19.
 */
public class BaseFragment extends RxFragment implements IRxFragmentView {

    protected MApplication mApplication;

    @Inject
    Toast toast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MApplication) getActivity().getApplication();
        DaggerFragmentComponent.builder().appComponent(mApplication.getAppComponent()).build().inject(this);
    }

    @Override
    public void onDestroy() {
        toastCancel();
        super.onDestroy();
        mApplication.getRefWatcher().watch(this);
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
