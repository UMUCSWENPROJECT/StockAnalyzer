package com.umuc.swen.java.stockanalyzer;

import com.umuc.swen.java.stockanalyzer.daomodels.StockLogs;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.umuc.swen.java.stockanalyzer.scrappers.YahooScraper;
import com.umuc.swen.java.stockanalyzer.scrappers.InvestopediaScraper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Main class for scrapping the data
 */
public class StockReporter {
    
    private static final Logger logger = Logger.getLogger(StockReporter.class.getName());
    
    public static void main(String[] args) {    
        
        logger.log(Level.INFO, "Get database instance");
        StockDao dao = StockDao.getInstance();
         Date stockDateStart = null;
        try{
            stockDateStart = new SimpleDateFormat("yyyy-MM-dd-hhmmss").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
        }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
        
        logger.log(Level.INFO, "Create Yahoo scraper instance");
        YahooScraper yahooScraper = new YahooScraper();
        
        StockLogs stockLog = new StockLogs();
        stockLog.setStart_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateStart).toString());
        List<String> exceptionLogs = new ArrayList<String>();
        try{
            stockLog.setSource("Yahoo");

            logger.log(Level.INFO, "Scrap summary data for Yahoo...");
            exceptionLogs = yahooScraper.scrapeAllSummaryData();
            Date stockDateEnd = null;
            try{
                stockDateEnd = new SimpleDateFormat("yyyy-MM-dd-hhmmss").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
            }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
            stockLog.setEnd_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateEnd).toString());
            stockLog.setStatus("Success");
            stockLog.setLog("Yahoo scrapper completed successfully!");
            if(!exceptionLogs.isEmpty()){
                stockLog.setStatus("Failed");
                stockLog.setLog("" + exceptionLogs);
                dao.insertLog(stockLog);
            }else{dao.insertLog(stockLog);}
            }catch(Exception e) {
                logger.log(Level.WARNING, "Issue has occured and an exception was thorwn during Yahoo Scrape");
                exceptionLogs.add(e.getMessage());
                Date stockDateEnd = null;
                try{
                    stockDateEnd = new SimpleDateFormat("yyyy-MM-dd-hhmmss").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
                }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
                stockLog.setEnd_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateEnd).toString());
                stockLog.setStatus("Failed");
                stockLog.setLog("" + exceptionLogs);
            }
            logger.log(Level.INFO, "Yahoo Scrape complete");
            
            logger.log(Level.INFO, "Scrap historical data for Yahoo...");
            
            try{
                stockDateStart = new SimpleDateFormat("yyyy-MM-dd-hhmmss").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
            }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
            
            stockLog.setStart_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateStart).toString());
            try{
                stockLog.setSource("Historical - Yahoo");
                
                yahooScraper.scrapeAllHistoricalData();
                
                Date stockDateEnd = null;
                
                try{
                    stockDateEnd = new SimpleDateFormat("yyyy-MM-dd-hhmmss").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
                }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
                stockLog.setEnd_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateEnd).toString());
                stockLog.setStatus("Success");
                stockLog.setLog("Historical - Yahoo scrapper completed successfully!");
                dao.insertLog(stockLog);
            
            }catch(Exception e) {
                logger.log(Level.WARNING, "Issue has occured and an exception was thorwn during Historical - Yahoo Scrape");
                Date stockDateEnd = null;
                try{
                    stockDateEnd = new SimpleDateFormat("yyyy-MM-dd-hhmmss").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
                }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
                stockLog.setEnd_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateEnd).toString());
                stockLog.setStatus("Failed");
                stockLog.setLog(e.getMessage());
                dao.insertLog(stockLog);
            }
            logger.log(Level.INFO, "Historical - Yahoo Scrape complete");
            
            InvestopediaScraper investopediaScraper = new InvestopediaScraper();
            
            logger.log(Level.INFO, "Scrap summary data for Investopedia...");
            
            try{
                stockDateStart = new SimpleDateFormat("yyyy-MM-dd-hhmmss").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
            }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
            
            stockLog.setStart_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateStart).toString());
            try{
                stockLog.setSource("Investopedia");
                
                exceptionLogs = investopediaScraper.scrapeAllSummaryData();
                Date stockDateEnd = null;
                
                try{
                    stockDateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
                }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
                stockLog.setEnd_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateEnd).toString());
                stockLog.setStatus("Success");
                stockLog.setLog("Investopedia scrapper completed successfully!");
                if(!exceptionLogs.isEmpty()){
                    stockLog.setStatus("Failed");
                    stockLog.setLog("" + exceptionLogs);
                    dao.insertLog(stockLog);
                }else{dao.insertLog(stockLog);}
            
            }catch(Exception e) {
                logger.log(Level.WARNING, "Issue has occured and an exception was thorwn during Investopedia Scrape");
                Date stockDateEnd = null;
                try{
                    stockDateEnd = new SimpleDateFormat("yyyy-MM-dd-hhmmss").parse(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date()));
                }catch(ParseException pe) {logger.log(Level.WARNING, pe.getMessage());}
                stockLog.setEnd_date(new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(stockDateEnd).toString());
                stockLog.setStatus("Failed");
                stockLog.setLog(e.getMessage());
                dao.insertLog(stockLog);
            }
    }
}