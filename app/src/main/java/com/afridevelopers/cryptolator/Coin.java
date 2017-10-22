package com.afridevelopers.cryptolator;

/**
 * Created by Risen on 10/17/2017.
 */

public class Coin {
    private String coin_type,currency, output_value,input_value;
    private int index, coin_image_id,currency_image_id;

    public Coin(int index, int coin_image_id,String coin_type, int currency_image_id,String currency, String input_value, String output_value) {
        this.index = index;
        this.coin_image_id = coin_image_id;
        this.coin_type = coin_type;
        this.currency_image_id = currency_image_id;
        this.currency = currency;
        this.output_value = output_value;
        this.input_value = input_value;
    }

    public String getCoin_type() {
        return coin_type;
    }

    public void setCoin_type(String coin_type) {
        this.coin_type = coin_type;
    }

    public int getCoin_image_id() {
        return coin_image_id;
    }

    public void setCoin_image_id(int coin_image_id) {
        this.coin_image_id = coin_image_id;
    }

    public int getCurrency_image_id() {
        return currency_image_id;
    }

    public void setCurrency_image_id(int currency_image_id) {
        this.currency_image_id = currency_image_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOutput_value() {
        return output_value;
    }

    public void setOutput_value(String output_value) {
        this.output_value = output_value;
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
