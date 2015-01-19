package com.ldm.ldmclient.exception;

/**
 * http fail session
 * Created by LDM on 14-4-8. Email : nightkid-s@163.com
 */
public class FailSessionException extends Exception{

    private int failCode;

    public FailSessionException(String detailMessage, int failCode) {
        super(detailMessage);
        this.failCode = failCode;
    }

    public int getFailCode() {
        return failCode;
    }
}
