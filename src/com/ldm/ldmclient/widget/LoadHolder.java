package com.ldm.ldmclient.widget;

import android.view.View;
import com.ldm.ldmclient.request.Loader;

/**
 * Created by LDM on 2015/1/10. Email : nightkid-s@163.com
 */
public class LoadHolder<T> extends InitLoadHolder<T>{

    public LoadHolder(Loader<T> request, View target) {
        super(request, target);
    }

}
