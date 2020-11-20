package com.yato.test;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class HttpUtils {
    public static String doGetHtml(String url, String phone){
        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().build();
        // 创建HttpGet对象，设置url地址
        HttpPost httpPost = new HttpPost(url);
        // 设置请求信息
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Cookie", "userPhone=12345678" + phone);

        CloseableHttpResponse resp = null;
        try {
            // 使用HttpClient发起请求，获取响应
            resp = httpClient.execute(httpPost);
            // 解析响应结果
//            if(resp.getStatusLine().getStatusCode() == 200){
                // 判断Entity是否为空，如果不为空，就可以使用EntityUtils
                if (resp.getEntity() != null){
                    String content = EntityUtils.toString(resp.getEntity());
                    return content;
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 注意HttpClient不用关闭
            if (resp != null) {
                try {
                    resp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}

