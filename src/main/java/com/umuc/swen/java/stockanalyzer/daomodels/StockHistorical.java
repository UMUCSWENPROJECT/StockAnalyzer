package com.umuc.swen.java.stockanalyzer.daomodels;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity object for historical data
 */
public class StockHistorical implements Serializable {
    
    private String source;
    
    private String ticker_symbol;
    
    private String ticker_name;
    
    private String historical_date; 
    
    private BigDecimal open;
    
    private BigDecimal high;
    
    private BigDecimal low;
    
    private BigDecimal close;
    
    private BigDecimal adjClose;
    
    private long volume;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTicker_symbol() {
        return ticker_symbol;
    }

    public void setTicker_symbol(String ticker_symbol) {
        this.ticker_symbol = ticker_symbol;
    }
    
    public String getTicker_name() {
        return ticker_name;
    }
    
    public void setTicker_name(String ticker_name) {
        this.ticker_name = ticker_name;
    }
    
    public String getHistorical_date() {
        return historical_date;
    }
    
    public void setHistorical_date(String historical_date) {
        this.historical_date = historical_date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(BigDecimal adjClose) {
        this.adjClose = adjClose;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
    
    @Override
	public String toString() {
		return "StockHistorical [source=" + source + ", ticker_symbol=" + ticker_symbol + ", ticker_name=" + ticker_name + ", historical_date=" + historical_date + ", open=" + open + ", high"
				+ high + ", low=" + low + ", close=" + close + ", adjClose=" + adjClose
				+ ", volume=" + volume  + "]";
	}
}
