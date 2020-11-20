package com.yato;

import com.yato.test.HttpUtils;

import java.net.http.HttpClient;
class Run implements Runnable {
    String phone;
    Run(int i){
        this.phone = String.valueOf(i);
    }
    @Override
    public void run() {
        String url = "http://localhost:8080/seckill/1002/1080db103e3d86ba0e44130452adbd73/excution";
        HttpUtils.doGetHtml(url, phone);
    }
}
public class ConcurrentTest {
    //    HttpClient httpClient = new
    public static void main(String[] args) {

        for (int i = 100; i < 999; i++) {
            new Thread(new Run(i)).start();
        }
    }
}
