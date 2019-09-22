package com.umuc.swen.java.stockanalyzer.daomodels;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for stock ticker
 */
public class StockTickerTest {
    
    StockTicker master = new StockTicker();
    
    public StockTickerTest() {
        master.setId(100);
        master.setName("Testing Corp");
        master.setSymbol("TC");

    }

    /* Test of getId
    * 
    */
    @Test
    public void testGetId() {
        long test = 100;
        assertEquals(test, master.getId());
    }

    /* Test of setId
    * 
    */
    @Test
    public void testSetId() {
        StockTicker instance = new StockTicker();
        instance.setId(100);
        assertEquals(master.getId(), instance.getId());
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
    public void testGetName() {
        String test = "Testing Corp";
        assertEquals(test, master.getName());
    }

    /* Test of setName
    * 
    */
    @Test
    public void testSetName() {
        StockTicker instance = new StockTicker();
        instance.setName("Testing Corp");
        assertEquals(master.getName(), instance.getName());
    }
    
}
