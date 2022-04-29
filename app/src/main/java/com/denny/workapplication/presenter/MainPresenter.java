package com.denny.workapplication.presenter;

import com.denny.workapplication.DataBean;
import com.denny.workapplication.contract.MainContract;
import com.denny.workapplication.model.MainModel;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainPresenter implements MainContract.IMainPresenter {
    private MainContract.IMainView iMainView;
    private MainContract.IMainModel iMainModel;

    public MainPresenter(MainContract.IMainView iMainView) {
        this.iMainView = iMainView;
        this.iMainModel = new MainModel(this);
    }

    @Override
    public void showDialog(String title, String msg) {
        iMainView.showDialog(title,msg);
    }

    @Override
    public void getFirstData() {
        iMainModel.getFirstData();
    }

    @Override
    public void showFirstData(ArrayList<DataBean> dataBeans) {
        iMainView.showFirstData(dataBeans);
    }

    @Override
    public void startWS() {
        iMainModel.startWS();
    }

    @Override
    public void stopWS() {
        iMainModel.stopWS();
    }

    public void parseData(String json){
        try {
            JSONObject object =new JSONObject(json);
            DataBean bean=new DataBean();
            bean.setA(object.getInt("a"));
            bean.setP(object.getString("p"));
            bean.setQ(object.getString("q"));
            bean.setF(object.getInt("f"));
            bean.setL(object.getInt("l"));
            bean.setT(object.getLong("T"));
            bean.setm(object.getBoolean("m"));
            bean.setM(object.getBoolean("M"));
            iMainView.addData(bean);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog("Error", e.toString());

        }


    }
}
