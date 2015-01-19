package com.ldm.ldmclient.request;

/**
 * when the data has been ready, the data will be distributed by this observer
 * Created by LDM on 14-4-9.Email : nightkid-s@163.com
 */
public interface OnParseObserver<T> {
    public void onParseSuccess(T t);
}
