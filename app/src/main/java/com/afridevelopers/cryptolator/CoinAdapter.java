package com.afridevelopers.cryptolator;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Risen on 10/17/2017.
 */

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.RecyclerViewHolder>{
    private ArrayList<Coin> coinArrayList;
    private MyItemClickListener clickListener;
    private MyItemLongClickListener longClickListener;

    public CoinAdapter(ArrayList<Coin> coinArrayList) {
        this.coinArrayList = coinArrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_cardview,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,clickListener,longClickListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Coin coin = coinArrayList.get(position);
//        holder.currency_icon.setText(coin.get);
        holder.coin.setText(coin.getCoin_type());
        holder.currency.setText(coin.getCurrency());
        holder.input_value.setText(String.valueOf(coin.getInput_value()));
        holder.currency_value.setText(String.valueOf(coin.getOutput_value()));

    }

    @Override
    public int getItemCount() {
        return coinArrayList.size();
    }



    //item listeners for Adapter
    public void setClickListener(MyItemClickListener listener){
        this.clickListener = listener;
    }

    public void setLongClickListener(MyItemLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }


    //ViewHolder inner class

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView coin, currency_icon,currency,input_value,currency_value;
        ImageView coin_image;
        private MyItemClickListener itemClickListener;
        private MyItemLongClickListener itemLongClickListener;

        public RecyclerViewHolder(View arg0,MyItemClickListener listener,MyItemLongClickListener itemLongClickListener) {
            super(arg0);

            coin_image = (ImageView)arg0.findViewById(R.id.coin_image);
            coin = (TextView)arg0.findViewById(R.id.coin_name);
            currency_icon = (TextView)arg0.findViewById(R.id.currency_image);
            currency = (TextView)arg0.findViewById(R.id.currency);
            input_value = (TextView)arg0.findViewById(R.id.input_value);
            currency_value = (TextView)arg0.findViewById(R.id.currency_value);
            this.itemClickListener = listener;
            this.itemLongClickListener = itemLongClickListener;
            arg0.setOnClickListener(this);
            arg0.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null){
                itemClickListener.onItemClick(view,getPosition());
            }
        }

        @Override
        public boolean onLongClick(View arg0) {
            if (itemLongClickListener != null){
                itemLongClickListener.onItemLongClick(arg0,getPosition());
            }
            return true;
        }
    }
}



