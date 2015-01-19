package com.ldm.ldmclient.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * layer of parsing single object data
 * Created by LDM on 14-1-9.Email : nightkid-s@163.com
 */
public abstract class Loader<T> extends BaseLoader<T> {

    protected Loader() {}

    protected Loader(boolean reLogin, boolean cache) {
        super(reLogin, cache);
    }

    protected abstract T parseBody(JSONObject object) throws JSONException;

    @Override
    protected T parseBody(String body) throws JSONException {
        JSONArray array = new JSONArray(body);
        if(array.length() > 0) {
            JSONObject object = array.getJSONObject(0);
            return parseBody(object);
        }
        return parseBody(new JSONObject());
    }

}
