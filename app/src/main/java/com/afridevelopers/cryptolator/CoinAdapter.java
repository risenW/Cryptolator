package com.afridevelopers.cryptolator;


import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

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
        holder.coin.setText(coin.getCoin_type());
        holder.currency_value.setText(String.valueOf(coin.getCurrency_value()));
        holder.currency.setText(coin.getCurrency());

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
        TextView coin,currency,currency_value;
        private MyItemClickListener itemClickListener;
        private MyItemLongClickListener itemLongClickListener;

        public RecyclerViewHolder(View arg0,MyItemClickListener listener,MyItemLongClickListener itemLongClickListener) {
            super(arg0);

            coin = (TextView)arg0.findViewById(R.id.coin_name);
            currency = (TextView)arg0.findViewById(R.id.currency);
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



