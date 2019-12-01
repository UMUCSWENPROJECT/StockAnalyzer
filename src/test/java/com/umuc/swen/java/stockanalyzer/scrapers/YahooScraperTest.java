    
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer.scrapers;

import com.umuc.swen.java.stockanalyzer.scrapers.YahooScraper;
import java.io.File;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;
import com.umuc.swen.java.stockanalyzer.StockDao;
import com.umuc.swen.java.stockanalyzer.Utility;
import com.umuc.swen.java.stockanalyzer.daomodels.StockTicker;
import com.umuc.swen.java.stockanalyzer.daomodels.StockSummary;
import com.umuc.swen.java.stockanalyzer.daomodels.StockHistorical;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Test class for Yahoo scraper
 */
public class YahooScraperTest {
    StockDao dao;
    
    StockTicker stockTicker = new StockTicker();
    StockSummary ss = new StockSummary();
    StockHistorical sh = new StockHistorical();
    
    public YahooScraperTest() {
        try {
             File tempFile = File.createTempFile("dbTest", "sqlite");
            tempFile.deleteOnExit();
            String tempUrl = "jdbc:sqlite:" + tempFile.getAbsolutePath();
            
            dao = new StockDao();
            Class daoClass = dao.getClass();

            Field f1 = daoClass.getDeclaredField("dbName");
            f1.setAccessible(true);
            f1.set(dao, tempFile.getName());
            System.out.println("ATTENTION the temp file name is " + tempFile.getName());
            Field f2 = daoClass.getDeclaredField("url");
            f2.setAccessible(true);
            f2.set(dao, tempUrl);
            System.out.println("ATTENTION the temp URL is " + tempUrl);
            Field f3 = daoClass.getDeclaredField("instance");
            f3.setAccessible(true);
            f3.set(dao, null);
            
            dao.getTestInstance();
            dao.deleteAll();
            
            stockTicker.setName("Apple Inc.");
            stockTicker.setSymbol("AAPL");
            
            
            ss.setSource("Yahoo");
            ss.setTicker_symbol("AAPL");
            ss.setTicker_name("Apple Inc.");
            ss.setStock_record_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Date().toString())));
            ss.setPrevClosePrice(Utility.convertStringCurrency("195.35"));
            ss.setBetaCoefficient(Utility.convertStringCurrency("0.99"));
            ss.setDaysRangeMax(Utility.convertStringCurrency("196.36"));
            ss.setDaysRangeMin(Utility.convertStringCurrency("194.71"));
            ss.setDividentYield(Utility.convertStringCurrency("2.92/1.49%"));
            ss.setEps(Utility.convertStringCurrency("12.12"));
            ss.setFiftyTwoWeeksMax(Utility.convertStringCurrency("233.47"));
            ss.setFiftyTwoWeeksMin(Utility.convertStringCurrency("142.00"));
            ss.setMarketCap(Utility.convertStringCurrency("922.733B"));
            ss.setOpenPrice(Utility.convertStringCurrency("194.79"));
            ss.setPeRatio(Utility.convertStringCurrency("16.14"));
            ss.setVolume(Utility.convertStringCurrency("18,747,318.00").longValue());
            ss.setBidPrice(Utility.convertStringCurrency("195.43"));
            ss.setAskPrice(Utility.convertStringCurrency("195.56"));
            ss.setAvgVolume(30598950);
            ss.setEarningDate("Apr 30, 2019");
            ss.setExDividentDate("2019-02-08");
            ss.setOneYearTargetEst(Utility.convertStringCurrency("190.94"));

            //The information for the date of Nov 26 2018 - When this is tested against what should be the last day scraped.
            sh.setSource("Yahoo");
            sh.setTicker_symbol("AAPL");
            sh.setTicker_name("Apple Inc.");
            sh.setHistorical_date("2018-11-26");
            sh.setOpen(Utility.convertStringCurrency("174.24"));
            sh.setHigh(Utility.convertStringCurrency("174.95"));
            sh.setLow(Utility.convertStringCurrency("170.26"));
            sh.setClose(Utility.convertStringCurrency("174.62"));
            sh.setAdjClose(Utility.convertStringCurrency("173.87"));
            sh.setVolume(44998500);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   

    /**
     * Test of scapeSingleSummaryData method, of class YahooScraper.
     */
    @Test
    public void testScapeSingleSummaryData() {
                try{
            File input = new File("Apr_4_2019_AAPL_Yahoo.html");
            Document tmpDocument = Jsoup.parse(input, "UTF-8", "");

            YahooScraper ys = new YahooScraper();
            Class isClass = ys.getClass();

            Field f1 = isClass.getDeclaredField("test");
            f1.setAccessible(true);
            f1.set(ys, true);

            Field f2 = isClass.getDeclaredField("document");
            f2.setAccessible(true);
            f2.set(ys, tmpDocument);

            ys.scrapeSingleSummaryData(stockTicker);

            Field f3 = isClass.getDeclaredField("summaryData");
            f3.setAccessible(true);
            StockSummary results = (StockSummary)f3.get(ys);

            String expected = ss.toString();
            String actual = results.toString();
            System.out.println("Expected:" + expected);
            System.out.println("Actual:  " + actual);

            assertEquals(expected, actual);

            //truncate the data after test
            dao.getTestInstance();
            dao.deleteAll();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    /*
    * Test scraping of historical data
    */

    @Test
    public void testscrapeSingleHisotricalData() {
        try {
            File input = new File("Apr_19_2019_AAPL_Historical_Yahoo.html");
            Document tmpDocument = Jsoup.parse(input, "UTF-8", "");

            YahooScraper hys = new YahooScraper();
            Class isClass = hys.getClass();

            Field f1 = isClass.getDeclaredField("test");
            f1.setAccessible(true);
            f1.set(hys, true);

            Field f2 = isClass.getDeclaredField("document");
            f2.setAccessible(true);
            f2.set(hys, tmpDocument);
            
            hys.scrapeSingleHistoricalData(stockTicker);

            Field f3 = isClass.getDeclaredField("stockHistorical");
            f3.setAccessible(true);
            StockHistorical results = (StockHistorical)f3.get(hys);

            String expected = sh.toString();
            String actual = results.toString();
            System.out.println("Expected:" + expected);
            System.out.println("Actual:  " + actual);

            assertEquals(expected, actual);

            //truncate the data after test
            dao.getTestInstance();
            dao.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    @Test
    public void testscrapeSingleHisotricalData() {
        try {
            YahooScraper ys = new YahooScraper();
            Class isClass = ys.getClass();
            ys.scrapeSingleHistoricalData(stockTicker);
            Field f3 = isClass.getDeclaredField("historicalData");
            StockHistorical results = (StockHistorical)f3.get(ys);
            String expected = sh.toString();
            String actual = results.toString();
            assertEquals(expected, actual);
            StockDao instance = StockDao.getInstance();
            dao.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */
    
}
