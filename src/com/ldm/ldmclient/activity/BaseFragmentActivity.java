package com.ldm.ldmclient.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.ldm.ldmclient.annotation.AnnotationHandler;
import com.ldm.ldmclient.app.AppManager;
import com.ldm.ldmclient.app.Constant;

/**
 * BaseFragmentActivity
 * Created by LDM on 2014/6/26. Email : nightkid-s@163.com
 */
public class BaseFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.ACTION_EXIT);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
		initView(savedInstanceState);
		AnnotationHandler.initTitleBar(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
	}

	/**
	 * you should set content view here, for global title setting
	 * @param savedInstanceState onCreate(Bundle savedInstanceState)
	 */
	public void initView(Bundle savedInstanceState) {}

	protected void displayToast(String text) {
		displayToast(text, Toast.LENGTH_SHORT);
	}

	protected void displayToast(String text, int duration) {
		if (TextUtils.isEmpty(text))
			return;
		Toast.makeText(this, text, duration).show();
	}

	protected AppManager getApp() {
		return AppManager.getInstance();
	}

	public void detachFragment(Fragment fragment){
		if(fragment != null && !fragment.isDetached())
			getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).detach(fragment).commit();
	}

    //broadcast of finish
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
}
