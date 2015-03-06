package com.ldm.ldmclient.util.http;

import java.io.IOException;

/**
 * Created by LDM on 2015/3/4. Email : nightkid-s@163.com
 */
public interface OnFileProgressListener {

    int BUFF_LEN = 150 * 1024;

    void write(byte[] bs, int start, int length, long totalLen) throws IOException;

    void finish() throws IOException;

}
