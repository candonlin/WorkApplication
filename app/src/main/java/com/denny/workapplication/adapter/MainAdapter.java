package com.denny.workapplication.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denny.workapplication.DataBean;
import com.denny.workapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private ArrayList<DataBean> dataBeans = new ArrayList<>();
    DecimalFormat format = new DecimalFormat("#,###.00");

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_time.setText(dataBeans.get(position).getTime());
        String s = format.format(Float.valueOf(dataBeans.get(position).getP()));
        holder.tv_price.setText(s);
        holder.tv_qty.setText(dataBeans.get(position).getQ());

        Float t1,t2;
        if(position<39){
            t1=  Float.valueOf(dataBeans.get(position).getP());
            t2=  Float.valueOf(dataBeans.get(position+1).getP());
        }else{
            t1=  Float.valueOf(dataBeans.get(position).getP());
            t2=  lastPrice;
        }
        if(t1>t2){
            holder.tv_price.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.tv_price.setTextColor(Color.parseColor("#00FF00"));
        }

    }
    private float lastPrice=0f;

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public void setNewData(ArrayList<DataBean> arrayList) {
        dataBeans.clear();
        dataBeans.addAll(arrayList);

        lastPrice=Float.valueOf(dataBeans.get(dataBeans.size()-1).getP());
        notifyDataSetChanged();
    }

    public void addData(DataBean bean) {
        if (dataBeans.size() > 0) {
            dataBeans.remove(dataBeans.size() - 1);
        }
        dataBeans.add(0, bean);
        lastPrice=Float.valueOf(dataBeans.get(dataBeans.size()-1).getP());

        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_time;
        private TextView tv_price;
        private TextView tv_qty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_qty = itemView.findViewById(R.id.tv_qty);
        }
    }
}
