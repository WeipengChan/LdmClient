package com.ldm.ldmclient.request;


import com.ldm.ldmclient.bean.NameValueParams;

import java.util.List;

/**
 * paging
 * Created by LDM on 14-1-10. Email : nightkid-s@163.com
 */
public abstract class PageLoader<T> extends ListLoader<T> implements OnParseObserver<List<T>>{

    protected int page; //page，default 1

    private int ItemCount; //item count of each request

    protected PageLoader() {
        this(1);
    }

    protected PageLoader(boolean  reLogin, boolean cache) {
        this(1, reLogin, cache);
    }

    protected PageLoader(int page) {
        this(page, true, false);
    }

    protected PageLoader(int page, boolean reLogin, boolean cache) {
        super(reLogin, cache);
        this.page = page;
        registerParserObserver(this);
    }

    protected abstract void setNoPageParams(List<NameValueParams> params); //don't set page param in this function!

    @Override
    protected void setParams(List<NameValueParams> params) {
        params.add(new NameValueParams("page", String.valueOf(page)));
        setNoPageParams(params);
    }

    @Override
    public void onParseSuccess(List<T> list) {
        ItemCount = list == null ? 0 : list.size();
    }

    public int getPage(){
        return page;
    }

    public void loadPage(int page){
        this.page = page;
        startRequest();
    }

    public void loadNextPage(){
        loadPage( page + 1 );
    }

    public void loadFirstPage(){
        loadPage(1);
    }

    public int getItemCount() {
        return ItemCount;
    }
}
