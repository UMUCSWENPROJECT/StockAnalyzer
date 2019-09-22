/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockreporter;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import stockreporter.daomodels.StockSummary;
import stockreporter.daomodels.StockDateMap;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import stockreporter.daomodels.StockHistorical;

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
     * Test stock source insert/get
     */
    @Test
    public void testBInsertStockSource() {
        String stockSource = "Robinhood";
        StockDao instance = StockDao.getInstance();
        instance.setStockSource(stockSource);
        int sourceId = instance.getStockSourceIdByName(stockSource);
        Assert.assertTrue("Stock Source failed to return any data", sourceId > 0);
    }
    
    /**
     * Test of setStockTickerData insert/get, of class StockDao.
     */
    @Test
    public void testCInsertStockTickerData() {
        String stockName = "T";
        String stockSymbol = "AT&T";
        StockDao instance = StockDao.getInstance();
        instance.setStockTickerData(stockName, stockSymbol);
        int tickerId = instance.getStockTickerBySymbol(stockName);
        Assert.assertTrue("Stock Ticker failed to return any data", tickerId > 0);
    }

    /**
     * Test stock date mapper insert/get
     */
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
    
    /**
     * Test of insertStockSummaryData insert/get, of class StockDao.
     */
    @Test
    public void testEInsertStockSummaryData() {
        StockDao instance = StockDao.getInstance();
        
        String date = "2019-04-01";
        
        int stockDateMapId = instance.getStockDateMapID(date, "MSFT", "Yahoo");
        
        StockSummary stockSummary = new StockSummary();
        stockSummary.setAskPrice(new BigDecimal(12.5));
        stockSummary.setBidPrice(new BigDecimal(12.8));
        stockSummary.setPrevClosePrice(new BigDecimal(11.7));
        stockSummary.setStockDtMapId(stockDateMapId);

        int cntBeforeInsert = instance.getStockSummaryCount();
        instance.insertStockSummaryData(stockSummary);
        int cntAfterInsert = instance.getStockSummaryCount();
        
        Assert.assertTrue("Stock summary failed to insert data", cntAfterInsert >= cntBeforeInsert);
    }

    /**
     * Test of getAvgStockSummaryView method, of class StockDao.
     */
    @Test
    public void testFGetAvgStockSummaryView() {
        StockDao instance = StockDao.getInstance();
        int totalRecords = instance.getAvgStockSummaryView();
        
        int cntStockDataMap = instance.getStockDateMapCount();
        
        //Only if the summary has data otherwise view will return 0 rows
        if(cntStockDataMap > 0) {
            String message = "Summary view failed to return any data";
            Assert.assertTrue(message, totalRecords > 0);
        }
    }

    /**
     * Test of insertStockHistoricalData insert/get, of class StockDao.
     */
    @Test
    public void testGInsertStockhistoricalData() {
        StockDao instance = StockDao.getInstance();
        
         String date = "2019-04-01";
        
        int stockDateMapId = instance.getStockDateMapID(date, "MSFT", "Yahoo");
        
        StockHistorical historical = new StockHistorical();
        historical.setOpen(new BigDecimal(120.5));
        historical.setHigh(new BigDecimal(122.1));
        historical.setLow(new BigDecimal(119.4));
        historical.setClose(new BigDecimal(121.7));
        historical.setAdjClose(new BigDecimal(121.7));
        historical.setVolume(27991000);
        historical.setStockDtMapId(stockDateMapId);
        
        int cntBeforeInsert = instance.getStockHistoricalCount();
        instance.insertStockHistoricalData(historical);
        int cntAfterInsert = instance.getStockHistoricalCount();
        
        Assert.assertTrue("Stock historical failed to insert data", cntAfterInsert >= cntBeforeInsert);
    }
    
    /**
    *Test get stock historical data from view
     */
    public void testHGetStockHIstoricalView() {
        StockDao instance = StockDao.getInstance();
        int totalRecords = instance.getStockHistoricalView();
        Assert.assertTrue(totalRecords > 0);
    }
    
    /**
     * Delete records from stock source
     */
    @Test
    public void testIdeleteAllFromStockSource(){
        StockDao instance = StockDao.getInstance();
        
        instance.deleteFromStockSource();
        int recordsCnt = instance.getStockSourceCount();
        
        Assert.assertTrue("Delete failed for stock source", recordsCnt < 1);
    }
    
    /**
     * Delete records from stock ticker
     */
    @Test
    public void testJdeleteAllFromStockTicker () {
        StockDao instance = StockDao.getInstance();

        instance.deleteFromStockTicker();
        int recordsCnt = instance.getStockTickerCount();
        
        Assert.assertTrue("Delete failed for stock ticker", recordsCnt <1);
    }
    
    /**
     * Delete records from stock date map
     */
    @Test
    public void testKdeleteAllFromStockDateMap() {
        StockDao instance = StockDao.getInstance();

        instance.deleteFromStockDateMap();
        int recordsCnt = instance.getStockDateMapCount();
        
        Assert.assertTrue("Delete failed for stock date map", recordsCnt <1);
    }
    
    /**
     * Delete records from stock summary
     */
    @Test
    public void testLDeleteAllFromStockSummary() {
        StockDao instance = StockDao.getInstance();

        instance.deleteFromStockSummary();
        int recordsCnt = instance.getStockSummaryCount();
        
        Assert.assertTrue("Delete failed for stock summary", recordsCnt <1);
    }
    /**
    * Delete records from stock historical
     */
    @Test
    public void testMDeleteFromStockHiastorical() {
        StockDao instance = StockDao.getInstance();
        instance.deleteFromStockHiastorical();
        int recordsCnt = instance.getStockHistoricalCount();
        Assert.assertTrue("Delete failed from stock history", recordsCnt <1);
    }
}