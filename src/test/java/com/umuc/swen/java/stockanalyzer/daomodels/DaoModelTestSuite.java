/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer.daomodels;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for model classes
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.umuc.swen.java.stockanalyzer.daomodels.StockDateMapTest.class, com.umuc.swen.java.stockanalyzer.daomodels.StockTickerTest.class, com.umuc.swen.java.stockanalyzer.daomodels.StockSummaryTest.class})
public class DaoModelTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
