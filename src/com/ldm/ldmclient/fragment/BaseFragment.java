package com.ldm.ldmclient.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.ldm.ldmclient.annotation.AnnotationHandler;

/**
 *
 * Created by LDM on 14-4-2. Email : nightkid-s@163.com
 */
public abstract class BaseFragment extends Fragment {

    private View view;
    private OnFmLoadListener onFmLoadListener;
    public abstract View getView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnFmLoadListener)
            onFmLoadListener = (OnFmLoadListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(onFmLoadListener != null) onFmLoadListener.OnLoadListener(this);
        if(view == null) view = getView(inflater, container, savedInstanceState);
        else if(view.getParent() != null) ((ViewGroup)view.getParent()).removeView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AnnotationHandler.initTitleBar(this);
    }

    protected void displayToast(String toast){
        Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(onFmLoadListener != null) onFmLoadListener.OnLoadListener(this);
    }

    /**when there was a network callback to update UI thread, call this function instead of {@link #getView()},
     * because the reference of {@link #getView()} may be recycled after {@link #onDestroyView()}**/
    public View getSyncView(){
        return view;
    }
    
    public void setOnFmLoadListener(OnFmLoadListener onFmLoadListener1){
    	this.onFmLoadListener = onFmLoadListener1;
    }

    public interface OnFmLoadListener {
        void OnLoadListener(Fragment fragment);
    }
}
