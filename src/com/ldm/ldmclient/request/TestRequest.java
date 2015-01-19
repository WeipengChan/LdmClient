package com.ldm.ldmclient.request;

import com.ldm.ldmclient.app.Server;
import com.ldm.ldmclient.bean.NameValueParams;
import com.ldm.ldmclient.bean.TestBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by LDM on 2015/1/7. Email : nightkid-s@163.com
 */
public class TestRequest extends PageLoader<TestBean>{

    public TestRequest() {
        super(true, true);
    }

    @Override
    protected void setNoPageParams(List<NameValueParams> params) {
        params.add(new NameValueParams("httpPostParams", "test"));
    }

    @Override
    protected TestBean parseBody(JSONObject object) throws JSONException {
        return new TestBean(object);
    }

    @Override
    protected String getApi() {
        return Server.API_TEST;
    }
}
