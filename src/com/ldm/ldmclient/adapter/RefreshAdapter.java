package com.ldm.ldmclient.adapter;

import android.widget.AbsListView;
import com.ldm.ldmclient.request.BaseRequest;
import com.ldm.ldmclient.request.ListLoader;
import com.ldm.ldmclient.request.OnFailSessionObserver;
import com.ldm.ldmclient.request.OnParseObserver;

import java.util.List;

/**
 * layer of refresh
 * Created by LDM on 2014/12/24. Email : nightkid-s@163.com
 */
public abstract class RefreshAdapter<T, P extends ListLoader<T>, H> extends BaseCusAdapter<T, H> implements BaseRequest.LoadObserver, OnFailSessionObserver, OnParseObserver<List<T>> {

    private P request;
    private AbsListView absListView;
    protected abstract void autoRefresh();
    protected abstract void refreshComplete();

    public RefreshAdapter(AbsListView absListView, P request) {
        this(absListView, request, null);
    }

    public RefreshAdapter(AbsListView absListView, P request, OnDataCountListener listener) {
        super(absListView.getContext(), listener);
        this.absListView = absListView;
        this.request = request;
        request.registerParserObserver(this);
        request.registerLoadObserver(this);
        request.registerFailObserver(this);
    }

    @Override
    public void onLoadFinishObserver() {
        refreshComplete();
    }

    @Override
    public void onFailSession(String errorInfo, int failCode) {
        displayToast(errorInfo);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(List<T> data, boolean isRefresh){
        List<T> dataList = getDataList();
        if(isRefresh)
            dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public P getRequest() {
        return request;
    }

    public void startDelayRefresh(){
        startDelayRefresh(200);
    }

    public void startDelayRefresh(long delayMillis){
        if(delayMillis < 0)
            delayMillis = 0;
        absListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoRefresh();
            }
        }, delayMillis);
    }
}
