/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer.daomodels;

/**
 * Entity object for stock ticker with setter/getters
 */
public class StockTicker{ 
    
    private String symbol;
    private String ticker_name;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTicker_name() {
        return ticker_name;
    }

    public void setName(String ticker_name) {
        this.ticker_name = ticker_name;
    }

    @Override
    public String toString() {
        return "StockTicker{" + ", symbol=" + symbol + ", ticker_name=" + ticker_name + '}';
    }
}
