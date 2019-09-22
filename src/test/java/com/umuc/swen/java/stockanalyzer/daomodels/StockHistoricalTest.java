/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer.daomodels;

import java.math.BigDecimal;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 * Test cases for stock historical
 */
public class StockHistoricalTest {
    
    public StockHistoricalTest() {
    }
    
    /* Test of getStockDtMapId
    * 
    */
    @Test
    public void testgetStockDtMapId () {
        StockHistorical instance = new StockHistorical();
        instance.setStockDtMapId(1);
        long expResult = 1;
        long result = instance.getStockDtMapId();
        assertEquals(expResult, result, 0.01);
    }

    /* Test of setStockDtMapid
    * 
    */
    @Test
    public void testsetStockDtMapId () {
        StockHistorical instance = new StockHistorical();
        long stockDtMapId = 2;
        long expResult = 2;
        instance.setStockDtMapId(stockDtMapId);
        long result = instance.getStockDtMapId();
        assertEquals(expResult, result, 0.1);
    }

  
    /* Test of getHistoricalId
    * 
    */
    @Test
    public void testgetHistoricalId () {
        StockHistorical instance = new StockHistorical();
        instance.setHistoricalId(4);
        long expResult = 4;
        long result = instance.getHistoricalId();
        assertEquals(expResult, result, 0.01);
    }

    /* Test of setHistoricalId
    * 
    */
    @Test
    public void testsetHistoricalId () {
        StockHistorical instance = new StockHistorical();
        long historicalId = 7;
        long expResult = 7;
        instance.setHistoricalId(historicalId);
        long result = instance.getHistoricalId();
        assertEquals(expResult, result, 0.1);
    }

    /* Test of getOpen
    * 
    */
    @Test
    public void testgetOpen () {
        StockHistorical instance = new StockHistorical();
        BigDecimal open = new BigDecimal("3.1");
        int i1, i2;
        i1 = open.intValue();
        instance.setOpen(open);
        BigDecimal expResult = new BigDecimal("3.1");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }
    
 
    /* Test of setOpen
    * 
    */
    @Test
    public void testsetOpen () {
        StockHistorical instance = new StockHistorical();
        BigDecimal open = new BigDecimal("3.1");
        int i1, i2;
        i1 = open.intValue();
        instance.setOpen(open);
        BigDecimal expResult = new BigDecimal("3.1");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }

    /* Test of getHigh
    * 
    */
    @Test
    public void testgetHigh () {
        StockHistorical instance = new StockHistorical();
        BigDecimal high = new BigDecimal("3.7");
        int i1, i2;
        i1 = high.intValue();
        instance.setHigh(high);
        BigDecimal expResult = new BigDecimal("3.7");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }

    /* Test of setHigh
    * 
    */
    @Test
    public void testsetHigh () {
        StockHistorical instance = new StockHistorical();
        BigDecimal high = new BigDecimal("3.9");
        int i1, i2;
        i1 = high.intValue();
        instance.setHigh(high);
        BigDecimal expResult = new BigDecimal("3.9");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }
    
    /* Test of getLow
    * 
    */
    @Test
    public void testgetLow () {
        StockHistorical instance = new StockHistorical();
        BigDecimal low = new BigDecimal("4.2");
        int i1, i2;
        i1 = low.intValue();
        instance.setLow(low);
        BigDecimal expResult = new BigDecimal("4.2");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }

    /* Test of setLow
    * 
    */
    @Test
    public void testsetLow () {
        StockHistorical instance = new StockHistorical();
        BigDecimal low = new BigDecimal("4.8");
        int i1, i2;
        i1 = low.intValue();
        instance.setLow(low);
        BigDecimal expResult = new BigDecimal("4.8");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }

    /* Test of getClose
    * 
    */
    @Test
    public void testgetClose () {
        StockHistorical instance = new StockHistorical();
        BigDecimal close = new BigDecimal("1.1");
        int i1, i2;
        i1 = close.intValue();
        instance.setClose(close);
        BigDecimal expResult = new BigDecimal("1.1");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }
    
    /* Test of setClose
    * 
    */
    @Test
    public void testsetClose () {
        StockHistorical instance = new StockHistorical();
        BigDecimal close = new BigDecimal("1.7");
        int i1, i2;
        i1 = close.intValue();
        instance.setClose(close);
        BigDecimal expResult = new BigDecimal("1.7");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }

    /* Test of getAdjClose
    * 
    */
    @Test
    public void testgetAdjClose () {
        StockHistorical instance = new StockHistorical();
        BigDecimal adjClose = new BigDecimal("1.9");
        int i1, i2;
        i1 = adjClose.intValue();
        instance.setAdjClose(adjClose);
        BigDecimal expResult = new BigDecimal("1.9");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }
    
    /* Test of setAdjClose
    * 
    */
    @Test
    public void testsetAdjClose () {
        StockHistorical instance = new StockHistorical();
        BigDecimal adjClose = new BigDecimal("1.95");
        int i1, i2;
        i1 = adjClose.intValue();
        instance.setAdjClose(adjClose);
        BigDecimal expResult = new BigDecimal("1.95");
        i2 = expResult.intValue();
        assertEquals(i1, i2, 0.1);
    }

    /* Test of getVolume
    * 
    */
    @Test
    public void testgetVolume () {
        StockHistorical instance = new StockHistorical();
        long volume = 5;
        instance.setVolume(volume);
        long expResult = 5;
        long result = instance.getVolume();
        assertEquals(expResult, result, 0.1);
    }
   
    /* Test of setVolume
    * 
    */
    @Test
    public void testsetVolume () {
        StockHistorical instance = new StockHistorical();
        long volume = 4;
        instance.setVolume(volume);
        long expResult = 4;
        long result = instance.getVolume();
        assertEquals(expResult, result, 0.1);
    }

   
}
