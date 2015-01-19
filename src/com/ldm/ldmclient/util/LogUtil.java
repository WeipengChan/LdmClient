package com.ldm.ldmclient.util;

import android.util.Log;
import com.ldm.ldmclient.app.Constant;

/**
 * 调试工具类
 * 建议用此代替默认的Log工具，正式发布的时候可以关闭应用调试输出
 * Created by LDM on 2014/6/26. Email : nightkid-s@163.com
 */
public class LogUtil {
    /**
     * 全局调试总开关
     */
    public static final boolean DEBUG = Constant.DebugMode;
    public static final String DEFAULT_LOG_D_TAG = "Log.d_default_tag";
    public static final String DEFAULT_LOG_I_TAG = "Log.i_default_tag";
    public static final String DEFAULT_LOG_E_TAG = "Log.e_default_tag";

    /**
     * 跟Log.d (String, String)功能一样，额外加了一个输出开关，
     * 建议适用情况：调试信息需要输出变量值
     */
    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    /**
     * 跟Log. i (String, String)功能一样，额外加了一个输出开关，
     * 建议适用情况：调试信息无需输出变量，用来跟踪代码运行位置，反馈固定log信息
     */
    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }

    /**
     * 跟Log.e (String, String)功能一样，额外加了一个输出开关，
     * 建议适用情况：跟踪程序可能错误、异常和逻辑问题
     */
    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    /**
     * 默认的通用输出debug类型的调试信息，只需要填入输出信息即可，
     */
    public static void d(String msg) {

        d(DEFAULT_LOG_D_TAG, msg);
    }

    /**
     * 默认的通用输出info类型的调试信息，只需要填入输出信息即可，
     */
    public static void i(String msg) {

        i(DEFAULT_LOG_I_TAG, msg);
    }

    /**
     * 默认的通用输出error类型的调试信息，只需要填入输出信息即可，
     */
    public static void e(String msg) {

        e(DEFAULT_LOG_E_TAG, msg);
    }

    /**
     * 默认的通用输出error类型的调试信息，只需要填入输出信息即可，这里加上类型信息，方便定位错误位置
     */
    public static void e(Class clazz, String msg) {

        e(DEFAULT_LOG_E_TAG, msg + " 异常所处位置信息：" + clazz.getName());
    }

}
