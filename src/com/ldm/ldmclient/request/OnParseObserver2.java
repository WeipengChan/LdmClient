package com.ldm.ldmclient.request;

/**
 * when the data has been ready, the data will be distributed by this observer
 * this interface is usually used to distinguish multi loader in a class, when there comes data
 *
 * Created by LDM on 14-4-9.Email : nightkid-s@163.com
 */
public interface OnParseObserver2<T> {
    public void onParseSuccess(T t, int id, MultiLoader multiLoader);
}
