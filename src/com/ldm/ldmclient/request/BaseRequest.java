package com.ldm.ldmclient.request;


import com.ldm.ldmclient.app.EventCode;
import com.ldm.ldmclient.bean.NameValueParams;
import com.ldm.ldmclient.bean.Response;
import com.ldm.ldmclient.exception.FailSessionException;
import com.ldm.ldmclient.util.http.HttpTool;
import com.ldm.ldmclient.util.LogUtil;
import com.ldm.ldmclient.util.TestData;

import java.util.ArrayList;
import java.util.List;

/**
 * layer of request process
 *
 * Created by LDM on 13-12-20. Email : nightkid-s@163.com
 */
public abstract class BaseRequest<T> extends Request<Response<T>> {

    protected List<LoadObserver> loadObservers = new ArrayList<LoadObserver>(); //observers of load state

    protected List<OnFailSessionObserver> failObservers = new ArrayList<OnFailSessionObserver>(); //observers of fail session

    protected abstract String getUrl();

    protected abstract List<NameValueParams> getParams(); //request params

    protected abstract T parseResult(String result, boolean remote); //abstract process of parse, the remote indicates that the result comes from remote server or local

    protected abstract void handleResult(T result) throws FailSessionException; //abstract process of result of parse

    /**load status observer interface**/
    public interface LoadObserver {
        void onPreLoadObserver(); //before load
        void onLoadFinishObserver(); //after load
    }

    @Override
    protected void preRequest() {
        for (LoadObserver observer : loadObservers) observer.onPreLoadObserver();
    }

    @Override
    protected Response<T> doRequest() {
        //get data from network
        boolean isSuccessful = false;
        StringBuilder info = new StringBuilder();
        StringBuilder data = new StringBuilder();
        try {
            //String result = HttpTool.post(getUrl(), getParams());
            String result = TestData.getData(getCompleteUrl());
            data.append(result);
            isSuccessful = true;
        } catch (RuntimeException e) {
            info.append(e.getMessage());
        }
        return new Response<T>(isSuccessful, info.toString(), isSuccessful ? parseResult(data.toString(), true) : null);
    }

    @Override
    protected void doRequestComplete(Response<T> response){
        //handle date form response
        for (LoadObserver observer : loadObservers) observer.onLoadFinishObserver();
        try {
            if (response.getIsSuccessful()) handleResult(response.getResult());
            else throw new FailSessionException(response.getResponseInfo(), EventCode.CODE_FAIL_REQUEST);
        }catch (FailSessionException e){
            for (OnFailSessionObserver failObserver : failObservers) failObserver.onFailSession(e.getMessage(), e.getFailCode());
            LogUtil.e("http_", String.format("session fail： %s  \ntype: %d \nurl:  %s", e.getMessage(), e.getFailCode(), getCompleteUrl()));
        }

    }

    public void registerLoadObserver(LoadObserver observer) {
        if (observer != null) loadObservers.add(observer);
    }

    public void unRegisterLoadObserver(LoadObserver observer) {
        if (observer != null) loadObservers.remove(observer);
    }

    public void registerFailObserver(OnFailSessionObserver failObserver) {
        if(failObserver != null) failObservers.add(failObserver);
    }

    public void unRegisterFailObserver(OnFailSessionObserver failObserver) {
        if(failObserver != null) failObservers.remove(failObserver);
    }

    public String getCompleteUrl(){
        return HttpTool.constructUrl(getUrl(), getParams());
    }
}
