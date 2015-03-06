package com.ldm.ldmclient.util.http;

import com.ldm.ldmclient.bean.NameValueParams;
import com.ldm.ldmclient.exception.TypeMisMatchException;
import com.ldm.ldmclient.util.LogUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * http链接方法
 * Created by LDM on 2014/6/26. Email : nightkid-s@163.com
 */
public class HttpTool {

    private static final int connManagerTimeout = 1000; //线程池取出超时
    private static final int connectTimeout = 6000; //连接超时
    private static final int socketTimeout = 6000; //socket超时
    private static final String CHARSET = HTTP.UTF_8; //字符编码集
    private static HttpClient customerHttpClient; //连接参数设置
    public static final int MAX_ROUTE_NUM = 5; //设置每个路由最大连接数

    /**
     * 请求之前，初始化一些HttpClient基本参数
     * @return DefaultHttpClient
     */
    public static synchronized HttpClient getHttpClient() {
        if (null == customerHttpClient) {
            HttpParams params = new BasicHttpParams();
            params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            HttpProtocolParams.setUserAgent(params, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
                            + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            //从连接池中取连接的超时时间
            ConnManagerParams.setTimeout(params, connManagerTimeout);
            //连接超时
            HttpConnectionParams.setConnectionTimeout(params, connectTimeout);
            //请求等待超时
            HttpConnectionParams.setSoTimeout(params, socketTimeout);
            //设置每个路由最大连接数
            ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(MAX_ROUTE_NUM));
            // 设置我们的HttpClient支持HTTP和HTTPS两种模式
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            // 使用线程安全的连接管理来创建HttpClient
            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
            customerHttpClient = new DefaultHttpClient(conMgr, params);
        }
        return customerHttpClient;
    }

    /**
     * get请求方法
     * @param url 连接地址
     * @param params 附加参数
     * @return 请求结果
     * @throws RuntimeException 处理（请求失败，字符码不支持，连接协议不支持，连接失败）失败应该统一抛出的异常
     */
    public static String get(String url, List<NameValueParams> params) throws RuntimeException {
        url = constructUrl(url, params);
        LogUtil.d("http_get_url", url);
        try {
            HttpGet request = new HttpGet(url);
            HttpResponse response = getHttpClient().execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                throw new RuntimeException("请求失败");
            HttpEntity resEntity = response.getEntity();
            if(resEntity == null)
                throw new RuntimeException("返回结果为空");
            String result = EntityUtils.toString(resEntity, CHARSET);
            LogUtil.d("http_get_result： " , result == null ? "result == null" :  result);
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("请求连接返回不支持的字符编码类型", e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("不支持该连接采用的client协议", e);
        } catch (SocketTimeoutException e){
            throw new RuntimeException("连接超时", e);
        } catch (ConnectTimeoutException e){
            throw new RuntimeException("连接超时", e);
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        }

    }

    /**
     * 简单的post请求， 请求参数和返回值都只含有字符类型
     * @param url 请求地址
     * @param params 参数集合
     * @return 返回结果
     * @throws RuntimeException 处理（请求失败，字符码不支持，连接协议不支持，连接失败）失败应该统一抛出的异常
     */
    public static String simplePost(String url, List<NameValueParams> params) throws RuntimeException{
        LogUtil.d("http_post_url", constructUrl(url, params));
        try {
            // 编码参数，进行了一次url encode转换
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, CHARSET);
            // 创建POST请求
            HttpPost request = new HttpPost(url);
            request.setEntity(entity);
            // 发送请求
            HttpResponse response = getHttpClient().execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                throw new RuntimeException("请求失败");
            HttpEntity resEntity = response.getEntity();
            if(resEntity == null)
                throw new RuntimeException("返回结果为空");
            String result = EntityUtils.toString(resEntity, CHARSET);
            LogUtil.d("http_post_result： " , result == null ? "result == null" :  result);
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("请求连接返回不支持的字符编码类型", e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("不支持该连接采用的client协议", e);
        }catch (ConnectTimeoutException e){
            throw new RuntimeException("连接超时", e);
        } catch (SocketTimeoutException e){
            throw new RuntimeException("连接超时", e);
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        }

    }

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
            HttpPost httppost = new HttpPost(url);
            MultipartEntity mpEntity = new MultipartEntity(); //文件传输
            for (NameValueParams pair : params){
                switch (pair.getType()){
                    case TEXT_:
                        mpEntity.addPart(pair.getName(), new StringBody(pair.getValue(), Charset.forName(CHARSET)));
                        break;
                    case FILE_:
                        mpEntity.addPart(pair.getName(), new FileBody(pair.getFile())); // <input type="file" name="~" />
                        break;
                    case BYTE_ARRAY_:
                        mpEntity.addPart(pair.getName(), pair.getByteArrayBody());
                        break;
                    case INPUT_STREAM_:
                        mpEntity.addPart(pair.getName(), pair.getInputStreamBody());
                        break;
                }
            }
            httppost.setEntity(mpEntity);
            HttpResponse response = getHttpClient().execute(httppost);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                throw new RuntimeException("请求失败");
            HttpEntity resEntity = response.getEntity();
            if (resEntity == null)
                throw new RuntimeException("返回结果为空");
            String result = EntityUtils.toString(resEntity, CHARSET);
            LogUtil.d("http_post_result： ", result == null ? "result == null" : result);
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("请求连接返回不支持的字符编码类型", e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException("不支持该连接采用的client协议", e);
        } catch (SocketTimeoutException e) {
            throw new RuntimeException("连接超时", e);
        } catch (ConnectTimeoutException e) {
            throw new RuntimeException("连接超时", e);
        } catch (TypeMisMatchException e){
            throw new RuntimeException("获取的参数类型不匹配");
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
