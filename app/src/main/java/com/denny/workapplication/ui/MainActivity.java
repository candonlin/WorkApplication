package com.denny.workapplication.ui;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denny.workapplication.DataBean;
import com.denny.workapplication.R;
import com.denny.workapplication.adapter.MainAdapter;
import com.denny.workapplication.contract.MainContract;
import com.denny.workapplication.presenter.MainPresenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.IMainView {
    private RecyclerView rv_price;
    private MainContract.IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        rv_price = findViewById(R.id.rv_price);
        rv_price.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter adapter = new MainAdapter();
        rv_price.setAdapter(adapter);
        presenter = new MainPresenter(this);

        presenter.getFirstData();
    }

    @Override
    public void showDialog(String title, String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isDestroyed()) {
                    AlertDialog.Builder alertDialog =
                            new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle(title);
                    alertDialog.setMessage(msg);
                    alertDialog.setCancelable(false);
                    alertDialog.show();

//                    new AlertDialog.Builder(MainActivity.this)
//                            .setTitle(title)
//                            .setMessage(msg)
//                            .show();
                }
            }
        });
    }

    @Override
    public void showFirstData(ArrayList<DataBean> dataBeans) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isDestroyed()) {
                    if (rv_price.getAdapter() != null) {
                        MainAdapter adater = (MainAdapter) rv_price.getAdapter();
                        adater.setNewData(dataBeans);
                    }
                }
            }
        });
    }

    @Override
    public void addData(DataBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isDestroyed()) {
                    if (rv_price.getAdapter() != null) {
                        MainAdapter adater = (MainAdapter) rv_price.getAdapter();
                        adater.addData(bean);
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startWS();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopWS();

    }
}