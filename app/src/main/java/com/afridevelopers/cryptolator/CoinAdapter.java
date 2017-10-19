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

    public CoinAdapter(ArrayList<Coin> coinArrayList) {
        this.coinArrayList = coinArrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_cardview,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
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

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView coin,currency,currency_value;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            coin = (TextView)itemView.findViewById(R.id.coin_name);
            currency = (TextView)itemView.findViewById(R.id.currency);
            currency_value = (TextView)itemView.findViewById(R.id.currency_value);


        }
    }
}



