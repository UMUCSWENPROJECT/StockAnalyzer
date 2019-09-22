package com.umuc.swen.java.stockanalyzer.daomodels;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity object for historical data
 */
public class StockHistorical implements Serializable {
    
    private long historicalId;
    
    private BigDecimal open;
    
    private BigDecimal high;
    
    private BigDecimal low;
    
    private BigDecimal close;
    
    private BigDecimal adjClose;
    
    private long volume;
    
    private long stockDtMapId;

    public long getStockDtMapId() {
        return stockDtMapId;
    }

    public void setStockDtMapId(long stockDtMapId) {
        this.stockDtMapId = stockDtMapId;
    }

    public long getHistoricalId() {
        return historicalId;
    }

    public void setHistoricalId(long historicalId) {
        this.historicalId = historicalId;
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
		return "StockHistorical [historicalId=" + historicalId + ", open=" + open + ", high"
				+ high + ", low=" + low + ", close=" + close + ", adjClose=" + adjClose
				+ ", volume=" + volume  + "]";
	}
}
