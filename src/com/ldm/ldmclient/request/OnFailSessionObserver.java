package com.ldm.ldmclient.request;

/**
 * when the process of request or parse fails, this interface will be executed.
 * Created by LDM on 14-4-9.Email : nightkid-s@163.com
 */
public interface OnFailSessionObserver {
    public void onFailSession(String errorInfo, int failCode);
}
