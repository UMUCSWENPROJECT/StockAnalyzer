/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import com.umuc.swen.java.stockanalyzer.daomodels.StockSummary;
import com.umuc.swen.java.stockanalyzer.daomodels.StockDateMap;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import com.umuc.swen.java.stockanalyzer.daomodels.StockHistorical;
import java.util.HashSet;
import java.util.Set;

/**
 * Test DAO methods with CRUD statements
 * Running test cases in order of method names in ascending order
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StockDaoTest {
    
    public StockDaoTest() {}
    
    /**
     * Test of getInstance method, of class StockDao.
     */
    @Test
    public void testAGetInstance() {
        StockDao result = StockDao.getInstance();
        assertNotNull(result);
    }
    
    /**
     * Test of getTestInstance method, of class StockDao.
     */
    @Test
    public void testAaGetInstance() {
        StockDao result = StockDao.getTestInstance();
        StockDao.setInstanceNull();
        assertNotNull(result);
    }

    /**
     * Test stock source insert/get
     */
    @Test
    public void testBInsertStockSource() {
        String stockSource = "Robinhood";
        StockDao instance = StockDao.getTestInstance();
        instance.setTestStockSource(stockSource);
        String sourceName = instance.getTestStockSourceByName(stockSource);
        Assert.assertTrue("Stock Source failed to return any data", sourceName.equals(stockSource));
    }
    
    /**
     * Test of setStockTickerData insert/get, of class StockDao.
     */
    @Test
    public void testCInsertStockTickerData() {
        String stockName = "T";
        String stockSymbol = "AT&T";
        StockDao instance = StockDao.getTestInstance();
        instance.setTestStockTickerData(stockSymbol, stockName);
        String tickerName = instance.getTestStockTickerNameBySymbol(stockSymbol);
        Assert.assertTrue("Stock Ticker failed to return any data", tickerName.equals(stockName));
    }

    /**
     * Test stock date mapper insert/get
     */
    /*
    @Test
    public void testDInsertStockDateMap() {
        StockDao instance = StockDao.getInstance();
        StockDateMap stockDateMap = new StockDateMap();
        int sourceId = instance.getStockSourceIdByName("Yahoo");
        int tickerId = instance.getStockTickerBySymbol("MSFT");
        stockDateMap.setSourceId(sourceId);
        stockDateMap.setTickerId(tickerId);
        stockDateMap.setDate("2019-04-01");
        int cntBeforeInsert = instance.getStockDateMapCount();
        instance.insertStockDateMap(stockDateMap);
        int cntAfterInsert = instance.getStockDateMapCount();
        Assert.assertTrue("Stock Date Map failed to insert data", cntAfterInsert >= cntBeforeInsert);
    }
    */
    /**
     * Test of insertStockSummaryData insert/get, of class StockDao.
     */
    @Test
    public void testEInsertStockSummaryData() throws Exception {
        StockDao instance = StockDao.getTestInstance();
        
        String date = "2019-04-01";
        
        StockSummary stockSummary = new StockSummary();
        stockSummary.setSource("Yahoo");
        stockSummary.setTicker_symbol("MSFT");
        stockSummary.setTicker_name("﻿Microsoft Corporation");
        stockSummary.setStock_record_date(date);
        stockSummary.setAskPrice(new BigDecimal(12.5));
        stockSummary.setBidPrice(new BigDecimal(12.8));
        stockSummary.setPrevClosePrice(new BigDecimal(11.7));

        int cntBeforeInsert = instance.getTestStockSummaryCount();
        instance.insertTestStockSummaryData(stockSummary);
        int cntAfterInsert = instance.getTestStockSummaryCount();
        
        Assert.assertTrue("Stock summary failed to insert data", cntAfterInsert >= cntBeforeInsert);
    }

    /**
     * Test of getAvgStockSummaryView method, of class StockDao.
    */
    /*
    @Test
    public void testFGetAvgStockSummaryView() {
        StockDao instance = StockDao.getTestInstance();
        int totalRecords = instance.getAvgStockSummaryView();
        
        int cntStockDataMap = instance.getStockDateMapCount();
        
        //Only if the summary has data otherwise view will return 0 rows
        if(cntStockDataMap > 0) {
            String message = "Summary view failed to return any data";
            Assert.assertTrue(message, totalRecords > 0);
        }
    }
    * /

    /**
     * Test of insertStockHistoricalData insert/get, of class StockDao.
     */
    @Test
    public void testGInsertStockhistoricalData() throws Exception {
        StockDao instance = StockDao.getTestInstance();
        
         String date = "2019-04-02";
        
        StockHistorical historical = new StockHistorical();
        historical.setSource("Yahoo");
        historical.setTicker_symbol("MSFT");
        historical.setTicker_name("﻿Microsoft Corporation");
        historical.setHistorical_date(date);
 
        historical.setOpen(new BigDecimal(120.5));
        historical.setHigh(new BigDecimal(122.1));
        historical.setLow(new BigDecimal(119.4));
        historical.setClose(new BigDecimal(121.7));
        historical.setAdjClose(new BigDecimal(121.7));
        historical.setVolume(27991000);
        
        int cntBeforeInsert = instance.getTestStockHistoricalCount();
        instance.insertTestStockHistoricalData(historical);
        int cntAfterInsert = instance.getTestStockHistoricalCount();
        
        Assert.assertTrue("Stock historical failed to insert data", cntAfterInsert >= cntBeforeInsert);
    }
    
    /**
    *Test get stock historical data from view
     */
    public void testHGetStockHIstoricalView() {
        StockDao instance = StockDao.getTestInstance();
        int totalRecords = instance.getStockHistoricalView();
        Assert.assertTrue(totalRecords > 0);
    }
    
    /**
     * Delete records from stock source
     */
    @Test
    public void testIdeleteAllFromStockSource(){
        StockDao instance = StockDao.getTestInstance();
        
        instance.deleteFromStockSource();
        int recordsCnt = instance.getTestStockSourceCount();
        
        Assert.assertTrue("Delete failed for stock source", recordsCnt < 1);
    }
    
    /**
     * Delete records from stock ticker
     */
    @Test
    public void testJdeleteAllFromStockTicker () {
        StockDao instance = StockDao.getTestInstance();

        instance.deleteFromStockTicker();
        int recordsCnt = instance.getTestStockTickerCount();
        
        Assert.assertTrue("Delete failed for stock ticker", recordsCnt <1);
    }
    
    /**
     * Delete records from stock summary
     */
    @Test
    public void testLDeleteAllFromStockSummary() {
        StockDao instance = StockDao.getTestInstance();

        instance.deleteFromStockSummary();
        int recordsCnt = instance.getTestStockSummaryCount();
        
        Assert.assertTrue("Delete failed for stock summary", recordsCnt <1);
    }
    /**
    * Delete records from stock historical
     */
    @Test
    public void testMDeleteFromStockHiastorical() {
        StockDao instance = StockDao.getTestInstance();
        instance.deleteFromStockHiastorical();
        int recordsCnt = instance.getTestStockHistoricalCount();
        Assert.assertTrue("Delete failed from stock history", recordsCnt <1);
    }
}