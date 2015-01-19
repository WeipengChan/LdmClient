package com.ldm.ldmclient.bean;

/**
 * 封装服务器参数和数据实体
 * @param <T> 数据实体
 * Created by LDM on 13-12-31. Email : nightkid-s@163.com
 */
public class Result<T> {
	private int errorCode;
    private String errorInfo;
    private String requestUrl;
    private T body;

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

    public T getBody() {
        return body;
    }

    public Result(int errorCode, String errorInfo, String requestUrl, T body) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.requestUrl = requestUrl;
        this.body = body;
    }

}
