package com.ldm.ldmclient.adapter;

import android.view.View;
import android.widget.ListView;
import com.ldm.ldmclient.request.PageLoader;
import com.ldm.ldmclient.widget.AdpLoadHolder;
import com.ldm.myptr.in.srain.cube.views.ptr.PtrDefaultHandler;
import com.ldm.myptr.in.srain.cube.views.ptr.PtrFrameLayout;
import com.ldm.myptr.in.srain.cube.views.ptr.PtrHandler;

/**
 * auto load next page and pull to refresh using alibaba ptr library
 * Created by LDM on 2015/1/6. Email : nightkid-s@163.com
 */
public abstract class PtrPageAdapter<T, P extends PageLoader<T>, H> extends PageAdapter<T, P, H> implements PtrHandler {

    private PtrFrameLayout layout;
    private P request;

    public PtrPageAdapter(PtrFrameLayout layout, ListView listView, P request) {
        this(layout, listView, request, null);
    }

    public PtrPageAdapter(PtrFrameLayout layout, ListView listView, P request, OnDataCountListener listener) {
        this(layout, listView, request, listener, 10); //default 10 item per page
    }

    public PtrPageAdapter(PtrFrameLayout layout, ListView listView, P request, OnDataCountListener listener, int pageItemCount) {
        super(listView, request, listener, pageItemCount);
        setOnCountListener(new AdpLoadHolder<T>(request, layout));
        this.layout = layout;
        this.layout.setPtrHandler(this);
        this.request = request;
    }

    @Override
    protected void autoRefresh() {
        layout.autoRefresh(true);
    }

    @Override
    protected void refreshComplete() {
        layout.refreshComplete();
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        getRequest().loadFirstPage();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View content, View header) {
        boolean canDoRefresh = !(getStatus() == Status.RUNNING && request.getPage() > 1); //loading greater than the first page disallows to refresh
        return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, content, header) && canDoRefresh;
    }
}
