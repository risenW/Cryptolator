package com.afridevelopers.cryptolator;

/**
 * Created by Risen on 10/17/2017.
 */

public class Coin {
    private String coin_type,currency,currency_value,input_value;
    private int index;

    public Coin(int index, String coin_type, String currency, String input_value, String currency_value) {
        this.coin_type = coin_type;
        this.currency = currency;
        this.currency_value = currency_value;
        this.index = index;
        this.input_value = input_value;
    }

    public String getCoin_type() {
        return coin_type;
    }

    public void setCoin_type(String coin_type) {
        this.coin_type = coin_type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency_value() {
        return currency_value;
    }

    public void setCurrency_value(String currency_value) {
        this.currency_value = currency_value;
    }

    public String getInput_value() {
        return input_value;
    }

    public void setInput_value(String input_value) {
        this.input_value = input_value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
