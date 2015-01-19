package com.ldm.ldmclient.util;

import android.util.SparseArray;

/**
 * ready some data for test
 * Created by LDM on 2015/1/7. Email : nightkid-s@163.com
 */
public class TestData {

    private static SparseArray<String> sparseArray = new SparseArray<String>(10);

    public static String getData(String url){
        if(sparseArray.size() < 1){
            sparseArray.put(page1.hashCode(), page1Data);
            sparseArray.put(page2.hashCode(), page2Data);
            sparseArray.put(page3.hashCode(), page3Data);
            sparseArray.put(page4.hashCode(), page4Data);
            sparseArray.put(page5.hashCode(), page5Data);
            sparseArray.put(page6.hashCode(), page6Data);
            sparseArray.put(page7.hashCode(), page7Data);
            sparseArray.put(page8.hashCode(), page8Data);
            sparseArray.put(page9.hashCode(), page9Data);
            sparseArray.put(page10.hashCode(), page10Data);
        }

        String data = sparseArray.get(url.hashCode(), "");
        LogUtil.d("http_post_request", url);
        LogUtil.d("http_post_result", data);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }

    //test url
    public static final String page1 = "http://test.com/test?page=1&httpPostParams=test";
    public static final String page2 = "http://test.com/test?page=2&httpPostParams=test";
    public static final String page3 = "http://test.com/test?page=3&httpPostParams=test";
    public static final String page4 = "http://test.com/test?page=4&httpPostParams=test";
    public static final String page5 = "http://test.com/test?page=5&httpPostParams=test";
    public static final String page6 = "http://test.com/test?page=6&httpPostParams=test";
    public static final String page7 = "http://test.com/test?page=7&httpPostParams=test";
    public static final String page8 = "http://test.com/test?page=8&httpPostParams=test";
    public static final String page9 = "http://test.com/test?page=9&httpPostParams=test";
    public static final String page10 = "http://test.com/test?page=10&httpPostParams=test";

    //test data
    //public static final String page1Data = "{\"data_list\":[],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page1Data = "{\"data_list\":[{\"title\":\"title1\",\"content\":\"content1\"}, {\"title\":\"title1\",\"content\":\"content1\"},{\"title\":\"title1\",\"content\":\"content1\"},{\"title\":\"title1\",\"content\":\"content1\"},{\"title\":\"title1\",\"content\":\"content1\"},{\"title\":\"title1\",\"content\":\"content1\"},{\"title\":\"title1\",\"content\":\"content1\"},{\"title\":\"title1\",\"content\":\"content1\"},{\"title\":\"title1\",\"content\":\"content1\"},{\"title\":\"title1\",\"content\":\"content1\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page2Data = "{\"data_list\":[{\"title\":\"title2\",\"content\":\"content2\"}, {\"title\":\"title2\",\"content\":\"content2\"},{\"title\":\"title2\",\"content\":\"content2\"},{\"title\":\"title2\",\"content\":\"content2\"},{\"title\":\"title2\",\"content\":\"content2\"},{\"title\":\"title2\",\"content\":\"content2\"},{\"title\":\"title2\",\"content\":\"content2\"},{\"title\":\"title2\",\"content\":\"content2\"},{\"title\":\"title2\",\"content\":\"content2\"},{\"title\":\"title2\",\"content\":\"content2\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page3Data = "{\"data_list\":[{\"title\":\"title3\",\"content\":\"content3\"}, {\"title\":\"title3\",\"content\":\"content3\"},{\"title\":\"title3\",\"content\":\"content3\"},{\"title\":\"title3\",\"content\":\"content3\"},{\"title\":\"title3\",\"content\":\"content3\"},{\"title\":\"title3\",\"content\":\"content3\"},{\"title\":\"title3\",\"content\":\"content3\"},{\"title\":\"title3\",\"content\":\"content3\"},{\"title\":\"title3\",\"content\":\"content3\"},{\"title\":\"title3\",\"content\":\"content3\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page4Data = "{\"data_list\":[{\"title\":\"title4\",\"content\":\"content4\"}, {\"title\":\"title4\",\"content\":\"content4\"},{\"title\":\"title4\",\"content\":\"content4\"},{\"title\":\"title4\",\"content\":\"content4\"},{\"title\":\"title4\",\"content\":\"content4\"},{\"title\":\"title4\",\"content\":\"content4\"},{\"title\":\"title4\",\"content\":\"content4\"},{\"title\":\"title4\",\"content\":\"content4\"},{\"title\":\"title4\",\"content\":\"content4\"},{\"title\":\"title4\",\"content\":\"content4\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page5Data = "{\"data_list\":[{\"title\":\"title5\",\"content\":\"content5\"}, {\"title\":\"title5\",\"content\":\"content5\"},{\"title\":\"title5\",\"content\":\"content5\"},{\"title\":\"title5\",\"content\":\"content5\"},{\"title\":\"title5\",\"content\":\"content5\"},{\"title\":\"title5\",\"content\":\"content5\"},{\"title\":\"title5\",\"content\":\"content5\"},{\"title\":\"title5\",\"content\":\"content5\"},{\"title\":\"title5\",\"content\":\"content5\"},{\"title\":\"title5\",\"content\":\"content5\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page6Data = "{\"data_list\":[{\"title\":\"title6\",\"content\":\"content6\"}, {\"title\":\"title6\",\"content\":\"content6\"},{\"title\":\"title6\",\"content\":\"content6\"},{\"title\":\"title6\",\"content\":\"content6\"},{\"title\":\"title6\",\"content\":\"content6\"},{\"title\":\"title6\",\"content\":\"content6\"},{\"title\":\"title6\",\"content\":\"content6\"},{\"title\":\"title6\",\"content\":\"content6\"},{\"title\":\"title6\",\"content\":\"content6\"},{\"title\":\"title6\",\"content\":\"content6\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page7Data = "{\"data_list\":[{\"title\":\"title7\",\"content\":\"content7\"}, {\"title\":\"title7\",\"content\":\"content7\"},{\"title\":\"title7\",\"content\":\"content7\"},{\"title\":\"title7\",\"content\":\"content7\"},{\"title\":\"title7\",\"content\":\"content7\"},{\"title\":\"title7\",\"content\":\"content7\"},{\"title\":\"title7\",\"content\":\"content7\"},{\"title\":\"title7\",\"content\":\"content7\"},{\"title\":\"title7\",\"content\":\"content7\"},{\"title\":\"title7\",\"content\":\"content7\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page8Data = "{\"data_list\":[{\"title\":\"title8\",\"content\":\"content8\"}, {\"title\":\"title8\",\"content\":\"content8\"},{\"title\":\"title8\",\"content\":\"content8\"},{\"title\":\"title8\",\"content\":\"content8\"},{\"title\":\"title8\",\"content\":\"content8\"},{\"title\":\"title8\",\"content\":\"content8\"},{\"title\":\"title8\",\"content\":\"content8\"},{\"title\":\"title8\",\"content\":\"content8\"},{\"title\":\"title8\",\"content\":\"content8\"},{\"title\":\"title8\",\"content\":\"content8\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page9Data = "{\"data_list\":[{\"title\":\"title9\",\"content\":\"content9\"}, {\"title\":\"title9\",\"content\":\"content9\"},{\"title\":\"title9\",\"content\":\"content9\"},{\"title\":\"title9\",\"content\":\"content9\"},{\"title\":\"title9\",\"content\":\"content9\"},{\"title\":\"title9\",\"content\":\"content9\"},{\"title\":\"title9\",\"content\":\"content9\"},{\"title\":\"title9\",\"content\":\"content9\"},{\"title\":\"title9\",\"content\":\"content9\"},{\"title\":\"title9\",\"content\":\"content9\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";
    public static final String page10Data = "{\"data_list\":[{\"title\":\"title10\",\"content\":\"content10\"}, {\"title\":\"title10\",\"content\":\"content10\"},{\"title\":\"title10\",\"content\":\"content10\"},{\"title\":\"title10\",\"content\":\"content10\"},{\"title\":\"title10\",\"content\":\"content10\"},{\"title\":\"title10\",\"content\":\"content10\"},{\"title\":\"title10\",\"content\":\"content10\"},{\"title\":\"title10\",\"content\":\"content10\"},{\"title\":\"title10\",\"content\":\"content10\"}],\"error_code\":\"1\",\"error_info\":\"成功\",\"request_url\":\"test.api\"}";

}
