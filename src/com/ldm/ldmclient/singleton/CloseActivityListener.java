package com.ldm.ldmclient.singleton;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * singleton close activity OnClickListener
 * Created by LDM on 14-2-28. Email : nightkid-s@163.com
 */
public class CloseActivityListener implements View.OnClickListener {

    private static CloseActivityListener instance = new CloseActivityListener(); //饿汉单例模式

    private CloseActivityListener() {
    }

    public static CloseActivityListener getInstance() {
        return instance;
    }

    @Override
    public void onClick(View view) {
        if(view ==  null) return;
        Context context = view.getContext();
        if(context instanceof Activity) ((Activity)context).onBackPressed();
    }
}
