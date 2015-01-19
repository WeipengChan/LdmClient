package com.ldm.ldmclient.request;

/**
 * when the process of request or parse fails, this interface will be executed.
 * this interface is usually used to distinguish multi loader in a class, when there comes fails in the process of request or parsing
 * Created by LDM on 14-4-9.Email : nightkid-s@163.com
 */
public interface OnFailSessionObserver2 {
    public void onFailSession(String errorInfo, int failCode, int id, MultiLoader multiLoader);
}
