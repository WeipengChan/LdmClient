package com.ldm.ldmclient.adapter;

import android.view.View;
import android.widget.AbsListView;
import com.ldm.ldmclient.request.ListLoader;
import com.ldm.ldmclient.widget.AdpLoadHolder;
import com.ldm.myptr.in.srain.cube.views.ptr.PtrDefaultHandler;
import com.ldm.myptr.in.srain.cube.views.ptr.PtrFrameLayout;
import com.ldm.myptr.in.srain.cube.views.ptr.PtrHandler;

import java.util.List;

/**
 * pull to refresh for single page.
 * using alibaba pull to refresh library
 * Created by LDM on 2015/1/6. Email : nightkid-s@163.com
 */
public abstract class PtrAdapter<T, P extends ListLoader<T>, H> extends RefreshAdapter<T, P, H> implements PtrHandler{

    private PtrFrameLayout layout;

    public PtrAdapter(PtrFrameLayout layout, AbsListView listView, P request) {
        this(layout, listView, request, null);
    }

    public PtrAdapter(PtrFrameLayout layout, AbsListView listView, P request, OnDataCountListener listener) {
        super(listView, request, listener);
        setOnCountListener(new AdpLoadHolder<T>(request, layout));
        layout.setPtrHandler(this);
        this.layout = layout;
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
    public void onPreLoadObserver() { }

    @Override
    public void onParseSuccess(List<T> t) {
        notifyDataSetChanged(t, true);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        getRequest().startRequest();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, content, header);
    }
}
