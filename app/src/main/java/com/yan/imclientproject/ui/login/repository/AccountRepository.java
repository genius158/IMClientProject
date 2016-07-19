package com.yan.imclientproject.ui.login.repository;

import android.content.SharedPreferences;
import android.text.TextUtils;


/**
 * Created by Sequarius on 2016/5/30.
 */
public class AccountRepository {
    private String username;
    private String password;

    private SharedPreferences preferences;

    public static AccountRepository accountRepository;

    public static AccountRepository getAccountRepository(SharedPreferences preferences) {
        if (accountRepository == null) {
            synchronized (AccountRepository.class) {
                if (accountRepository == null) {
                    accountRepository = new AccountRepository(preferences);
                }
            }
        }
        return accountRepository;
    }

    AccountRepository(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getUsername() {
        if (TextUtils.isEmpty(username)) {
            username = preferences.getString("username", null);
        }
        return username;
    }

    public void setUsername(String username) {
        if (preferences.edit().putString("username", username).commit()) {
            this.username = username;
        }
    }

    public String getPassword() {
        if (TextUtils.isEmpty(password)) {
            password = preferences.getString("password", null);
        }
        return password;
    }

    public void setPassword(String password) {
        if (preferences.edit().putString("password", password).commit()) {
            this.password = password;
        }
    }
}
