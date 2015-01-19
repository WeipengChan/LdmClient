package com.ldm.ldmclient.adapter;

/**
 * 网络适配器加载状态
 * Created by LDM on 14-1-10. Email : nightkid-s@163.com
 */
public enum Status {
    /**
     * Indicates that the process has not been executed yet.可接受数据状态
     */
    PENDING,
    /**
     * Indicates that the process is running. 正在运行的状态，不可接受数据
     */
    RUNNING,
    /**
     * Indicates that process has finished. 不再接受数据
     */
    FINISHED,
    /**
     * 暂停接受数据
     */
    HOLDING,
}
