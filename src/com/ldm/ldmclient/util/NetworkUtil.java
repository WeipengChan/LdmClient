package com.ldm.ldmclient.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具
 * Created by LDM on 14-1-14. Email : nightkid-s@163.com
 */
public class NetworkUtil {

    /**
     * 检查是否有网络链接
     * @param context context
     * @return 结果
     */
    public static boolean isConnectedOrConnecting(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            LogUtil.e("checkConnection - no connection found");
            return false;
        }
        return true;
    }

    /**
     * 检查是否有有效的网络连接
     * @param context context
     * @return 结果
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            LogUtil.e("checkConnection - no connection found");
            return false;
        }
        return true;
    }
}
