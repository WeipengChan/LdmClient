package com.ldm.ldmclient.util;

import com.ldm.ldmclient.bean.NameValueParams;
import org.apache.http.NameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * http链接方法
 * Created by LDM on 2014/6/26. Email : nightkid-s@163.com
 */
public class HttpTool2 {

    private static final int connectTimeout = 6000; //连接超时
    private static final int socketTimeout = 6000; //socket超时
    private static final String CHARSET = HTTP.UTF_8; //字符编码集
    public static final String BOUNDARY = "----------V2ymHFg03ehbqgZCaKO6jy";

    /**
     * 支持附带文件的post请求方式
     * @param url 请求地址
     * @param params 字符参数 包含了带文件路径的参数
     * @return 返回结果
     * @throws RuntimeException
     */
    public static String post(String url, List<NameValueParams> params) throws RuntimeException {
        LogUtil.d("http_post_url", constructUrl(url, params));
        try {
            URL u = new URL(constructUrl(url, params));
            HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            httpURLConnection.addRequestProperty("charset", CHARSET);
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0(Linux;U;Android 4.4.2;en-us;Nexus 5 Build.FRG83) "
                    + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            httpURLConnection.setReadTimeout(socketTimeout);
            httpURLConnection.setConnectTimeout(connectTimeout);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            String result = httpURLConnection.getResponseMessage();
            return result;
        } catch (MalformedURLException e) {
            throw new RuntimeException("请求失败");
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        }
    }

    /**
     * 拼接post显式请求连接，多用于复制入浏览器查看请求结果。
     * @param url 请求连接
     * @param params 请求参数
     * @return 返回结果
     */
    public static String constructUrl(String url, List<NameValueParams> params){
        StringBuilder sb = new StringBuilder(url == null ? "" : url).append("?");
        for (int i = 0 ; i < ((params == null) ? 0 : params.size()); i ++){
            NameValuePair pair = params.get(i);
            sb.append(pair.getName()).append("=").append(pair.getValue()).append("&");
            if(i == params.size() - 1) sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
