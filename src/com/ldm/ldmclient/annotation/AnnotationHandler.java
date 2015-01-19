package com.ldm.ldmclient.annotation;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.ldm.ldmclient.R;
import com.ldm.ldmclient.singleton.CloseActivityListener;

/**
 * 处理反射的工具类
 * Created by LDM on 14-3-18. Email : nightkid-s@163.com
 */
public class AnnotationHandler {

    /**
     * 处理back的
     * @param activity 界面容器
     */
    public static void initTitleBar(final Activity activity) {

        //Class<?> clazz = Class.forName(activity.getClass().getName());
        Class<?> clazz = activity.getClass();
        if (!clazz.isAnnotationPresent(HandleTitleBar.class)) return;
        HandleTitleBar handler = clazz.getAnnotation(HandleTitleBar.class);
        //后退键
        TextView back = (TextView) activity.findViewById(R.id.cancel_btn);
        if (back != null) {
            int backTextId = handler.showBackText();
            back.setVisibility(handler.showBackBtn() || backTextId > 0 ? View.VISIBLE : View.GONE);
            if (handler.showBackBtn() || backTextId > 0) back.setOnClickListener(CloseActivityListener.getInstance());
            if (backTextId > 0) back.setText(backTextId);
        }
        //title text
        TextView titleText = (TextView) activity.findViewById(R.id.text_title_id);
        int titleTextId = handler.showTitleText();
        if (titleText != null && titleTextId > 0) titleText.setText(titleTextId);
        //right text
        TextView rightText = (TextView) activity.findViewById(R.id.right_text_id);
        int rightTextId = handler.showRightText();
        if (rightText != null && rightTextId > 0) rightText.setText(rightTextId);
    }

    /**
     * 处理back的
     * @param fragment 界面容器
     */
    public static void initTitleBar(final Fragment fragment) {

        //Class<?> clazz = Class.forName(activity.getClass().getName());
        Class<?> clazz = ((Object)fragment).getClass();
        if(fragment.getView() == null) return;
        if (!clazz.isAnnotationPresent(HandleTitleBar.class)) return;
        HandleTitleBar handler = clazz.getAnnotation(HandleTitleBar.class);
        //后退键
        TextView back = (TextView) fragment.getView().findViewById(R.id.cancel_btn);
        if (back != null) {
            int backTextId = handler.showBackText();
            back.setVisibility(handler.showBackBtn() || backTextId > 0 ? View.VISIBLE : View.GONE);
            if (handler.showBackBtn() || backTextId > 0) back.setOnClickListener(CloseActivityListener.getInstance());
            if (backTextId > 0) back.setText(backTextId);
        }
        //title text
        TextView titleText = (TextView) fragment.getView().findViewById(R.id.text_title_id);
        int titleTextId = handler.showTitleText();
        if (titleText != null && titleTextId > 0) titleText.setText(titleTextId);
        //right text
        TextView rightText = (TextView) fragment.getView().findViewById(R.id.right_text_id);
        int rightTextId = handler.showRightText();
        if (rightText != null && rightTextId > 0) rightText.setText(rightTextId);
    }
}
