package com.ldm.ldmclient.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ldm.ldmclient.R;

/**
 * 列表加载动画 布局类
 * Created by LDM on 14-1-16.
 */
public class ListLoadingView extends LinearLayout{

    private TextView loadingMoreTv;
    private ProgressBar loadingMorePb;

    public ListLoadingView(Context context) {
        this(context, null);
    }

    public ListLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.listfooter_more, this);
        loadingMoreTv = (TextView) findViewById(R.id.load_more);
        loadingMorePb = (ProgressBar) findViewById(R.id.pull_to_refresh_progress);
    }

    public void setLoadingText(String hintText){
        loadingMoreTv.setText(TextUtils.isEmpty(hintText) ? "" : hintText);
    }

    public void setLoadingBarVisibility(boolean isShow){
        loadingMorePb.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setListFooter(String hintText, boolean showBar){
        setLoadingText(hintText);
        setLoadingBarVisibility(showBar);
    }
}
