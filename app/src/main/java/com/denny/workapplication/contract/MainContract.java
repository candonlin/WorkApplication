package com.denny.workapplication.contract;

import com.denny.workapplication.DataBean;

import java.util.ArrayList;

public interface MainContract {
    interface IMainView {
        void showDialog(String title, String msg);

        void showFirstData(ArrayList<DataBean> dataBeans);

        void addData(DataBean bean);

    }

    interface IMainPresenter {
        void showDialog(String title, String msg);

        void getFirstData();

        void showFirstData(ArrayList<DataBean> dataBeans);

        void parseData(String json);


        void startWS();

        void stopWS();

    }

    interface IMainModel {
        void getFirstData();

        void startWS();

        void stopWS();

    }
}
