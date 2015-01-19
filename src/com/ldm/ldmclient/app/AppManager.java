package com.ldm.ldmclient.app;

import android.app.Application;
import android.util.TypedValue;

/**
 * application for whole app,
 * pre load some important var
 * Created by LDM on 2014/6/23. Email : nightkid-s@163.com
 */
public class AppManager extends Application{

    private static AppManager instance;
    private float padding = 0;
    private float density = 0;

    public static AppManager getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public float getFloatPadding() {
    	 return padding <= 0 ? padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()) : padding;
	}
    
    public float getDensity() {
    	 return density <= 0 ? density = getResources().getDisplayMetrics().density : density;
	}

}
