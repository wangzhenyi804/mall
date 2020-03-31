package com.zhenyiwang.mall.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpUtils {
    /**
     * 获取请求体
     * @param request
     * @return
     * @throws IOException
     */
    public static String requestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = inputStream.read(bytes)) != -1){
            outputStream.write(bytes,0,len);
        }
        //使用ByteArrayOutputStream的toString()方法，指定编码字符集
        return outputStream.toString("utf-8");
    }
}
