package com.umuc.swen.java.stockanalyzer.daomodels;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for stock ticker
 */
public class StockTickerTest {
    
    StockTicker master = new StockTicker();
    
    public StockTickerTest() {
        master.setName("Testing Corp");
        master.setSymbol("TC");

    }

    /* Test of getsymbol
    * 
    */
    @Test
    public void testGetSymbol() {
        String test = "TC";
        assertEquals(test, master.getSymbol());
    }

    /* Test of setsymbol
    * 
    */
    @Test
    public void testSetSymbol() {
        StockTicker instance = new StockTicker();
        instance.setSymbol("TC");
        assertEquals(master.getSymbol(), instance.getSymbol());
    }

    /* Test of getName
    * 
    */
    @Test
    public void testGetTicker_name() {
        String test = "Testing Corp";
        assertEquals(test, master.getTicker_name());
    }

    /* Test of setName
    * 
    */
    @Test
    public void testSetName() {
        StockTicker instance = new StockTicker();
        instance.setName("Testing Corp");
        assertEquals(master.getTicker_name(), instance.getTicker_name());
    }
    
}
