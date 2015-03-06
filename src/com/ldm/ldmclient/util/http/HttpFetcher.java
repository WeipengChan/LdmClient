package com.ldm.ldmclient.util.http;

import com.ldm.ldmclient.bean.NameValueParams;
import com.ldm.ldmclient.exception.TypeMisMatchException;
import org.apache.http.NameValuePair;

import java.io.*;
import java.net.*;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * http通用工具库
 * Created by LDM on 2014/6/26. Email : nightkid-s@163.com
 */
public class HttpFetcher {

    public static final int connectTimeout = 6000; //连接超时
    public static final int socketTimeout = 6000; //socket超时
    public static final String CHARSET = "UTF-8"; //字符编码集
    public static final String BOUNDARY = "***********************";
    public static final String TWO_HYPHENS = "--";
    public static final String CRLF = "\r\n";
    public static final int BUFF_SIZE = 300 * 1024;

    /**
     * 支持附带文件的post请求方式
     * @param url 请求地址
     * @param params 字符参数 包含了带文件路径的参数
     * @return 返回结果
     * @throws RuntimeException
     */
    public static String post(String url, List<NameValueParams> params) throws RuntimeException {
        System.out.println("http_post_url" +  constructUrl(url, params));
        try {
            //region start construct connection
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(socketTimeout);
            conn.setConnectTimeout(connectTimeout);
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Connection", "Keep-Alive");
            conn.addRequestProperty("Accept-Charset", CHARSET);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.addRequestProperty("User-Agent", "Mozilla/5.0(Linux;U;Android 4.4.2;en-us;Nexus 5) "
                    + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            conn.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            for (NameValueParams pair : params){
                os.writeBytes(CRLF + TWO_HYPHENS + BOUNDARY + CRLF);
                switch (pair.getType()){
                    case TEXT_:
                        os.writeBytes("Content-Disposition: form-data; name=\"" + pair.getName() + "\"" + CRLF);
                        os.writeBytes("Content-Type: text/plain" + "; charset="  + CHARSET + CRLF);
                        os.writeBytes("Content-Transfer-Encoding: 8bit" + CRLF + CRLF);
                        os.write(pair.getValue().getBytes(CHARSET));
                        break;
                    case FILE_:
                        os.writeBytes("Content-Disposition: form-data; name=\"" + pair.getName() + "\";filename=\"" + pair.getValue() + "\"" + CRLF);
                        os.writeBytes("Content-Type: application/octet-stream" + CRLF);
                        os.writeBytes("Content-Transfer-Encoding: binary" + CRLF + CRLF);
                        FileInputStream is = new FileInputStream(pair.getFile());
                        byte[] bytes = new byte[BUFF_SIZE];
                        int len;
                        while ((len = is.read(bytes)) != -1)
                            os.write(bytes, 0, len);
                        is.close();
                        break;
                    case BYTE_ARRAY_:
                        os.writeBytes("Content-Disposition: form-data; name=\"" + pair.getName() + "\";filename=\"" + pair.getValue() + "\"" + CRLF);
                        os.writeBytes("Content-Type: application/octet-stream" + CRLF);
                        os.writeBytes("Content-Transfer-Encoding: binary" + CRLF + CRLF);
                        pair.getByteArrayBody().writeTo(os);
                        break;
                    case INPUT_STREAM_:
                        os.writeBytes("Content-Disposition: form-data; name=\"" + pair.getName() + "\";filename=\"" + pair.getValue() + "\"" + CRLF);
                        os.writeBytes("Content-Type: application/octet-stream" + CRLF);
                        os.writeBytes("Content-Transfer-Encoding: binary" + CRLF + CRLF);
                        InputStream ips = pair.getInputStreamBody().getInputStream();
                        byte[] bs = new byte[BUFF_SIZE];
                        int length;
                        while ((length = ips.read(bs)) != -1)
                            os.write(bs, 0, length);
                        ips.close();
                        break;
                }
            }
            //endregion

            //region end connection
            os.writeBytes(CRLF + TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + CRLF);
            os.flush();
            os.close();
            //endregion

            //region get response
            if(conn.getResponseCode() != HTTP_OK)
                throw new RuntimeException(String.format("处理下载失败,状态码 : %d", conn.getResponseCode()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder(30);
            String result;
            while ((result = reader.readLine()) != null)
                sb.append(result);
            result = sb.toString();
            System.out.println("http_post_result： " + result);
            return result;
            //endregion
        } catch (SocketTimeoutException e) {
            throw new RuntimeException("连接超时", e);
        } catch (MalformedURLException e) {
            throw new RuntimeException("请求失败", e);
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        } catch (TypeMisMatchException e) {
            throw new RuntimeException("获取的参数类型不匹配", e);
        }
    }

    public static String get(String url, List<NameValueParams> params) throws RuntimeException {
        return get(constructUrl(url, params, true));
    }

    public static String get(String url) throws RuntimeException {
        System.out.println("http_post_url: " +  url);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(socketTimeout);
            conn.setConnectTimeout(connectTimeout);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() != HTTP_OK)
                throw new RuntimeException(String.format("处理下载失败,状态码 : %d", conn.getResponseCode()));
            StringBuilder sb = new StringBuilder(30);
            String result;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((result = reader.readLine()) != null)
                sb.append(result);
            result = sb.toString();
            System.out.println("http_post_result： " + result);
            return result;
        } catch (SocketTimeoutException e) {
            throw new RuntimeException("连接超时", e);
        } catch (MalformedURLException e) {
            throw new RuntimeException("请求失败", e);
        } catch (IOException e) {
            throw new RuntimeException("连接失败", e);
        }
    }

    public static String constructUrl(String url, List<NameValueParams> params){
        return constructUrl(url, params, false);
    }

    /**
     * 拼接post显式请求连接，多用于复制入浏览器查看请求结果。
     * @param url 请求连接
     * @param params 请求参数
     * @param encodeParam 对参数进行编码
     * @return 返回结果
     */
    public static String constructUrl(String url, List<NameValueParams> params, boolean encodeParam){
        StringBuilder sb = new StringBuilder(url == null ? "" : url).append("?");
        for (int i = 0 ; i < ((params == null) ? 0 : params.size()); i ++){
            NameValuePair pair = params.get(i);
            try {
                String param = encodeParam ? URLEncoder.encode(pair.getValue(), CHARSET) : pair.getValue(); //抽变量出来，防止append一些无谓字符如=号后抛异常
                sb.append(pair.getName()).append("=").append(param).append("&");
            } catch (UnsupportedEncodingException e) {
                sb.append(pair.getName()).append("=").append(pair.getValue()).append("&");
            }
        }
        if(params != null && params.size() > 0) sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
