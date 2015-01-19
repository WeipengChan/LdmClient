package com.ldm.ldmclient.request;


import com.ldm.ldmclient.bean.NameValueParams;

import java.util.List;

/**
 * using composite instead of inherit to handle the paging request
 *
 * Created by LDM on 14-1-10. Email : nightkid-s@163.com
 */
public abstract class PageLoader2<T> implements OnParseObserver<List<T>>{

    protected int page; //page，default 1

    private int ItemCount; //item count of each request

    private ListLoader<T> loader;

    protected PageLoader2(ListLoader<T> loader) {
        loader.registerParserObserver(this);
        this.loader = loader;
    }

    protected void setParams(List<NameValueParams> params) {
        params.add(new NameValueParams("page", String.valueOf(page)));
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
        loader.startRequest();
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
