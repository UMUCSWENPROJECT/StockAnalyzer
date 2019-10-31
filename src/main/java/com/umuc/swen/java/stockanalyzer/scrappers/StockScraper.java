/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer.scrappers;
import com.umuc.swen.java.stockanalyzer.scrappers.*;
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
    protected Date latestScrappedDate;
    protected Date latestHistoricalDate;
    
    public StockScraper(String src){
        source = src;
        dao = StockDao.getInstance();
        stockTickers = dao.getAllstockTickers();
        latestScrappedDate = dao.getLatestScrappedDate(source);
        latestHistoricalDate = dao.getLatestHistoricalScrappedDate();
    }
}
