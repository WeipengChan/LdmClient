package com.ldm.ldmclient.request;

import android.text.TextUtils;
import com.ldm.ldmclient.app.EventCode;
import com.ldm.ldmclient.bean.Result;
import com.ldm.ldmclient.db.ClientDbHelper;
import com.ldm.ldmclient.util.LogUtil;

import static com.ldm.ldmclient.request.BaseRequest.LoadObserver;

/**
 * cache data for each wrapped request
 * Created by LDM on 2014/7/7. Email : nightkid-s@163.com
 */
public class CacheWrapper<T, P extends BaseLoader<T>> implements LoadObserver, OnFailSessionObserver, BaseLoader.OnOutputListener<T>{

    protected P request;

    public CacheWrapper(P request) {
        request.registerLoadObserver(this);
        request.registerFailObserver(this);
        request.setOutputListener(this);
        this.request = request;
    }

    @Override
    public void onOutput(String data, Result<T> result, boolean remote) {
        // data will not be saved following below 3 conditions:
        // 1.data comes from local
        // 2.there is an error in the process of loading network data or parsing json
        if(!remote) return;
        if(result.getErrorCode() != EventCode.CODE_SUCCESS) return;
        try {
            ClientDbHelper.getInstance().updateOfflineData(result.getRequestUrl(), data);
        } catch (RuntimeException e){
            LogUtil.e(String.format("saving data throws an exception :\n%s", e.getMessage()));
        }
    }

    @Override
    public void onPreLoadObserver() { }

    @Override
    public void onLoadFinishObserver() { }

    @Override
    public void onFailSession(String errorInfo, int failCode) {
        if(failCode != EventCode.CODE_FAIL_REQUEST) return;
        try {
            String offlineData = ClientDbHelper.getInstance().getOfflineData(request.getUrl()); //query local data
            if(TextUtils.isEmpty(offlineData)) return;
            request.handleResult(offlineData); //load local data
        } catch (RuntimeException e){
            LogUtil.e(String.format("getting data throws an exception :\n%s", e.getMessage()));
        }
    }

}
