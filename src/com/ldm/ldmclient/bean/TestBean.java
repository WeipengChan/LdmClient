package com.ldm.ldmclient.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * just for test
 * Created by LDM on 2015/1/7. Email : nightkid-s@163.com
 */
public class TestBean {
    private String title;
    private String content;

    public TestBean(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public TestBean(JSONObject object) throws JSONException{
        title = object.optString("title", "");
        content = object.optString("content", "");
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
