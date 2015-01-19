package com.ldm.ldmclient.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ldm.ldmclient.R;
import com.ldm.ldmclient.request.BaseLoader;
import com.ldm.ldmclient.request.BaseRequest;
import com.ldm.ldmclient.request.OnFailSessionObserver;
import com.ldm.ldmclient.request.OnParseObserver;
import com.ldm.ldmclient.util.NetworkUtil;

/**
 * if there is noting in the list view, this will show some tips
 * Created by LDM on 2015/1/8. Email : nightkid-s@163.com
 */
public class InitLoadHolder<T> implements View.OnClickListener, View.OnTouchListener, OnFailSessionObserver, BaseRequest.LoadObserver, OnParseObserver<T>{

    private BadgeFrame badgeFrame;
    private View target;
    private TextView textView;
    private ProgressBar bar;
    private BaseLoader<T> request;
    private Drawable noNetDrawable;

    public InitLoadHolder(BaseLoader<T> request, View target) {
        Context context = target.getContext();
        badgeFrame = new BadgeFrame(target);
        RelativeLayout layout = new RelativeLayout(context);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.WHITE);
        layout.setOnTouchListener(this);
        textView = new TextView(context);
        textView.setCompoundDrawablePadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, target.getResources().getDisplayMetrics()));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setOnClickListener(this);
        textView.setVisibility(View.GONE);
        bar = new ProgressBar(context);
        bar.setVisibility(View.GONE);
        layout.addView(textView);
        layout.addView(bar);
        badgeFrame.addView(layout);
        badgeFrame.show();

        request.registerFailObserver(this);
        request.registerLoadObserver(this);
        request.registerParserObserver(this);
        this.target = target;
        this.request = request;
    }

    @Override
    public void onClick(View v) {
        boolean validNetwork = NetworkUtil.isConnectedOrConnecting(v.getContext());
        if(validNetwork)
            request.startRequest();
        else
            Toast.makeText(v.getContext(), v.getResources().getString(R.string.invalid_network_state), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onPreLoadObserver() {
        if(badgeFrame.getVisibility() != View.VISIBLE) return;
        textView.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinishObserver() {
        if(badgeFrame.getVisibility() != View.VISIBLE) return;
        bar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailSession(String errorInfo, int failCode) {
        textView.setText(errorInfo);
        boolean validNetwork = NetworkUtil.isConnectedOrConnecting(getContext());
        if(validNetwork) return;
        textView.setText(getContext().getString(R.string.invalid_network_state));
        textView.setCompoundDrawables(null, getNoNetDrawable(), null, null);
    }

    @Override
    public void onParseSuccess(T t) {
        badgeFrame.hide();
    }

    public void setVisibility(boolean show){
        badgeFrame.setVisibility(show);
    }

    public BadgeFrame getBadgeFrame() {
        return badgeFrame;
    }

    public Context getContext(){
        return target.getContext();
    }

    protected View getTarget() {
        return target;
    }

    protected TextView getTextView() {
        return textView;
    }

    protected Drawable getNoNetDrawable() {
        if(noNetDrawable == null){
            noNetDrawable = getTarget().getResources().getDrawable(R.drawable.invalid_network_bg);
            noNetDrawable.setBounds(0, 0, noNetDrawable.getMinimumWidth(), noNetDrawable.getMinimumHeight());
        }
        return noNetDrawable;
    }

}
