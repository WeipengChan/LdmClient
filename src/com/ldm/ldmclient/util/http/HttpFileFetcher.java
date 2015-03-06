package com.ldm.ldmclient.util.http;

import com.ldm.ldmclient.bean.NameValueParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

import static com.ldm.ldmclient.util.http.OnFileProgressListener.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * 文件下载器
 * Created by LDM on 2015/2/28. Email : nightkid-s@163.com
 */
public class HttpFileFetcher extends HttpFetcher {

    public static void fetch(String url, List<NameValueParams> params, OnFileProgressListener listener) throws RuntimeException {
        fetch(constructUrl(url, params, true), listener);
    }

    //下载，不支持断点
    public static void fetch(String url, OnFileProgressListener listener) throws RuntimeException {
        System.out.println("http_post_url" +  url);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            if(conn.getResponseCode() != HTTP_OK)
                throw new RuntimeException(String.format("处理下载失败,状态码 : %d", conn.getResponseCode()));
            int ctLength = conn.getContentLength();
            if(ctLength < 0)
                throw new RuntimeException("下载文件的长度不合法!");
            getContent(conn, listener);
        } catch (SocketTimeoutException e) {
            throw new RuntimeException("连接超时", e);
        } catch (MalformedURLException e) {
            throw new RuntimeException("请求失败", e);
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        } finally {
            if(listener != null) try {
                listener.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void partialFetch(String url, List<NameValueParams> params, long offset, OnFileProgressListener listener) throws RuntimeException {
        partialFetch(constructUrl(url, params, true), offset, listener);
    }

    //断点续传
    public static void partialFetch(String url, long offset, OnFileProgressListener listener) throws RuntimeException {
        System.out.println("http_post_url" +  url);
        try {
            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setRequestProperty("Range", "bytes=" + offset + "-");
            if(conn.getResponseCode() != HTTP_OK)
                throw new RuntimeException(String.format("处理下载失败,状态码 : %d", conn.getResponseCode()));

            int ctLength = conn.getContentLength();
            if(ctLength < 0)
                throw new RuntimeException("下载文件的长度不合法!");
            getContent(conn, listener);
        } catch (SocketTimeoutException e) {
            throw new RuntimeException("连接超时", e);
        } catch (MalformedURLException e) {
            throw new RuntimeException("请求失败", e);
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        } finally {
            if(listener != null) try {
                listener.finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getContent(HttpURLConnection conn, OnFileProgressListener listener) throws IOException {
        InputStream is = conn.getInputStream();
        long len = (long)conn.getContentLength();
        byte[] buff = new byte[BUFF_LEN];
        int read;
        while ((read = is.read(buff)) != -1){
            if(listener != null)
                listener.write(buff, 0, read, len);
        }
        is.close();
    }
}
