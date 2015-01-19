package com.ldm.ldmclient.adapter;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.ldm.ldmclient.R;
import com.ldm.ldmclient.app.EventCode;
import com.ldm.ldmclient.request.OnParseObserver;
import com.ldm.ldmclient.request.PageLoader;
import com.ldm.ldmclient.util.NetworkUtil;
import com.ldm.ldmclient.widget.ListLoadingView;

import java.util.List;

/**
 * layer of auto loading next page
 * Created by LDM on 2015/1/6. Email : nightkid-s@163.com
 */
public abstract class PageAdapter<T, P extends PageLoader<T>, H> extends RefreshAdapter<T, P, H> implements OnParseObserver<List<T>>{

    /**view*/
    private ListView listView;
    private ListLoadingView footerView;
    private View lastPageView; //last page footer view
    /**logic*/
    private int currentPage; //page count说明当前列表的数据有几页
    private final int pageItemCount; //item count in each page
    private Status mStatus;
    private int totalCount; //total count of this request api.
    private P request;

    public PageAdapter(ListView listView, P request, OnDataCountListener listener, int pageItemCount) {
        super(listView, request, listener);
        currentPage = 1; //the first page
        totalCount = -1; //-1 represents the totalCount not been init yet
        mStatus = Status.PENDING;
        this.request = request;
        this.pageItemCount = pageItemCount;
        listView.setAdapter(this);
        this.listView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List<T> dataList = getDataList();
        switch (mStatus) {
            case PENDING:  //Pending indicates that next page can be loaded.
                if (request.getItemCount() < pageItemCount || position != dataList.size() - 1) break;
                request.loadPage(currentPage + 1);
                listView.addFooterView(getLoadingView(), null, false);
                break;
            case HOLDING: //Holding indicates that when the fit condition comes, the status may transform into Pending or Finished
                int visualCount = 1 + Math.abs((listView.getLastVisiblePosition() - listView.getFirstVisiblePosition()));
                if(visualCount >= dataList.size()) break;
                if(position != dataList.size() - (visualCount + 1) ) break;
                if( !NetworkUtil.isConnected(getContext()) ) break;
                mStatus = isLastPage() ? Status.FINISHED : Status.PENDING;
                break;
        }
        return super.getView(position, convertView, parent);
    }

    @Override
    public void onPreLoadObserver() {
        mStatus = Status.RUNNING;
    }

    @Override
    public void onLoadFinishObserver() {
        super.onLoadFinishObserver();
        mStatus = Status.HOLDING;
        listView.removeFooterView(getLoadingView());
        listView.removeFooterView(getLastPageFooterView());
    }

    @Override
    public void onParseSuccess(List<T> t) {
        notifyDataSetChanged(t, (currentPage = request.getPage()) <= 1);

        mStatus = t.size() < pageItemCount ? Status.FINISHED : Status.PENDING;
        if(t.size() < pageItemCount) totalCount = getCount(); //记录好总数

        if(isLastPage() && getCount() > 0 && request.getPage() > 1)
            listView.addFooterView(getLastPageFooterView(), null, false);
    }

    @Override
    public void onFailSession(String errorInfo, int failCode) {
        if(failCode == EventCode.CODE_ACCOUNT_OFFLINE) return;
        super.onFailSession(errorInfo, failCode);
    }

    private boolean isLastPage(){
        return totalCount > 0  && getCount() >= totalCount;
    }

    //get next page footer view
    protected ListLoadingView getLoadingView(){
        if(footerView == null) footerView  = new ListLoadingView(getContext());
        footerView.setListFooter("正在加载...", true);
        return footerView;
    }

    //get last page footer view
    private View getLastPageFooterView(){
        if(lastPageView != null) return lastPageView;
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(0, 5 * padding, 0, 5 * padding);

        TextView textView = new TextView(getContext());
        textView.setId(R.id.no_data_text_view_text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.GRAY);
        textView.setText("已经是最后一页啦");

        layout.addView(textView, params);
        return lastPageView = layout;
    }

    public Status getStatus(){
        return mStatus;
    }

}
