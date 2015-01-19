package com.ldm.ldmclient.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * show fragment by tag
 * Created by LDM on 2014/8/15. Email : nightkid-s@163.com
 */
public abstract class TagFragmentActivity extends BaseFragmentActivity{

    private Fragment currentFmt;

    protected abstract Fragment getFragment(String tag);

    protected void showFragment(int containerResId, String tag){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        showFragment(containerResId, tag, ft);
        ft.commit();
    }

    /**
     * manage the fragment by attach and detach, any attaching action should execute after detaching action
     * @param containerResId view container
     * @param tag fragment tag
     */
    protected void showFragment(int containerResId, String tag, FragmentTransaction ft){
        if(currentFmt != null && tag.equals(currentFmt.getTag())) return; //same fragment, no need to show this fragment
        if(currentFmt != null) ft.detach(currentFmt);
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        currentFmt = f == null ? getFragment(tag) : f;
        if(f == null) ft.add(containerResId, currentFmt, tag);
        else ft.attach(f);
        ft.commit();
    }

    public Fragment getCurrentFmt() {
        return currentFmt;
    }
}
