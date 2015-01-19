package com.ldm.ldmclient.bean;

import com.ldm.ldmclient.exception.TypeMisMatchException;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.File;

/**
 * 请求参数的键值对实现， 每次实例化只有参数类型和键名称和对应参数类型被赋值
 * Created by LDM on 13-12-31. Email : nightkid-s@163.com
 */
public class NameValueParams implements NameValuePair {

    private HttpParamType type; //参数类型
    private String name; //键名称
    private String value; //字符值参数
    private File file; //文件参数
    private ByteArrayBody byteArrayBody;//字节数组参数
    private InputStreamBody inputStreamBody;//输入流参数

    public NameValueParams(String name, String value) {
        this(HttpParamType.TEXT_, name, value, null, null, null);
    }

    public NameValueParams(String name, File file) {
        this(HttpParamType.FILE_, name, null, file, null, null);
    }

    public NameValueParams(String name, ByteArrayBody byteArrayBody) {
        this(HttpParamType.BYTE_ARRAY_, name, null, null, byteArrayBody, null);
    }

    public NameValueParams(String name, InputStreamBody inputStreamBody) {
        this(HttpParamType.INPUT_STREAM_, name, null, null, null, inputStreamBody);
    }

    private NameValueParams(HttpParamType type, String name, String value, File file, ByteArrayBody byteArrayBody, InputStreamBody inputStreamBody) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.file = file;
        this.byteArrayBody = byteArrayBody;
        this.inputStreamBody = inputStreamBody;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value == null ? "" : value;
    }

    public File getFile() throws TypeMisMatchException {
        if(type == HttpParamType.FILE_) return file;
        else throw new TypeMisMatchException("没有file类型的参数");
    }

    public ByteArrayBody getByteArrayBody() throws TypeMisMatchException{
        if(type == HttpParamType.BYTE_ARRAY_) return byteArrayBody;
        else throw new TypeMisMatchException("没有ByteArrayBody类型的参数");
    }

    public InputStreamBody getInputStreamBody() throws TypeMisMatchException{
        if(type == HttpParamType.INPUT_STREAM_) return inputStreamBody;
        else throw new TypeMisMatchException("没有InputStreamBody类型的参数");
    }

    public HttpParamType getType() {
        return type;
    }

}
