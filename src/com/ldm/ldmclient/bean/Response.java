package com.ldm.ldmclient.bean;

/**
 * 封装网络的响应事件
 * Created by LDM on 13-12-20. Email : nightkid-s@163.com
 */
public class Response<T> {
    private boolean isSuccessful;
    private String responseInfo;
    private T result;

    public Response(boolean isSuccessful, String responseInfo, T result) {
        this.isSuccessful = isSuccessful;
        this.responseInfo = responseInfo;
        this.result = result;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }

    public String getResponseInfo() {
        return responseInfo;
    }

    public T getResult() {
        return result;
    }
}
