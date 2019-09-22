/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umuc.swen.java.stockanalyzer.scrappers;

import com.umuc.swen.java.stockanalyzer.scrappers.*;
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


/**
 * Scrap Yahoo stock financial data
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
        super();
    }
    
    /**
     * Scrap summary data
     */
    public void scrapeAllSummaryData(){
        for(StockTicker stockTicker: stockTickers)
            scrapeSingleSummaryData(stockTicker);
    }
    /**
     * Scrap historical data
     */
    public void scrapeAllHistoricalData(){
        for(StockTicker stockTicker: stockTickers)
            scrapeSingleHistoricalData(stockTicker);
    }
    /**
     * Scrap summary data by stock ticker
     * @param stockTicker 
     */
    public void scrapeSingleSummaryData(StockTicker stockTicker){     
        logger.log(Level.INFO,"Scrapping: "+stockTicker.getSymbol());
        
        String url = "https://finance.yahoo.com/quote/"+stockTicker.getSymbol().toLowerCase();
        try {
            if(!test){
            Connection jsoupConn = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
            document = jsoupConn.referrer("http://www.google.com") .timeout(1000*20).get();
            }
            Date stockDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if((latestScrappedDate!=null && stockDate.compareTo(latestScrappedDate) > 0) || latestScrappedDate == null){
                StockDateMap stockDateMap = new StockDateMap();
                stockDateMap.setSourceId(dao.getStockSourceIdByName(Constants.SCRAP_DATA_FROM_YAHOO));
                stockDateMap.setTickerId(stockTicker.getId());
                stockDateMap.setDate(new SimpleDateFormat("yyyy-MM-dd").format(stockDate));
                int last_inserted_id = dao.insertStockDateMap(stockDateMap);

                Element table1 = document.select("table").get(0);
                Elements rows = table1.select("tr");    
                summaryData = new StockSummary();

                summaryData.setStockDtMapId(last_inserted_id);

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
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
        }
    }
    
    /**
     * Scrap historical data
     * @param stockTicker 
     */
    public void scrapeSingleHistoricalData(StockTicker stockTicker){ 
        logger.log(Level.INFO,"Scrapping: "+stockTicker.getSymbol());
        
        String url = "https://finance.yahoo.com/quote/"+stockTicker.getSymbol().toLowerCase()+"/history?p="+stockTicker.getSymbol().toLowerCase();
        try {
            if(!test){
            Connection jsoupConn = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
            
            document = jsoupConn.referrer("http://www.google.com") .timeout(1000*20).get();
            }
            Element table1 = document.select("table").get(0);
            Elements rows = table1.select("tr");    
            
            for(int i=1; i<rows.size()-1; i++){
                stockHistorical = new StockHistorical();
                Element row = rows.get(i);
                Elements columns = row.select("td");
                if(columns.size() == 7){
                    String date = columns.get(0).text();
                    Date stockDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date(date)));
                    if((latestScrappedDate!=null && stockDate.compareTo(latestScrappedDate) > 0) || latestScrappedDate == null){
                        StockDateMap stockDateMap = new StockDateMap();
                        stockDateMap.setSourceId(dao.getStockSourceIdByName(Constants.SCRAP_DATA_FROM_YAHOO));
                        stockDateMap.setTickerId(stockTicker.getId());
                        stockDateMap.setDate(new SimpleDateFormat("yyyy-MM-dd").format(stockDate));
                        int last_inserted_id = dao.insertStockDateMap(stockDateMap);
                        stockHistorical.setStockDtMapId(last_inserted_id);

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
                    }
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
        }
    }    
}
