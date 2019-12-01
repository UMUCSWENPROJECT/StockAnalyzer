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
    
    /* Test of getSource
    * 
    */
    @Test
    public void testgetSource () {
        StockHistorical instance = new StockHistorical();
        instance.setSource("Yahoo");
        String expResult = "Yahoo";
        String result = instance.getSource();
        assertEquals(expResult, result);
    }

    /* Test of setSource
    * 
    */
    @Test
    public void testsetSource () {
        StockHistorical instance = new StockHistorical();
        String stockSource = "Yahoo";
        String expResult = "Yahoo";
        instance.setSource(stockSource);
        String result = instance.getSource();
        assertEquals(expResult, result);
    }
    
    /* Test of getTicker_symbol
    * 
    */
    @Test
    public void testgetTicker_symbol () {
        StockHistorical instance = new StockHistorical();
        instance.setTicker_symbol("MSFT");
        String expResult = "MSFT";
        String result = instance.getTicker_symbol();
        assertEquals(expResult, result);
    }

    /* Test of setTicker_symbol
    * 
    */
    @Test
    public void testsetTicker_symbol () {
        StockHistorical instance = new StockHistorical();
        String stockTickerSymbol = "MSFT";
        String expResult = "MSFT";
        instance.setTicker_symbol(stockTickerSymbol);
        String result = instance.getTicker_symbol();
        assertEquals(expResult, result);
    }
    
    /* Test of getTicker_name
    * 
    */
    @Test
    public void testgetTicker_name () {
        StockHistorical instance = new StockHistorical();
        instance.setTicker_name("Microsoft Corporation");
        String expResult = "Microsoft Corporation";
        String result = instance.getTicker_name();
        assertEquals(expResult, result);
    }

    /* Test of setTicker_name
    * 
    */
    @Test
    public void testsetTicker_name () {
        StockHistorical instance = new StockHistorical();
        String stockTickerName = "Microsoft Corporation";
        String expResult = "Microsoft Corporation";
        instance.setTicker_name(stockTickerName);
        String result = instance.getTicker_name();
        assertEquals(expResult, result);
    }
    
    /* Test of getHistorical_date
    * 
    */
    @Test
    public void testgetHistorical_date () {
        StockHistorical instance = new StockHistorical();
        instance.setHistorical_date("2019-04-01");
        String expResult = "2019-04-01";
        String result = instance.getHistorical_date();
        assertEquals(expResult, result);
    }

    /* Test of setHistorical_date
    * 
    */
    @Test
    public void testsetHistorical_date () {
        StockHistorical instance = new StockHistorical();
        String stockDate = "2019-04-01";
        String expResult = "2019-04-01";
        instance.setHistorical_date(stockDate);
        String result = instance.getHistorical_date();
        assertEquals(expResult, result);
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
