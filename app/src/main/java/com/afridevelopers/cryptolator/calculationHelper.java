package com.afridevelopers.cryptolator;

/**
 * Created by Risen on 10/10/2017.
 */

public class CalculationHelper {

    public CalculationHelper(){
    }

    public String getCoinSelected(int value){
        String temp;
        switch (value){
            case 0:
                temp = "BTC";
                break;
            case 1:
                temp = "ETH";
                break;
            default:
                temp = "BTC";
        }
        return temp;
    }

    public String getCurrencySelected(int value){
        String temp;
        switch (value){
            case 0:
                temp = "USD";
                break;
            case 1:
                temp = "NGN";
                break;
            case 2:
                temp = "EUR";
                break;
            case 3:
                temp = "YEN";
                break;
            default:
                temp = "USD";

        }
        return temp;
    }

    public String convert(double value_to_convert, String value_from_server){
       //value_to_convert is the entered value: value_from_server is the returned value of 1 BTC OR ETH from the server
        double temp = Double.parseDouble(value_from_server);
        temp = temp * value_to_convert;
        String formatted = String.format("%.4f",temp);  //formats the value to 4 dp
        return String.valueOf(formatted);
    }
}
