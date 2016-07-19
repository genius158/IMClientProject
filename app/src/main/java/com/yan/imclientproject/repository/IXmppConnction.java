package com.yan.imclientproject.repository;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface IXmppConnction {

    boolean connect();

    boolean login(String account, String password);

    boolean login();

    boolean disconnect();


}
