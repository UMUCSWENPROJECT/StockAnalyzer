package com.umuc.swen.java.stockanalyzer.daomodels;

import java.math.BigDecimal;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 * Test class for summary 
 */
public class StockSummaryTest {
    
    public StockSummaryTest() {
    }
    /* Test of getSummaryId
    * 
    */
        @Test
        public void testgetSummaryId () {
            StockSummary instance = new StockSummary();
            instance.setSummaryId(1);
            long expResult = 1;
            long result = instance.getSummaryId();
            assertEquals(expResult, result, 0.01);
        }
        
        /* Test of setSummaryId
    * 
    */
   	@Test
        public void testsetSummaryId () {
            StockSummary instance = new StockSummary();
            long summaryId = 2;
            long expResult = 2;
            instance.setSummaryId(summaryId);
            long result = instance.getSummaryId();
            assertEquals(expResult, result, 0.1);
        }

        /* Test of getPrevClosePrice
    * 
    */
	@Test
        public void testgetPrevClosePrice () {
            StockSummary instance = new StockSummary();
            BigDecimal prevClosePrice = new BigDecimal("2.00");
            instance.setPrevClosePrice(prevClosePrice);
            int expResult = prevClosePrice.intValue();
            int result = instance.getPrevClosePrice().intValue();
            assertEquals(expResult, result, 0.01);
        }

        /* Test of setPrevClosePrice
    * 
    */
	@Test
        public void testsetPrevClosePrice () {
            StockSummary instance = new StockSummary();
            BigDecimal prevClosePrice = new BigDecimal ("3.34");
            instance.setPrevClosePrice(prevClosePrice);
            int expResult = prevClosePrice.intValue();
            int result = instance.getPrevClosePrice().intValue();
            assertEquals(expResult, result, 0.1);
        }
	/* Test of getOpenPrice
    * 
    */
        @Test
        public void testgetOpenPrice () {
            StockSummary instance = new StockSummary();
            BigDecimal openPrice = new BigDecimal("2.13");
            instance.setOpenPrice(openPrice);
            int expResult = openPrice.intValue();
            int result = instance.getOpenPrice().intValue();
            assertEquals(expResult, result, 0.001);
            
        }
	
        /* Test of setOpenPrice
    * 
    */
        @Test
        public void testsetOpenPrice () {
            StockSummary instance = new StockSummary();
            BigDecimal openPrice = new BigDecimal("4.1");
            instance.setOpenPrice(openPrice);
            int expResult = openPrice.intValue();
            int result = instance.getOpenPrice().intValue();
            assertEquals(expResult, result, 0.1);
        }

        /* Test of getBidPrice
    * 
    */
	@Test
        public void testgetBidPrice () {
            StockSummary instance = new StockSummary();
            BigDecimal bidPrice = new BigDecimal("3.2");
            instance.setBidPrice(bidPrice);
            int expResult = bidPrice.intValue();
            int result = instance.getBidPrice().intValue();
            assertEquals(expResult, result, 0.1);
        }

        /* Test of setBidPrice
    * 
    */
	@Test
        public void testsetBidPrice () {
            StockSummary instance = new StockSummary();
            BigDecimal bidPrice = new BigDecimal("3.2");
            instance.setBidPrice(bidPrice);
            int expResult = bidPrice.intValue();
            int result = instance.getBidPrice().intValue();
            assertEquals(expResult, result, 0.1);
        }

        /* Test of getAskPrice
    * 
    */
	@Test
        public void testgetAskPrice () {
            StockSummary instance = new StockSummary();
            BigDecimal askPrice = new BigDecimal("1.2");
            instance.setAskPrice(askPrice);
            int expResult = askPrice.intValue();
            int result = instance.getAskPrice().intValue();
            assertEquals(expResult, result, 0.1);
        }
	
        /* Test of setAskPrice
    * 
    */
        @Test
        public void testsetAskPrice () {
          StockSummary instance = new StockSummary();
          BigDecimal askPrice = new BigDecimal("1.2");
          instance.setAskPrice(askPrice);
          int expResult = askPrice.intValue();
          int result = instance.getAskPrice().intValue();
          assertEquals(expResult, result, 0.1);  
        }
	
        /* Test of getDaysRangeMin
    * 
    */
        @Test
        public void testgetDaysRangeMin () {
          StockSummary instance = new StockSummary();
          BigDecimal daysRangeMin = new BigDecimal("4.24");
          instance.setDaysRangeMin(daysRangeMin);
          int expResult = daysRangeMin.intValue();
          int result = instance.getDaysRangeMin().intValue();
          assertEquals(expResult, result, 0.1); 
        }

        /* Test of setDaysRangeMin
    * 
    */
	@Test
        public void testsetDaysRangeMin () {
           StockSummary instance = new StockSummary();
          BigDecimal daysRangeMin = new BigDecimal("4.04");
          instance.setDaysRangeMin(daysRangeMin);
          int expResult = daysRangeMin.intValue();
          int result = instance.getDaysRangeMin().intValue();
          assertEquals(expResult, result, 0.1);  
        }

        /* Test of getDaysRangeMax
    * 
    */
        @Test
        public void testgetDaysRangeMax () {
           StockSummary instance = new StockSummary();
          BigDecimal daysRangeMax = new BigDecimal("2.04");
          instance.setDaysRangeMax(daysRangeMax);
          int expResult = daysRangeMax.intValue();
          int result = instance.getDaysRangeMax().intValue();
          assertEquals(expResult, result, 0.1);  
        }
	
        /* Test of setDaysRangeMax
    * 
    */
	@Test
        public void testsetDaysRangeMax () {
           StockSummary instance = new StockSummary();
          BigDecimal daysRangeMax = new BigDecimal("2.22");
          instance.setDaysRangeMax(daysRangeMax);
          int expResult = daysRangeMax.intValue();
          int result = instance.getDaysRangeMax().intValue();
          assertEquals(expResult, result, 0.01);  
        }

        /* Test of getFiftyTwoWeeksMin
    * 
    */
	@Test
        public void testgetFiftyTwoWeeksMin () {
           StockSummary instance = new StockSummary();
          BigDecimal fiftyTwoWeeksMin = new BigDecimal("2.25");
          instance.setFiftyTwoWeeksMin(fiftyTwoWeeksMin);
          int expResult = fiftyTwoWeeksMin.intValue();
          int result = instance.getFiftyTwoWeeksMin().intValue();
          assertEquals(expResult, result, 0.01);  
        }

        /* Test of setFiftyTwoWeeksMin
    * 
    */
        @Test
        public void testsetFiftyTwoWeeksMin () {
          StockSummary instance = new StockSummary();
          BigDecimal fiftyTwoWeeksMin = new BigDecimal("2.25");
          instance.setFiftyTwoWeeksMin(fiftyTwoWeeksMin);
          int expResult = fiftyTwoWeeksMin.intValue();
          int result = instance.getFiftyTwoWeeksMin().intValue();
          assertEquals(expResult, result, 0.01);  
        }
        
        /* Test of getFiftyTwoWeeksMax
    * 
    */
        @Test
        public void testgetFiftyTwoWeeksMax () {
          StockSummary instance = new StockSummary();
          BigDecimal fiftyTwoWeeksMax = new BigDecimal("2.35");
          instance.setFiftyTwoWeeksMax(fiftyTwoWeeksMax);
          int expResult = fiftyTwoWeeksMax.intValue();
          int result = instance.getFiftyTwoWeeksMax().intValue();
          assertEquals(expResult, result, 0.01);  
        }

        /* Test of setFiftyTwoWeeksMax
    * 
    */
        @Test
        public void testsetFiftyTwoWeeksMax () {
          StockSummary instance = new StockSummary();
          BigDecimal fiftyTwoWeeksMax = new BigDecimal("2.35");
          instance.setFiftyTwoWeeksMax(fiftyTwoWeeksMax);
          int expResult = fiftyTwoWeeksMax.intValue();
          int result = instance.getFiftyTwoWeeksMax().intValue();
          assertEquals(expResult, result, 0.01);  
        }
	
        /* Test of getVolume
    * 
    */
        @Test
        public void testgetVolume () {
           StockSummary instance = new StockSummary();
           long volume = 3;
           instance.setVolume(volume);
           long expResult = 3;
           long result = instance.getVolume();
           assertEquals(expResult, result, 0.1);
        }
        
        /* Test of setVolume
    * 
    */
        @Test
        public void testsetVolume () {
           StockSummary instance = new StockSummary();
           long volume = 4;
           instance.setVolume(volume);
           long expResult = 4;
           long result = instance.getVolume();
           assertEquals(expResult, result, 0.1);
        }
	
        /* Test of getavgVolume
    * 
    */
        @Test
        public void testgetavgVolume () {
           StockSummary instance = new StockSummary();
           long avgVolume = 1;
           instance.setAvgVolume(avgVolume);
           long expResult = 1;
           long result = instance.getAvgVolume();
           assertEquals(expResult, result, 0.1);
        }
	
        /* Test of setavgVolume
    * 
    */
        @Test
        public void testsetavgVolume () {
           StockSummary instance = new StockSummary();
           long avgVolume = 5;
           instance.setAvgVolume(avgVolume);
           long expResult = 5;
           long result = instance.getAvgVolume();
           assertEquals(expResult, result, 0.1);
        }

        /* This was causing errors in the IDE as already existing so commenting out for now. -Jason
	@Test
        public void testgetMarketCap () {
           StockSummary instance = new StockSummary();
           long avgVolume = 3;
           instance.setAvgVolume(avgVolume);
           long expResult = 3;
           long result = instance.getAvgVolume();
           assertEquals(expResult, result, 0.1);
        }
        */

        /* Test of getMarketCap
    * 
    */
        @Test
        public void testgetMarketCap () {
          StockSummary instance = new StockSummary();
          BigDecimal marketCap = new BigDecimal("2.35");
          instance.setMarketCap(marketCap);
          int expResult = marketCap.intValue();
          int result = instance.getMarketCap().intValue();
          assertEquals(expResult, result, 0.01);  
        }
        
        /* Test of setMarketCap
    * 
    */
        @Test
        public void testsetMarketCap () {
          StockSummary instance = new StockSummary();
          BigDecimal marketCap = new BigDecimal("2.15");
          instance.setMarketCap(marketCap);
          int expResult = marketCap.intValue();
          int result = instance.getMarketCap().intValue();
          assertEquals(expResult, result, 0.01);  
        }
	
        /* Test of getBetaCoefficient
    * 
    */
        @Test
        public void testgetBetaCoefficient () {
          StockSummary instance = new StockSummary();
          BigDecimal betaCoefficient = new BigDecimal("2.05");
          instance.setBetaCoefficient(betaCoefficient);
          int expResult = betaCoefficient.intValue();
          int result = instance.getBetaCoefficient().intValue();
          assertEquals(expResult, result, 0.01);  
        }
	

        /* Test of setBetaCoeficcient
    * 
    */
	@Test
        public void testsetBetaCoefficient () {
          StockSummary instance = new StockSummary();
          BigDecimal betaCoefficient = new BigDecimal("1.05");
          instance.setBetaCoefficient(betaCoefficient);
          int expResult = betaCoefficient.intValue();
          int result = instance.getBetaCoefficient().intValue();
          assertEquals(expResult, result, 0.01);  
        }

        /* Test of getPetRatio
    * 
    */
	@Test
        public void testgetPeRatio () {
          StockSummary instance = new StockSummary();
          BigDecimal peRatio = new BigDecimal("2.05");
          instance.setPeRatio(peRatio);
          int expResult = peRatio.intValue();
          int result = instance.getPeRatio().intValue();
          assertEquals(expResult, result, 0.01);  
        }
        
        /* Test of setPetRatio
    * 
    */
        @Test
        public void testsetPeRatio () {
          StockSummary instance = new StockSummary();
          BigDecimal peRatio = new BigDecimal("2.45");
          instance.setPeRatio(peRatio);
          int expResult = peRatio.intValue();
          int result = instance.getPeRatio().intValue();
          assertEquals(expResult, result, 0.01);  
        }

        /* Test of getEps
    * 
    */
        @Test
        public void testgetEps () {
          StockSummary instance = new StockSummary();
          BigDecimal eps = new BigDecimal("2.54");
          instance.setEps(eps);
          int expResult = eps.intValue();
          int result = instance.getEps().intValue();
          assertEquals(expResult, result, 0.01);  
        }
	
        /* Test of setEps
    * 
    */
        @Test
        public void testsetEps () {
          StockSummary instance = new StockSummary();
          BigDecimal eps = new BigDecimal("1.54");
          instance.setEps(eps);
          int expResult = eps.intValue();
          int result = instance.getEps().intValue();
          assertEquals(expResult, result, 0.01);  
        }

        /* Test of getEarningDate
    * 
    */
	@Test
        public void testgetEarningDate () {
          StockSummary instance = new StockSummary();
          String earningDate = "2019-03-25";
          instance.setEarningDate(earningDate);
          String expResult = earningDate;
          String result = instance.getEarningDate();
          assertEquals(expResult, result);  
        }

        /* Test of setEarningDate
    * 
    */
	@Test
        public void testsetEarningDate () {
          StockSummary instance = new StockSummary();
          String earningDate = "2019-03-21";
          instance.setEarningDate(earningDate);
          String expResult = earningDate;
          String result = instance.getEarningDate();
          assertEquals(expResult, result);  
        }

        /* Test of getDividentYield
    * 
    */
        @Test
        public void testgetDividentYield() {
          StockSummary instance = new StockSummary();
          BigDecimal dividentYield = new BigDecimal("1.45");
          instance.setDividentYield(dividentYield);
          int expResult = dividentYield.intValue();
          int result = instance.getDividentYield().intValue();
          assertEquals(expResult, result, 0.01);  
        }
	
        /* Test of setDividentYield
    * 
    */
	//Left Off here 
        @Test
        public void testsetDividentYield() {
          StockSummary instance = new StockSummary();
          BigDecimal dividentYield = new BigDecimal("0.45");
          instance.setDividentYield(dividentYield);
          int expResult = dividentYield.intValue();
          int result = instance.getDividentYield().intValue();
          assertEquals(expResult, result, 0.01);  
        }
	
/* Test of getexDividentDate
    * 
    */
	
        @Test
        public void testgetExDividentDate() {
          StockSummary instance = new StockSummary();
          String exDividentDate = "2019-03-11";
          instance.setExDividentDate(exDividentDate);
          String expResult = exDividentDate;
          String result = instance.getExDividentDate();
          assertEquals(expResult, result);  
        }
	
        /* Test of setExDividentDate
    * 
    */
        @Test
        public void testsetExDividentDate() {
          StockSummary instance = new StockSummary();
          String exDividentDate = "2019-03-27";
          instance.setExDividentDate(exDividentDate);
          String expResult = exDividentDate;
          String result = instance.getExDividentDate();
          assertEquals(expResult, result);  
        }
        
        /* Test of getOneYearTargetEst
    * 
    */
        @Test
        public void testgetOneYearTargetEst() {
          StockSummary instance = new StockSummary();
          BigDecimal oneYearTargetEst = new BigDecimal("1.18");
          instance.setOneYearTargetEst(oneYearTargetEst);
          int expResult = oneYearTargetEst.intValue();
          int result = instance.getOneYearTargetEst().intValue();
          assertEquals(expResult, result, 0.01);  
        }
        
        /* Test of setOneYearTargetEst
    * 
    */
        @Test
        public void testsetOneYearTargetEst() {
          StockSummary instance = new StockSummary();
          BigDecimal oneYearTargetEst = new BigDecimal("1.05");
          instance.setOneYearTargetEst(oneYearTargetEst);
          int expResult = oneYearTargetEst.intValue();
          int result = instance.getOneYearTargetEst().intValue();
          assertEquals(expResult, result, 0.01);  
        }

        /* Test of getStockDtMapId
    * 
    */
        @Test
        public void testgetStockDtMapId () {
            StockSummary instance = new StockSummary();
            long stockDtMapId = 7;
            long expResult = 7;
            instance.setStockDtMapId(stockDtMapId);
            long result = instance.getStockDtMapId();
            assertEquals(expResult, result, 0.1);
        }
        
        /* Test of setStockDtMapId
    * 
    */
        @Test
        public void testsetStockDtMapId () {
            StockSummary instance = new StockSummary();
            long stockDtMapId = 5;
            long expResult = 5;
            instance.setStockDtMapId(stockDtMapId);
            long result = instance.getStockDtMapId();
            assertEquals(expResult, result, 0.1);
        }
        
       
}
