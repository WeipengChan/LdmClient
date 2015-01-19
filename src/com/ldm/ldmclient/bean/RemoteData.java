package com.ldm.ldmclient.bean;

/**
 * Created by LDM on 2014/7/7. Email : nightkid-s@163.com
 */
public class RemoteData {
    private String url;
    private String data;
    private int hashCode;

    public RemoteData(String url, String data) {
        this.url = url.trim();
        this.data = data.trim();
        this.hashCode = url.hashCode();
    }

    public String getUrl() {
        return url;
    }

    public String getData() {
        return data;
    }

    public int getHashCode() {
        return hashCode;
    }
}
