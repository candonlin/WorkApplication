package com.denny.workapplication;

import android.text.format.DateFormat;

import java.util.Date;

public class DataBean {

    //    [
//    {
//        "a": 26129,         // 归集成交ID
//            "p": "0.01633102",  // 成交价
//            "q": "4.70443515",  // 成交量
//            "f": 27781,         // 被归集的首个成交ID
//            "l": 27781,         // 被归集的末个成交ID
//            "T": 1498793709153, // 成交时间
//            "m": true,          // 是否为主动卖出单
//            "M": true           // 是否为最优撮合单(可忽略，目前总为最优撮合)
//    }]
    private int a;
    private String p;
    private String q;
    private int f;
    private int l;
    private long T;
    private boolean m;
    private boolean M;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public long getT() {
        return T;
    }

    public String getTime() {
        String dateString = DateFormat.format("HH:mm:ss", new Date(T)).toString();
        return dateString;
    }

    public void setT(long t) {
        T = t;
    }

    public boolean ism() {
        return m;
    }

    public void setm(boolean m) {
        this.m = m;
    }

    public boolean isM() {
        return M;
    }

    public void setM(boolean m) {
        this.M = m;
    }
}
