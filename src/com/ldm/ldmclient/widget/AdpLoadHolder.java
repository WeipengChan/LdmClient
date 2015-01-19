package com.ldm.ldmclient.widget;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.ldm.ldmclient.R;
import com.ldm.ldmclient.adapter.BaseCusAdapter;
import com.ldm.ldmclient.request.ListLoader;
import com.ldm.ldmclient.util.NetworkUtil;

import java.util.List;

/**
 * adapter loading holder
 * Created by LDM on 2015/1/10. Email : nightkid-s@163.com
 */
public class AdpLoadHolder<T> extends ListLoadHolder<T> implements BaseCusAdapter.OnDataCountListener{

    private Drawable noDataDrawable;

    public AdpLoadHolder(ListLoader<T> request, View target) {
        super(request, target);
    }

    @Override
    public void onParseSuccess(List<T> list) {
        setVisibility(list.isEmpty());
    }

    @Override
    public void onFailSession(String errorInfo, int failCode) {
        getTextView().setText(errorInfo);
    }

    @Override
    public void onCount(int count) {
        getBadgeFrame().setVisibility(count < 1);
        if(count > 0) return;
        boolean validNetwork = NetworkUtil.isConnectedOrConnecting(getContext());
        getTextView().setText(getContext().getString(validNetwork ? R.string.no_data_tip : R.string.invalid_network_state));
        getTextView().setCompoundDrawables(null, validNetwork ? getNoDataDrawable() : getNoNetDrawable(), null, null);
    }


    private Drawable getNoDataDrawable() {
        if(noDataDrawable == null){
            noDataDrawable = getContext().getResources().getDrawable(R.drawable.no_record_icon);
            noDataDrawable.setBounds(0, 0, noDataDrawable.getMinimumWidth(), noDataDrawable.getMinimumHeight());
        }
        return noDataDrawable;
    }
}
