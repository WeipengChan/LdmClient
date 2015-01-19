package com.ldm.ldmclient.app;

/**
 * 接口统一处理地址
 * Created by LDM on 13-12-31. Email : nightkid-s@163.com
 */
public final class Server {
    /**
     * 域名
     */
    private static final String MAIN_DOMAIN = "http://test.com/";
    /**
     * 测试域名
     */
    private static final String TEST_DOMAIN = "";
    private static final String TEST_DOMAIN1 = "";

    /**
     * 接口
     */
    public static final String API_TEST = "test";

    /**
     * 获取主域名
     */
    public static String getDomain() {
        return MAIN_DOMAIN;
    }
}
