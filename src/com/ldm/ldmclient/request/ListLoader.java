package com.ldm.ldmclient.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * layer of parsing list data
 * Created by LDM on 13-12-31.Email : nightkid-s@163.com
 */
public abstract class ListLoader<T> extends BaseLoader<List<T>>{

    protected ListLoader() {}

    protected ListLoader(boolean reLogin, boolean cache) {
        super(reLogin, false);
        if(cache)
            new PageCacheWrapper<T, ListLoader<T>>(this);
    }

    protected abstract T parseBody(JSONObject object) throws  JSONException; //解析单个个体

    @Override
    protected List<T> parseBody(String body) throws JSONException{
        JSONArray jsonArray = new JSONArray(body);
        List<T> list = new ArrayList<T>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++){
            list.add(parseBody(jsonArray.getJSONObject(i)));
        }
        return list;
    }

}
