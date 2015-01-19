package com.ldm.ldmclient.request;

import com.ldm.ldmclient.app.EventCode;
import com.ldm.ldmclient.app.Server;
import com.ldm.ldmclient.bean.NameValueParams;
import com.ldm.ldmclient.bean.Result;
import com.ldm.ldmclient.exception.FailSessionException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * layer of parsing json
 * Created by LDM on 13-12-31.Email : nightkid-s@163.com
 */
public abstract class BaseLoader<T> extends BaseRequest<Result<T>> {

    private List<OnParseObserver<T>> parseObservers = new ArrayList<OnParseObserver<T>>(); //observers of parse result.

    protected abstract T parseBody(String body) throws JSONException;

    protected abstract void setParams(List<NameValueParams> params); //set the request params

    protected abstract String getApi(); //http://...fixed prefix path.../api

    private OnOutputListener<T> outputListener;

    public BaseLoader() {
        this(true, false);
    }

    public BaseLoader(boolean reLogin, boolean cache) {
        if(cache) new CacheWrapper<T, BaseLoader<T>>(this);     //cache the request data
        if(reLogin) new ReLoginWrapper<BaseLoader<T>>(this);    //handle offline and re_login
    }

    @Override
    protected Result<T> parseResult(String data, boolean remote) {
        Result<T> result;
        try {
            JSONObject object = new JSONObject(data);
            int errorCode = object.optInt("error_code",EventCode.CODE_NOT_EXIST_FIELD);
            String errorInfo = object.optString("error_info", "");
            String body = object.optString("data_list", "");
            result =  new Result<T>(errorCode, errorInfo, getCompleteUrl(), errorCode != EventCode.CODE_SUCCESS ? null : parseBody(body));
        } catch (JSONException e) {
            result = new Result<T>(EventCode.CODE_FAIL_PARSE, "wrong data format", getCompleteUrl(), null);
        }
        if(outputListener != null)
            outputListener.onOutput(data, result, remote);
        return result;
    }

    @Override
    protected void handleResult(Result<T> result) throws FailSessionException {
        if (result.getErrorCode() == EventCode.CODE_SUCCESS)
            for (OnParseObserver<T> observer : parseObservers) {
                if (observer != null) observer.onParseSuccess(result.getBody());
            }
        else
            throw new FailSessionException(result.getErrorInfo(), result.getErrorCode());
    }

    @Override
    protected String getUrl() {
        return Server.getDomain() + getApi();
    }

    @Override
    protected List<NameValueParams> getParams() {
        List<NameValueParams> params = new ArrayList<NameValueParams>();
        //you can add some fixed params here//
        setParams(params);
        return params;
    }

    public void registerParserObserver(OnParseObserver<T> observer) {
        if (observer != null) parseObservers.add(observer);
    }

    public void unregisterParserObserver(OnParseObserver<T> observer) {
        if (observer != null) parseObservers.remove(observer);
    }

    public void handleResult(String data){
        handleResult(data, false);
    }

    public void handleResult(String data, boolean remote){
        try {
            handleResult(parseResult(data, remote));
        } catch (FailSessionException e) {
            e.printStackTrace();
        }
    }

    public void setOutputListener(OnOutputListener<T> outputListener) {
        this.outputListener = outputListener;
    }

    public interface OnOutputListener<T>{
        void onOutput(String data, Result<T> result, boolean remote);
    }
}
