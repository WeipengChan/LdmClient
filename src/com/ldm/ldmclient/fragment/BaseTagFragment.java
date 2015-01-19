package com.ldm.ldmclient.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * 基于tag方式管理fragment
 * Created by LDM on 14-4-9. Email : nightkid-s@163.com
 */
public abstract class BaseTagFragment extends BaseFragment{

    private Fragment currentFmt;

    protected abstract Fragment getFragment(String tag);

    /**
     * manage the fragment by attach and detach, any attaching action should execute after detaching action
     * @param containerResId view container
     * @param tag fragment tag
     */
    protected void showFragment(int containerResId, String tag){
        if(currentFmt != null && tag.equals(currentFmt.getTag())) return; //当前显示fragment一致，无需改动
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        if(currentFmt != null) ft.detach(currentFmt);
        Fragment f = getChildFragmentManager().findFragmentByTag(tag);
        currentFmt = f == null ? getFragment(tag) : f;
        if(f == null) ft.add(containerResId, currentFmt, tag);
        else ft.attach(f);
        ft.commit();
    }

    public Fragment getCurrentFmt() {
        return currentFmt;
    }

}
