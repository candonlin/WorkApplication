package com.denny.workapplication.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.denny.workapplication.DataBean;
import com.denny.workapplication.contract.MainContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainModel implements MainContract.IMainModel {
    private MainContract.IMainPresenter iMainPresenter;
    private  WebSocket webSocket;

    public MainModel(MainContract.IMainPresenter iMainPresenter) {
        this.iMainPresenter = iMainPresenter;
    }

    public void getFirstData() {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.yshyqxx.com/api/v1/aggTrades?symbol=BTCUSDT&&limit=40")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                iMainPresenter.showDialog("Error", e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String body = response.body().string();

                        ArrayList<DataBean> arrayList = new Gson().fromJson(body,
                                new TypeToken<ArrayList<DataBean>>() {
                                }.getType());
                        iMainPresenter.showFirstData(arrayList);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    iMainPresenter.showDialog("Error", e.toString());

                }
            }
        });
    }

    public  void startWS(){
        OkHttpClient client;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        client = builder.build();
        Request request = new Request.Builder()
                .url("wss://stream.yshyqxx.com/ws/btcusdt@aggTrade")
//                .header("Auth-Token","secret-api-token-here")
                .build();
        Log.i("WebSockets", "Headers: " + request.headers().toString());
        webSocket = client.newWebSocket(request, new WebSocketListener() {

            private static final int NORMAL_CLOSURE_STATUS = 1000;
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
//                webSocket.send("{Auth-Token:secret-api-token-here}");
                Log.i("WebSockets", "Connection accepted!");
                //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
            }
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.i("WebSockets", "Receiving : " + text);
//                {
//                    "e": "aggTrade",  // 事件类型
//                        "E": 123456789,   // 事件时间
//                        "s": "BNBBTC",    // 交易对
//                        "a": 12345,       // 归集交易ID
//                        "p": "0.001",     // 成交价格
//                        "q": "100",       // 成交数量
//                        "f": 100,         // 被归集的首个交易ID
//                        "l": 105,         // 被归集的末次交易ID
//                        "T": 123456785,   // 成交时间
//                        "m": true,        // 买方是否是做市方。如true，则此次成交是一个主动卖出单，否则是一个主动买入单。
//                        "M": true         // 请忽略该字段
//                }
                iMainPresenter.parseData(text);



            }
            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                Log.i("WebSockets", "Receiving bytes : " + bytes.hex());
            }
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                webSocket.close(NORMAL_CLOSURE_STATUS, null);
                Log.i("WebSockets", "Closing : " + code + " / " + reason);
            }
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Log.i("WebSockets", "Error : " + t.getMessage());
            }});
    }

    @Override
    public void stopWS() {
        if(webSocket!=null){
            webSocket.close(1000, null);
        }
    }
}
