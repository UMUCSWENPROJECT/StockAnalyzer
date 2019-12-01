/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer.scrapers;
import java.util.Date;
import java.util.List;
import com.umuc.swen.java.stockanalyzer.StockDao;
import com.umuc.swen.java.stockanalyzer.daomodels.StockTicker;

/**
 * Initialize stock scrapper with dao and stock tickers
 */
public class StockScraper {
    
    protected String source;
    protected StockDao dao;
    protected List<StockTicker> stockTickers;
    protected Date latestScrapedDate;
    protected Date latestHistoricalDate;
    
    public StockScraper(String src){
        source = src;
        dao = StockDao.getInstance();
        stockTickers = dao.getAllstockTickers();
        latestScrapedDate = dao.getLatestScrapedDate(source);
        latestHistoricalDate = dao.getLatestHistoricalScrapedDate();
    }
    
    public void setLatestScrapedDate(Date latestScrapedDate) {
        this.latestScrapedDate = latestScrapedDate;
    }
    public void setLatestHistoricalDate(Date latestHistoricalDate) {
        this.latestHistoricalDate = latestHistoricalDate;
    }
}
