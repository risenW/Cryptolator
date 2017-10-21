package com.afridevelopers.cryptolator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Risen on 10/10/2017.
 */

public class CalculationHelper {

    public CalculationHelper(){
    }

    public String convert(double value_to_convert, String value_from_server){
        //value_to_convert is the entered value: value_from_server is the returned value of 1 BTC OR ETH from the server
        double temp = Double.parseDouble(value_from_server);
        temp = temp * value_to_convert;

        //Formats the value to monetary form
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setCurrencySymbol("");
        formatter.setDecimalFormatSymbols(symbols);
        String formatted = formatter.format(temp);

        return String.valueOf(formatted);
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
                temp = "JPY";
                break;
            case 4:
                temp = "GBP";
                break;
            case 5:
                temp = "AUD";
                break;
            case 6:
                temp = "CAD";
                break;
            case 7:
                temp = "CHF";
                break;
            case 8:
                temp = "CNY";
                break;
            case 9:
                temp = "SEK";
                break;
            case 10:
                temp = "MXN";
                break;
            case 11:
                temp = "NZD";
                break;
            case 12:
                temp = "SGD";
                break;
            case 13:
                temp = "HKD";
                break;
            case 14:
                temp = "NOK";
                break;
            case 15:
                temp = "KRW";
                break;
            case 16:
                temp = "TRY";
                break;
            case 17:
                temp = "INR";
                break;
            case 18:
                temp = "RUB";
                break;
            case 19:
                temp = "BRL";
                break;
            case 20:
                temp = "ZAR";
                break;
            case 21:
                temp = "DKK";
                break;
            case 22:
                temp = "PLN";
                break;
            default:
                temp = "USD";

        }
        return temp;
    }


}
