package stockreporter.daomodels;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Stock date map test class
 */
public class StockDateMapTest {
    
    StockDateMap master = new StockDateMap();
    
    public StockDateMapTest() {
        master.setId(100);
        master.setSourceId(101);
        master.setTickerId(102);
        master.setDate("2019-04-01");
    }

    /* Test of getId
    * 
    */
    @Test
    public void testGetId() {
        long test = 100;
        assertEquals(master.getId(), test);
    }
    /* Test of setId
    * 
    */

    @Test
    public void testSetId() {
        StockDateMap instance = new StockDateMap();
        instance.setId(100);
        assertEquals(master.getId(), instance.getId());
    }

    /* Test of getDate
    * 
    */
    @Test
    public void testGetDate() {
        String test = "2019-04-01";
        assertEquals(master.getDate(), test);
    }

    /* Test of setDate
    * 
    */
    @Test
    public void testSetDate() {
        StockDateMap instance = new StockDateMap();
        instance.setDate("2019-04-01");
        assertEquals(master.getDate(), instance.getDate());
    }

    /* Test of getTickerId
    * 
    */
    @Test
    public void testGetTickerId() {
        long test = 102;
        assertEquals(master.getTickerId(), test);
    }

    /* Test of setTickerId
    * 
    */
    @Test
    public void testSetTickerId() {
        StockDateMap instance = new StockDateMap();
        instance.setTickerId(102);
        assertEquals(master.getTickerId(), instance.getTickerId());
    }

    /* Test of getSourceId
    * 
    */
    @Test
    public void testGetSourceId() {
        long test = 101;
        assertEquals(master.getSourceId(), test);
    }

    /* Test of setSourceId
    * 
    */
    @Test
    public void testSetSourceId() {
        StockDateMap instance = new StockDateMap();
        instance.setSourceId(101);
        assertEquals(master.getSourceId(), instance.getSourceId());
    }
    
}
