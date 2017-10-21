package com.afridevelopers.cryptolator;

/**
 * Created by Risen on 10/17/2017.
 */

public class Coin {
    private String coin_type;
    private String Currency;
    private double currency_value;
    private int index;

    public Coin(int index,String coin_type, String currency, double currency_value) {
        this.coin_type = coin_type;
        Currency = currency;
        this.currency_value = currency_value;
        this.index = index;

    }

    public String getCoin_type() {
        return coin_type;
    }

    public void setCoin_type(String coin_type) {
        this.coin_type = coin_type;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public double getCurrency_value() {
        return currency_value;
    }

    public void setCurrency_value(double currency_value) {
        this.currency_value = currency_value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
