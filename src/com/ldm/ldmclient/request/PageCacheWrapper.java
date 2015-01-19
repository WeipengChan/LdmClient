package com.ldm.ldmclient.request;

import android.text.TextUtils;
import com.ldm.ldmclient.app.EventCode;
import com.ldm.ldmclient.bean.Result;
import com.ldm.ldmclient.db.ClientDbHelper;
import com.ldm.ldmclient.util.LogUtil;

import java.util.List;

/**
 * cache wrapper for page
 *
 * Created by LDM on 2015/1/6. Email : nightkid-s@163.com
 */
public class PageCacheWrapper<T, P extends ListLoader<T>> extends CacheWrapper<List<T>, P>{

    private boolean Loaded; //whether load once

    public PageCacheWrapper(P request) {
        super(request);
        Loaded = false;
    }

    @Override
    public void onOutput(String data, Result<List<T>> result, boolean remote) {
        // data will not be saved following below 3 conditions:
        // 1.data comes from local
        // 2.there is an error in the process of loading network data or parsing json
        // 3.empty data body
        if(!remote) return;
        if(result.getErrorCode() != EventCode.CODE_SUCCESS) return;
        List<T> list = result.getBody();
        if(list == null || list.isEmpty()) return;
        try {
            ClientDbHelper.getInstance().updateOfflineData(result.getRequestUrl(), data);
        } catch (RuntimeException e){
            LogUtil.e(String.format("saving data throws an exception :\n%s", e.getMessage()));
        }
    }

    @Override
    public void onPreLoadObserver() {
        super.onPreLoadObserver();
        // paging strategy :
        // the first page will load local data ,and then preferentially load network data from next page
        try {
            if(Loaded) return;
            Loaded = true;
            String offlineData = ClientDbHelper.getInstance().getOfflineData(request.getCompleteUrl());  //query local data
            if(TextUtils.isEmpty(offlineData)) return;
            request.handleResult(offlineData, false); //load local data
        } catch (RuntimeException e){
            LogUtil.e(String.format("getting data throws an exception :\n%s", e.getMessage()));
        }
    }

}
