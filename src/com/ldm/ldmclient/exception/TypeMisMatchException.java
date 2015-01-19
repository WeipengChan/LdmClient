package com.ldm.ldmclient.exception;

/**
 *  throw this exception http param not match correct type
 * Created by LDM on 14-3-20. Email : nightkid-s@163.com
 */
public class TypeMisMatchException extends Exception{

    public TypeMisMatchException() {
    }

    public TypeMisMatchException(String detailMessage) {
        super(detailMessage);
    }
}
