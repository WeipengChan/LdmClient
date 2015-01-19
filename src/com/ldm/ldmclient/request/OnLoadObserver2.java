package com.ldm.ldmclient.request;

/**
 * observer of load state
 * Created by LDM on 2014/5/6.Email : nightkid-s@163.com
 */
public interface OnLoadObserver2 {
    void onPreLoadObserver(int id); //before loading
    void onLoadFinishObserver(int id, MultiLoader multiLoader); //after loading
}
