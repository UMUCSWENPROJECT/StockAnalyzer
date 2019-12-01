/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer.scrapers;

import java.io.IOException;
import java.text.ParseException;
import com.umuc.swen.java.stockanalyzer.daomodels.StockSummary;
import com.umuc.swen.java.stockanalyzer.daomodels.StockTicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.umuc.swen.java.stockanalyzer.Constants;
import com.umuc.swen.java.stockanalyzer.Utility;
import com.umuc.swen.java.stockanalyzer.daomodels.StockDateMap;
import com.umuc.swen.java.stockanalyzer.daomodels.StockHistorical;
import com.umuc.swen.java.stockanalyzer.StockDao;
import java.util.ArrayList;
import java.util.List;


/**
 * ScrapeYahoo stock financial data
 */
public class YahooScraper extends StockScraper {
    
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     * default constructor
     */
    private boolean test = false;
    private Document document;
    private StockSummary summaryData;
    private StockHistorical stockHistorical;
    
    public YahooScraper(){
        super("Yahoo");
    }
    
    /**
     * Scrapesummary data
     */
    public List scrapeAllSummaryData() {
        List<String> exceptionLogs = new ArrayList<String>();
        int tickerCount = 0;

            for(StockTicker stockTicker: stockTickers){
               try{
                scrapeSingleSummaryData(stockTicker);
                tickerCount++;
                }catch(Exception e) {
                    exceptionLogs.add(stockTickers.get(tickerCount).getSymbol() + ": " + e.getMessage());
                    tickerCount++;
                }
            }
        return exceptionLogs;
    }
    /**
     * Scrapesummary data by source and ticker symbol
     */
    public List scrapeAllSummaryData(String src) {
        List<String> exceptionLogs = new ArrayList<String>();
        int tickerCount = 0;

            for(StockTicker stockTicker: stockTickers){
               try{
                    setLatestScrapedDate(StockDao.getInstance().getLatestScrapedDate(src, stockTicker.getSymbol()));
                }catch(Exception e){
                    logger.log(Level.WARNING, "Exception thrown from scrapeall summary data with source when setting latest scrapedate from error: {0}", e.getMessage());
                   try {
                       setLatestScrapedDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(0))));
                   } catch (ParseException ex) {
                       Logger.getLogger(YahooScraper.class.getName()).log(Level.SEVERE, "Parse exception thrown from scrapeall summary data when trying to set default date. Unable to parse latest scrapedate from error: " + ex);
                   }
                }
                try{
                    logger.log(Level.INFO, "Scraping source: {0} for ticker: {1} with latest date of: {2} ", new Object[]{src, stockTicker.getSymbol(), latestScrapedDate.toString()});
                    scrapeSingleSummaryData(stockTicker);
                    tickerCount++;
                }catch(Exception e) {
                    exceptionLogs.add(stockTickers.get(tickerCount).getSymbol() + ": " + e.getMessage());
                    tickerCount++;
                }
            }
        return exceptionLogs;
    }
    /**
     * Scrapehistorical data
     */
    public void scrapeAllHistoricalData() throws Exception{
        for(StockTicker stockTicker: stockTickers)
            try{
                scrapeSingleHistoricalData(stockTicker);
            }catch(Exception e){throw e;}
    }
    
    /**
     * Scrapehistorical data by source and ticker symbol
     */
    public void scrapeAllHistoricalData(String src) throws Exception{
        List<String> exceptionLogs = new ArrayList<String>();
        int tickerCount = 0;
        
        for(StockTicker stockTicker: stockTickers){
            try{
                setLatestHistoricalDate(StockDao.getInstance().getLatestHistoricalScrapedDate(src, stockTicker.getSymbol()));
            }catch(Exception e){
                logger.log(Level.WARNING, "Exception thrown from scrapeall historical data with source when setting latest scrapedate from error: {0}", e.getMessage());
                try {
                    setLatestHistoricalDate(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(0))));
                } catch (ParseException ex) {
                    Logger.getLogger(YahooScraper.class.getName()).log(Level.SEVERE, "Parse exception thrown from scrapeall hsitorical data when trying to set default date. Unable to parse latest scrapedate from error: " + ex);
                }  
            }   
            try{
                logger.log(Level.INFO, "Historical Scraping source: {0} for ticker: {1} with latest date of: {2} ", new Object[]{src, stockTicker.getSymbol(), latestScrapedDate.toString()});
                scrapeSingleHistoricalData(stockTicker);
                tickerCount++;
            }catch(Exception e){
                exceptionLogs.add(stockTickers.get(tickerCount).getSymbol() + ": " + e.getMessage());
                tickerCount++;
            }
        }
    }
    /**
     * Scrapesummary data by stock ticker
     * @param stockTicker 
     */
    public void scrapeSingleSummaryData(StockTicker stockTicker) throws IOException, ParseException, Exception {     
        logger.log(Level.INFO, "Scraping: {0}", stockTicker.getSymbol());
        
        String url = "https://finance.yahoo.com/quote/"+stockTicker.getSymbol().toLowerCase();
        try {
            if(!test){
            Connection jsoupConn = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
            document = jsoupConn.referrer("http://www.google.com") .timeout(1000*20).get();
            }
            
            Date stockDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
          
            if(!test){
                if((latestScrapedDate!=null && stockDate.compareTo(latestScrapedDate) > 0) || latestScrapedDate==null){
                    logger.log(Level.INFO, "Conditions for dates met running scrap!");
                    Element table1 = document.select("table").get(0);
                    Elements rows = table1.select("tr");    
                    summaryData = new StockSummary();

                    summaryData.setSource(dao.getStockSourceByName(Constants.SCRAPE_DATA_FROM_YAHOO));
                    summaryData.setTicker_symbol(stockTicker.getSymbol());
                    summaryData.setTicker_name(stockTicker.getTicker_name());
                    summaryData.setStock_record_date(new SimpleDateFormat("yyyy-MM-dd").format(stockDate).toString());

                    int rowNum=0;
                    String prevClosePrice = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setPrevClosePrice(Utility.convertStringCurrency(Utility.isBlank(prevClosePrice)?"0":prevClosePrice));
                    rowNum++;

                    String openPrice = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setOpenPrice(Utility.convertStringCurrency(Utility.isBlank(openPrice)?"0":openPrice));
                    rowNum++;

                    String bidPrice = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setBidPrice(Utility.convertStringCurrency(Utility.isBlank(bidPrice)?"0":bidPrice));
                    rowNum++;

                    String askPrice = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setAskPrice(Utility.convertStringCurrency(Utility.isBlank(askPrice)?"0":askPrice));
                    rowNum++;

                    String daysRangeMin = Utility.getRangeMinAndMax(rows.get(rowNum).select("td").get(1).text())[0].trim();
                    summaryData.setDaysRangeMin(Utility.convertStringCurrency(Utility.isBlank(daysRangeMin)?"0":daysRangeMin));
                    String daysRangeMax = Utility.getRangeMinAndMax(rows.get(rowNum).select("td").get(1).text())[1].trim();
                    summaryData.setDaysRangeMax(Utility.convertStringCurrency(Utility.isBlank(daysRangeMax)?"0":daysRangeMax)); 
                    rowNum++;

                    String fiftyTwoWeeksMin = Utility.getRangeMinAndMax(rows.get(rowNum).select("td").get(1).text())[0].trim();
                    summaryData.setFiftyTwoWeeksMin(Utility.convertStringCurrency(Utility.isBlank(fiftyTwoWeeksMin)?"0":fiftyTwoWeeksMin));
                    String fiftyTwoWeeksMax = Utility.getRangeMinAndMax(rows.get(rowNum).select("td").get(1).text().trim())[1].trim();
                    summaryData.setFiftyTwoWeeksMax(Utility.convertStringCurrency(Utility.isBlank(fiftyTwoWeeksMax)?"0":fiftyTwoWeeksMax));
                    rowNum++;

                    String volume = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setVolume(Utility.convertStringCurrency(Utility.isBlank(volume)?"0":volume).longValue());
                    rowNum++;

                    String avgVolume = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setAvgVolume(Utility.convertStringCurrency(Utility.isBlank(avgVolume)?"0":avgVolume).longValue());


                    rowNum=0;
                    Element table2 = document.select("table").get(1);
                    rows = table2.select("tr");    

                    String marketCap = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setMarketCap(Utility.convertStringCurrency(Utility.isBlank(marketCap)?"0":marketCap));
                    rowNum++;

                    String betaCoefficient = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setBetaCoefficient(Utility.convertStringCurrency(Utility.isBlank(betaCoefficient)?"0":betaCoefficient));
                    rowNum++;

                    String peRatio = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setPeRatio(Utility.convertStringCurrency(Utility.isBlank(peRatio)?"0":peRatio));
                    rowNum++;

                    String eps = rows.get(rowNum).select("td").get(1).text();
                        summaryData.setEps(Utility.convertStringCurrency(Utility.isBlank(eps)?"0":eps));
                    rowNum++;

                    String earningDate = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setEarningDate(earningDate);
                    rowNum++;

                    String dividend = Utility.getNumberBeforeParantheses(rows.get(rowNum).select("td").get(1).text()).trim();
                    summaryData.setDividentYield(Utility.convertStringCurrency(Utility.isBlank(dividend)?"0":dividend));
                    rowNum++;

                    String exDividendDate = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setExDividentDate(exDividendDate);
                    rowNum++;

                    String onYearTargetEst = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setOneYearTargetEst(Utility.convertStringCurrency(Utility.isBlank(onYearTargetEst)?"0":onYearTargetEst));

                    dao.insertStockSummaryData(summaryData);
                }
            }
            
            if(test){
                Element table1 = document.select("table").get(0);
                Elements rows = table1.select("tr");    
                summaryData = new StockSummary();
                
                summaryData.setSource(dao.getStockSourceByName(Constants.SCRAPE_DATA_FROM_YAHOO));
                summaryData.setTicker_symbol(stockTicker.getSymbol());
                summaryData.setTicker_name(stockTicker.getTicker_name());
                summaryData.setStock_record_date(new SimpleDateFormat("yyyy-MM-dd").format(stockDate).toString());

                int rowNum=0;
                String prevClosePrice = rows.get(rowNum).select("td").get(1).text();
                summaryData.setPrevClosePrice(Utility.convertStringCurrency(Utility.isBlank(prevClosePrice)?"0":prevClosePrice));
                rowNum++;

                String openPrice = rows.get(rowNum).select("td").get(1).text();
                summaryData.setOpenPrice(Utility.convertStringCurrency(Utility.isBlank(openPrice)?"0":openPrice));
                rowNum++;

                String bidPrice = rows.get(rowNum).select("td").get(1).text();
                summaryData.setBidPrice(Utility.convertStringCurrency(Utility.isBlank(bidPrice)?"0":bidPrice));
                rowNum++;

                String askPrice = rows.get(rowNum).select("td").get(1).text();
                summaryData.setAskPrice(Utility.convertStringCurrency(Utility.isBlank(askPrice)?"0":askPrice));
                rowNum++;

                String daysRangeMin = Utility.getRangeMinAndMax(rows.get(rowNum).select("td").get(1).text())[0].trim();
                summaryData.setDaysRangeMin(Utility.convertStringCurrency(Utility.isBlank(daysRangeMin)?"0":daysRangeMin));
                String daysRangeMax = Utility.getRangeMinAndMax(rows.get(rowNum).select("td").get(1).text())[1].trim();
                summaryData.setDaysRangeMax(Utility.convertStringCurrency(Utility.isBlank(daysRangeMax)?"0":daysRangeMax)); 
                rowNum++;

                String fiftyTwoWeeksMin = Utility.getRangeMinAndMax(rows.get(rowNum).select("td").get(1).text())[0].trim();
                summaryData.setFiftyTwoWeeksMin(Utility.convertStringCurrency(Utility.isBlank(fiftyTwoWeeksMin)?"0":fiftyTwoWeeksMin));
                String fiftyTwoWeeksMax = Utility.getRangeMinAndMax(rows.get(rowNum).select("td").get(1).text().trim())[1].trim();
                summaryData.setFiftyTwoWeeksMax(Utility.convertStringCurrency(Utility.isBlank(fiftyTwoWeeksMax)?"0":fiftyTwoWeeksMax));
                rowNum++;

                String volume = rows.get(rowNum).select("td").get(1).text();
                summaryData.setVolume(Utility.convertStringCurrency(Utility.isBlank(volume)?"0":volume).longValue());
                rowNum++;

                String avgVolume = rows.get(rowNum).select("td").get(1).text();
                summaryData.setAvgVolume(Utility.convertStringCurrency(Utility.isBlank(avgVolume)?"0":avgVolume).longValue());


                rowNum=0;
                Element table2 = document.select("table").get(1);
                rows = table2.select("tr");    

                String marketCap = rows.get(rowNum).select("td").get(1).text();
                summaryData.setMarketCap(Utility.convertStringCurrency(Utility.isBlank(marketCap)?"0":marketCap));
                rowNum++;

                String betaCoefficient = rows.get(rowNum).select("td").get(1).text();
                summaryData.setBetaCoefficient(Utility.convertStringCurrency(Utility.isBlank(betaCoefficient)?"0":betaCoefficient));
                rowNum++;

                String peRatio = rows.get(rowNum).select("td").get(1).text();
                summaryData.setPeRatio(Utility.convertStringCurrency(Utility.isBlank(peRatio)?"0":peRatio));
                rowNum++;

                String eps = rows.get(rowNum).select("td").get(1).text();
                    summaryData.setEps(Utility.convertStringCurrency(Utility.isBlank(eps)?"0":eps));
                rowNum++;

                String earningDate = rows.get(rowNum).select("td").get(1).text();
                summaryData.setEarningDate(earningDate);
                rowNum++;

                String dividend = Utility.getNumberBeforeParantheses(rows.get(rowNum).select("td").get(1).text()).trim();
                summaryData.setDividentYield(Utility.convertStringCurrency(Utility.isBlank(dividend)?"0":dividend));
                rowNum++;

                String exDividendDate = rows.get(rowNum).select("td").get(1).text();
                summaryData.setExDividentDate(exDividendDate);
                rowNum++;

                String onYearTargetEst = rows.get(rowNum).select("td").get(1).text();
                summaryData.setOneYearTargetEst(Utility.convertStringCurrency(Utility.isBlank(onYearTargetEst)?"0":onYearTargetEst));

                dao.insertTestStockSummaryData(summaryData);
            }
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "IOException thrown from Yahoo scraper when scraping single summary data from error: " + ex.getLocalizedMessage());
            throw ex;
        } catch (ParseException px) {
            logger.log(Level.SEVERE, "ParseException thrown from Yahoo scraper when scraping single summary data from error: " + px.getLocalizedMessage());
            throw px;
        }catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown from Yahoo scraper when scraping single summary data from error: " + e.getLocalizedMessage());
            throw e;
        }
    }
    
    /**
     * Scrapehistorical data
     * @param stockTicker 
     * @throws java.io.IOException 
     * @throws java.text.ParseException 
     * @throws java.lang.Exception 
     */
    public void scrapeSingleHistoricalData(StockTicker stockTicker) throws IOException, ParseException, Exception { 
        logger.log(Level.INFO, "Scraping: {0}", stockTicker.getSymbol());
        
        String url = "https://finance.yahoo.com/quote/"+stockTicker.getSymbol().toLowerCase()+"/history?p="+stockTicker.getSymbol().toLowerCase();
        try {
            if(!test){
                logger.log(Level.INFO, "PERFROMING REGULAR EXECUTION");
            Connection jsoupConn = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
            
            document = jsoupConn.referrer("http://www.google.com") .timeout(1000*20).get();
            }
            
            if(test){
                logger.log(Level.INFO, "EXECUTING TEST CASE");
                Element table1 = document.select("table").get(0);
                Elements rows = table1.select("tr");    
            
                for(int i=1; i<rows.size()-1; i++){
                    stockHistorical = new StockHistorical();
                    Element row = rows.get(i);
                    Elements columns = row.select("td");
                    if(columns.size() == 7){
                        String date = columns.get(0).text();
                        Date stockDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(date)));
                        try{
                            stockHistorical.setSource(dao.getStockSourceByName(Constants.SCRAPE_DATA_FROM_YAHOO));
                            stockHistorical.setTicker_symbol(stockTicker.getSymbol());
                            stockHistorical.setTicker_name(stockTicker.getTicker_name());
                            stockHistorical.setHistorical_date(new SimpleDateFormat("yyyy-MM-dd").format(stockDate));
                            logger.log(Level.INFO, "Source is {0} " + stockHistorical.getSource());
                            logger.log(Level.INFO, "Ticker Symbol is {0} " + stockHistorical.getTicker_symbol());
                            logger.log(Level.INFO, "Ticker Name is {0} " + stockHistorical.getTicker_name());
                            logger.log(Level.INFO, "Historical Date is {0} " + stockHistorical.getHistorical_date());
                            
                            
                            
                            String openPrice = columns.get(1).text();
                            stockHistorical.setOpen(Utility.convertStringCurrency(Utility.isBlank(openPrice)?"0":openPrice));
                            String high = columns.get(2).text();
                            stockHistorical.setHigh(Utility.convertStringCurrency(Utility.isBlank(high)?"0":high));
                            String low = columns.get(3).text();
                            stockHistorical.setLow(Utility.convertStringCurrency(Utility.isBlank(low)?"0":low));
                            String close = columns.get(4).text();
                            stockHistorical.setClose(Utility.convertStringCurrency(Utility.isBlank(close)?"0":close));
                            String adjClose = columns.get(5).text();
                            stockHistorical.setAdjClose(Utility.convertStringCurrency(Utility.isBlank(adjClose)?"0":adjClose));
                            String volume = columns.get(6).text();
                            stockHistorical.setVolume(Utility.convertStringCurrency(Utility.isBlank(volume)?"0":volume).longValue());
                            dao.insertTestStockHistoricalData(stockHistorical);
                        }catch(Exception e){throw e;}
                    }
                }
            }
            if(!test){
                Element table1 = document.select("table").get(0);
                Elements rows = table1.select("tr");    

                for(int i=1; i<rows.size()-1; i++){
                    stockHistorical = new StockHistorical();
                    Element row = rows.get(i);
                    Elements columns = row.select("td");
                    if(columns.size() == 7){
                        String date = columns.get(0).text();
                        Date stockDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(date)));
                        if((latestHistoricalDate!=null && stockDate.compareTo(latestHistoricalDate) > 0)){
                            try{
                                stockHistorical.setSource(dao.getStockSourceByName(Constants.SCRAPE_DATA_FROM_YAHOO));
                                stockHistorical.setTicker_symbol(stockTicker.getSymbol());
                                stockHistorical.setTicker_name(stockTicker.getTicker_name());
                                stockHistorical.setHistorical_date(new SimpleDateFormat("yyyy-MM-dd").format(stockDate));

                                String openPrice = columns.get(1).text();
                                stockHistorical.setOpen(Utility.convertStringCurrency(Utility.isBlank(openPrice)?"0":openPrice));
                                String high = columns.get(2).text();
                                stockHistorical.setHigh(Utility.convertStringCurrency(Utility.isBlank(high)?"0":high));
                                String low = columns.get(3).text();
                                stockHistorical.setLow(Utility.convertStringCurrency(Utility.isBlank(low)?"0":low));
                                String close = columns.get(4).text();
                                stockHistorical.setClose(Utility.convertStringCurrency(Utility.isBlank(close)?"0":close));
                                String adjClose = columns.get(5).text();
                                stockHistorical.setAdjClose(Utility.convertStringCurrency(Utility.isBlank(adjClose)?"0":adjClose));
                                String volume = columns.get(6).text();
                                stockHistorical.setVolume(Utility.convertStringCurrency(Utility.isBlank(volume)?"0":volume).longValue());
                                dao.insertStockHistoricalData(stockHistorical);
                            }catch(Exception e){throw e;}
                        }
                    }
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            throw ex;
        } catch (ParseException px) {
            logger.log(Level.SEVERE, px.getLocalizedMessage());
            throw px;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage()); 
            throw e;
        }
    }
    public StockHistorical getStockHistorical() {return this.stockHistorical;}
}
