package com.kaida.apple.kaidacode;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetClient {
    private static NetClient netClient;
    private NetClient(){
        client = initOkHttpClient();
    }
    public final OkHttpClient client;
    private OkHttpClient initOkHttpClient(){
        //初始化的时候可以自定义一些参数
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)//设置读取超时为10秒
                .connectTimeout(10000, TimeUnit.MILLISECONDS)//设置链接超时为10秒
                .build();
        return okHttpClient;
    }
    public static NetClient getNetClient(){
        if(netClient == null){
            netClient = new NetClient();
        }
        return netClient;
    }

    public void callNet(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = getNetClient().initOkHttpClient().newCall(request);
        call.enqueue(callback);
    }
}
