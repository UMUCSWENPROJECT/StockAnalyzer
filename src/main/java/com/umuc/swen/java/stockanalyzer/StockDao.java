package com.umuc.swen.java.stockanalyzer;

import com.umuc.swen.java.stockanalyzer.daomodels.StockSummary;
import com.umuc.swen.java.stockanalyzer.daomodels.StockTicker;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.umuc.swen.java.stockanalyzer.daomodels.StockDateMap;
import com.umuc.swen.java.stockanalyzer.daomodels.StockHistorical;
import com.umuc.swen.java.stockanalyzer.daomodels.StockLogs;

/**
 * This is the Data Access Layer (DAO) between database and business logic
 * It contains all CRUD and DDL statements to database operation
 */
public final class StockDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static StockDao instance = null;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    
    //For testing change the name to something like "stockreporter.test"
    // and drop the database after testing
    private String dbName = "stockreporter.prod";
    private String url = "jdbc:sqlite:stockreporter.prod";
    
    /**
     * Default constructor to check if database exist otherwise
     * create new database with tables, views and indexes
     */
    public StockDao() {
            if(!databaseAlreadyInitialized()){
                ArrayList<String> sqlStrings = new ArrayList<>();
                //Create the tables
                String stockTicker = "CREATE TABLE IF NOT EXISTS STOCK_TICKER (\n"
                        + "	symbol varchar(255) NOT NULL PRIMARY KEY UNIQUE,\n"
                        + "	ticker_name varchar(255) NOT NULL UNIQUE\n"
                        + ");";

                sqlStrings.add(stockTicker);

                String stockSource = "CREATE TABLE IF NOT EXISTS STOCK_SOURCE (\n"
                        + "	source_name varchar(255) NOT NULL PRIMARY KEY UNIQUE\n"
                        + ");";

                sqlStrings.add(stockSource);

                String stockSummary = "CREATE TABLE IF NOT EXISTS STOCK_SUMMARY (\n"
                        + "	source varchar(255) NOT NULL,\n"
                        + "	ticker_symbol varchar(255) NOT NULL,\n"
                        + "	ticker_name varchar(255) NOT NULL,\n"
                        + "	stock_record_date text NOT NULL,\n"
                        + "	prev_close_price real,\n"
                        + "	open_price real,\n"
                        + "	bid_price real,\n"
                        + "	ask_price real,\n"
                        + "	days_range_min real,\n"
                        + "	days_range_max real,\n"
                        + "	fifty_two_week_min real,\n"
                        + "	fifty_two_week_max real,\n"
                        + "	volume bigin,\n"
                        + "	avg_volume bigint,\n"
                        + "	market_cap real,\n"
                        + "	beta_coefficient real,\n"
                        + "	pe_ratio real,\n"
                        + "	eps real,\n"
                        + "	earning_date text,\n"
                        + "     dividend_yield real,\n"
                        + "     ex_dividend_date text,\n"
                        + "     one_year_target_est real,\n"
                        + "	PRIMARY KEY (source, ticker_symbol, ticker_name, stock_record_date),\n"
                        + "     CONSTRAINT fk_sources_column\n"
                        + "         FOREIGN KEY (source)\n"
                        + "         REFERENCES STOCK_SOURCE (source_name)\n"
                        + "      CONSTRAINT fk_stock_ticker_column\n"
                        + "          FOREIGN KEY (ticker_name)\n"
                        + "          REFERENCES STOCK_TICKER (ticker_name)\n"
                        + "      CONSTRAINT fk_stock_ticker_symbol_column\n"
                        + "          FOREIGN KEY (ticker_symbol)\n"
                        + "          REFERENCES STOCK_TICKER (symbol)\n"
                        + ");";

                sqlStrings.add(stockSummary);

                //StockHistorical string.
                String stockHistorical = "CREATE TABLE IF NOT EXISTS HISTORICAL (\n"
                        + "      source varchar(255) NOT NULL,\n"
                        + "      ticker_symbol varchar(255) NOT NULL,\n"
                        + "      ticker_name varchar(255) NOT NULL,\n"
                        + "      historical_date text NOT NULL,\n"
                        + "      open real,\n"
                        + "      high real,\n"
                        + "      low real,\n"
                        + "      close real,\n"
                        + "      adj_close real,\n"
                        + "      volume bigint,\n"
                        + "	 PRIMARY KEY (source, ticker_symbol, ticker_name, historical_date),\n"
                        + "      CONSTRAINT fk_sources_column\n"
                        + "         FOREIGN KEY (source)\n"
                        + "         REFERENCES STOCK_SOURCE (source_name)\n"
                        + "      CONSTRAINT fk_stock_ticker_column\n"
                        + "          FOREIGN KEY (ticker_name)\n"
                        + "          REFERENCES STOCK_TICKER (ticker_name)\n"
                        + "      CONSTRAINT fk_stock_ticker_symbol_column\n"
                        + "          FOREIGN KEY (ticker_symbol)\n"
                        + "          REFERENCES STOCK_TICKER (symbol)\n"
                        + ");";
                
                sqlStrings.add(stockHistorical);
                
                //LOGS string.
                String stockLogs = "CREATE TABLE IF NOT EXISTS LOGS (\n"
                        + "      log_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                        + "      source  varchar(255) NOT NULL,\n"
                        + "      start_date text NOT NULL,\n"
                        + "      end_date text,\n"
                        + "      status varchar(255),\n"
                        + "      log text,\n"
                        + "      CONSTRAINT fk_sources_column\n"
                        + "         FOREIGN KEY (source)\n"
                        + "         REFERENCES STOCK_SOURCE (source_name)\n"
                        + ");";
                
                sqlStrings.add(stockLogs);
                
                //Execute the SQL strings in the DB.
                logger.log(Level.INFO, "Creating database and DDL statements...");
                try {
                    connect();
                    stmt = conn.createStatement();
                    for (String str : sqlStrings) {
                        stmt.execute(str);
                    }
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                } finally {
                    disconnect();
                }
                insertAllStockSources();
                insertAllTickers();
          }
    }

    
    /**
     * Get database instance
     * @return 
     */
    public static StockDao getInstance() {
        if (instance == null) {
            instance = new StockDao();
        }
        return instance;
    }

    /**
     * Make database connection
     */
    public void connect() {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Disconnect from database
     */
    private void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
    }
    
    /**
     * Method to check if the database already exist
     * @return boolean
     */
    public boolean databaseAlreadyInitialized() {
        logger.log(Level.INFO, "Check database already initialized...");
        String tableName=null;
        try {
            connect();
            DatabaseMetaData meta = conn.getMetaData();
            rs = meta.getTables(null, null, "STOCK_TICKER", new String[] {"TABLE"});
            while (rs.next()) 
                tableName = rs.getString("TABLE_NAME");
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
        return tableName!=null;
    }


    /**
     * Insert stock_ticker data if the table is empty
     */
    private void insertAllTickers() {
        for (int i = 0; i <= Constants.stockSymbols.length-1; i++) 
            setStockTickerData(Constants.stockSymbols[i], Constants.stockNames[i]);
    }
    
    /**
     * Insert stock_source data if the table is empty
     */
    private void insertAllStockSources() {
        for(int cnt=0; cnt <= Constants.stockSourceNames.length-1; cnt++) {
            setStockSource(Constants.stockSourceNames[cnt]);
        }
    }
    
    /**
     * Insert Logging into Logs table
     */
    public void insertLog(StockLogs stockLog) {
        String sql = "INSERT INTO LOGS (source, start_date, end_date, status, log) VALUES (?, ?, ?, ?, ?);";
        try {
            connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stockLog.getSource());
            pstmt.setString(2, stockLog.getStart_date());
            pstmt.setString(3, stockLog.getEnd_date());
            pstmt.setString(4, stockLog.getStatus());
            pstmt.setString(5, stockLog.getLog());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
  
    }
    /**
     * Insert STOCK_TICKER data
     * @param stockSymbol
     * @param stockName 
     */
    public void setStockTickerData(String stockSymbol, String stockName) {
        logger.log(Level.INFO, "Insert STOCK_TICKER data...");
        
        String sql = "INSERT INTO STOCK_TICKER (symbol, ticker_name) VALUES (?, ?);";
        try {
            connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stockSymbol);
            pstmt.setString(2, stockName);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
    }

    /**
     * Load STOCK_TICKER data
     * @return 
     */
    public List<StockTicker> getAllstockTickers() {
        logger.log(Level.INFO, "Get all STOCK_TICKER data...");
        List<StockTicker> stockTickers = new ArrayList<>();
        String query = "SELECT symbol, ticker_name FROM STOCK_TICKER";
        try {
            connect();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            StockTicker stockticker = null;
            
            while (rs.next()) {
                stockticker = new StockTicker();
                stockticker.setName(rs.getString("ticker_name"));
                stockticker.setSymbol(rs.getString("symbol"));
                stockTickers.add(stockticker);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
        return stockTickers;
    }

    /**
     * Insert data into STOCK_SOURCE table
     * @param stockSource 
     */
    public void setStockSource(String stockSource) {
        logger.log(Level.INFO, "Insert STOCK_SOURCE data...");
        String sql = "INSERT INTO STOCK_SOURCE (source_name) VALUES (?);";
        try {
            connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stockSource);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
    }

    /**
     * Insert data into STOCK_DATE_MAP table
     * @param stockDateMap
     * @return 
     */
    public int insertStockDateMap(StockDateMap stockDateMap) {
        logger.log(Level.INFO, "Insert data into STOCK_DATE_MAP...");
        int last_inserted_id = -1;
        String sql = "INSERT INTO STOCK_DATE_MAP (STOCK_DATE,"
                + " TICKER_ID,"
                + " SOURCE_ID) VALUES (?, ?, ?);";
        if (stockDateMap.getTickerId() != -1 && stockDateMap.getSourceId() != -1) {
            try {
                connect();
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, stockDateMap.getDate());
                pstmt.setInt(2, (int)stockDateMap.getTickerId());
                pstmt.setInt(3, (int)stockDateMap.getSourceId());
                pstmt.executeUpdate();
                
                rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    last_inserted_id = rs.getInt(1);
                conn.commit();
                pstmt.close();
                rs.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, e.getMessage());
            } finally {
                disconnect();
            }
        }
        return last_inserted_id;
    }

    /**
     * Get stock ticker by symbol
     * @param symbol
     * @return 
     */
    public int getStockTickerBySymbol(String symbol) {
        logger.log(Level.INFO, "Get stock ticker id by symbol...");
        
        String symbolQuery = "SELECT TICKER_ID FROM STOCK_TICKER WHERE SYMBOL = ?";
        int tickerID = 0;
        try {
            connect();
            pstmt = conn.prepareStatement(symbolQuery);
            pstmt.setString(1, symbol);
            rs = pstmt.executeQuery();
            tickerID = rs.getInt("ticker_id");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    
                    if(rs != null)
                        rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
            }
                   
            disconnect();
        }
        return tickerID;
    }
    
        /**
     * Get stock source by name
     * @param source_name
     * @return source
     */
    public String getStockSourceByName(String source_name) {
        logger.log(Level.INFO, "Get stock sourceid by name...");
        String source = "";
        String symbolQuery = "SELECT source_name FROM STOCK_SOURCE WHERE source_name = ?";
        
        try {
            connect();
            pstmt = conn.prepareStatement(symbolQuery);
            pstmt.setString(1, source_name);
            rs = pstmt.executeQuery();
            source = rs.getString("source_name");
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    
                    if(rs != null)
                        rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
            }
            
            disconnect();
        }
        return source;
    }
    
    /**
     * Count number of records from stock date mapper
     * @return 
     */
    public int getStockDateMapCount() {
        logger.log(Level.INFO, "Get stock date map count...");
        
        String SQL = "SELECT COUNT(*) as CNT FROM STOCK_DATE_MAP";
        int recCount = 0;
        try {
            connect();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            recCount = rs.getInt("CNT");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    if(rs != null)
                        rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
            }
            disconnect();
        }
        return recCount;
    }
    
    /**
     * Count number of records from summary
     * @return 
     */
    public int getStockSummaryCount() {
        logger.log(Level.INFO, "Get stock summary count...");
        
        String SQL = "SELECT COUNT(*) as CNT FROM STOCK_SUMMARY";
        int recCount = 0;
        try {
            connect();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            recCount = rs.getInt("CNT");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    if(rs != null)
                        rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
            }
            disconnect();
        }
        return recCount;
    }
    
    /**
     * Count number of records from historical
     * @return 
     */
    public int getStockHistoricalCount() {
        logger.log(Level.INFO, "Get stock historical count...");
        
        String SQL = "SELECT COUNT(*) as CNT FROM STOCK_HISTORICAL";
        int recCount = 0;
        try {
            connect();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            recCount = rs.getInt("CNT");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    if(rs != null)
                        rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
            }
            disconnect();
        }
        return recCount;
    }
    
    /**
     * Count number of records from stock source
     * @return 
     */
    public int getStockSourceCount() {
        logger.log(Level.INFO, "Get stock source count...");
        
        String SQL = "SELECT COUNT(*) as CNT FROM STOCK_SOURCE";
        int recCount = 0;
        try {
            connect();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            recCount = rs.getInt("CNT");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    if(rs != null)
                        rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
            }
            disconnect();
        }
        return recCount;
    }
    
        /**
     * Count number of records from stock ticker
     * @return 
     */
    public int getStockTickerCount() {
        logger.log(Level.INFO, "Get stock ticker count...");
        
        String SQL = "SELECT COUNT(*) as CNT FROM STOCK_TICKER";
        int recCount = 0;
        try {
            connect();
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            recCount = rs.getInt("CNT");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    if(rs != null)
                        rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
            }
            disconnect();
        }
        return recCount;
    }
    
    /**
     * Get stock date map by date, tickerid and sourceid
     * @param date
     * @param tickerID
     * @param sourceID
     * @return 
     */
    private int getStockDateMapId(String date, int tickerID, int sourceID) {
       logger.log(Level.INFO, "Get stock datemap id...");
       
        String stockDtMapId = "SELECT STOCK_DT_MAP_ID FROM STOCK_DATE_MAP WHERE STOCK_DATE = ? AND TICKER_ID = ? AND SOURCE_ID = ?";
        int stockDateMapID = 0;

        try {
            connect();
            pstmt = conn.prepareStatement(stockDtMapId);
            pstmt.setString(1, date);
            pstmt.setInt(2, tickerID);
            pstmt.setInt(3, sourceID);
            rs = pstmt.executeQuery();
            stockDateMapID = rs.getInt("stock_dt_map_id");
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    
                    if(rs != null)
                        rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
            }
            disconnect();
        }
        return stockDateMapID;
    }

    /**
     * insert data into STOCK_SUMMARY table
     * @param stockSummary 
     */
    public void insertStockSummaryData(StockSummary stockSummary) throws Exception {
        logger.log(Level.INFO, "Insert data into STOCK_SUMMARY...");
        String sql = "INSERT INTO STOCK_SUMMARY (source, "
                + " ticker_symbol,"
                + " ticker_name,"
                + " stock_record_date,"
                + " prev_close_price,"
                + " open_price,"
                + " bid_price,"
                + " ask_price,"
                + " days_range_min,"
                + " days_range_max,"
                + " fifty_two_week_min,"
                + " fifty_two_week_max,"
                + " volume,"
                + " avg_volume,"
                + " market_cap,"
                + " beta_coefficient,"
                + " pe_ratio,"
                + " eps,"
                + " earning_date,"
                + " dividend_yield,"
                + " ex_dividend_date,"
                + " one_year_target_est) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            connect();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stockSummary.getSource());
            pstmt.setString(2, stockSummary.getTicker_symbol());
            pstmt.setString(3, stockSummary.getTicker_name());
            pstmt.setString(4, stockSummary.getStock_record_date());
            pstmt.setBigDecimal(5, stockSummary.getPrevClosePrice());
            pstmt.setBigDecimal(6, stockSummary.getOpenPrice());
            pstmt.setBigDecimal(7, stockSummary.getBidPrice());
            pstmt.setBigDecimal(8, stockSummary.getAskPrice());
            pstmt.setBigDecimal(9, stockSummary.getDaysRangeMin());
            pstmt.setBigDecimal(10, stockSummary.getDaysRangeMax());
            pstmt.setBigDecimal(11, stockSummary.getFiftyTwoWeeksMin());
            pstmt.setBigDecimal(12, stockSummary.getFiftyTwoWeeksMax());
            pstmt.setLong(13, stockSummary.getVolume());
            pstmt.setLong(14, stockSummary.getAvgVolume());
            pstmt.setBigDecimal(15, stockSummary.getMarketCap());
            pstmt.setBigDecimal(16, stockSummary.getBetaCoefficient());
            pstmt.setBigDecimal(17, stockSummary.getPeRatio());
            pstmt.setBigDecimal(18, stockSummary.getEps());
            pstmt.setString(19, stockSummary.getEarningDate());
            pstmt.setBigDecimal(20, stockSummary.getDividentYield());
            pstmt.setString(21, stockSummary.getExDividentDate());
            pstmt.setBigDecimal(22, stockSummary.getOneYearTargetEst());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        } finally {
            disconnect();
        }
    }

    
    /**
     * insert data into STOCK_HISTORICAL table
     * @param stockHistorical 
     */
    public void insertStockHistoricalData(StockHistorical stockHistorical) throws Exception {
        logger.log(Level.INFO, "Insert data into STOCK_HISTORICAL...");
        String sql = "INSERT INTO HISTORICAL (source,"
                + " ticker_symbol,"
                + " ticker_name,"
                + " historical_date,"
                + " open,"
                + " high,"
                + " low,"
                + " close,"
                + " adj_close,"
                + " volume) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            connect();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stockHistorical.getSource());
            pstmt.setString(2, stockHistorical.getTicker_symbol());
            pstmt.setString(3, stockHistorical.getTicker_name());
            pstmt.setString(4, stockHistorical.getHistorical_date());
            pstmt.setBigDecimal(5, stockHistorical.getOpen());
            pstmt.setBigDecimal(6, stockHistorical.getHigh());
            pstmt.setBigDecimal(7, stockHistorical.getLow());
            pstmt.setBigDecimal(8, stockHistorical.getClose());
            pstmt.setBigDecimal(9, stockHistorical.getAdjClose());
            pstmt.setLong(10, stockHistorical.getVolume());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw e;
        } finally {
            disconnect();
        }
    }
    
    /**
     * Get Stock summary data from view
     */
    public int getAvgStockSummaryView() {
        logger.log(Level.INFO, "Get STOCK_SUMMARY_VIEW data...");
        int totalRecords = 0;
        
        String sql = "SELECT * FROM STOCK_SUMMARY_VIEW;";
        connect();
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            logger.log(Level.INFO, "getAvgStockSummaryView results...");
            while (rs.next()) {
                logger.log(Level.INFO, rs.getString("STK_DATE") + "\t"
                        + rs.getString("STOCK") + "\t"
                        + rs.getBigDecimal("AVG_PRICE"));
                ++totalRecords;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
        return totalRecords;
    }
    
    /**
     * Get Stock historical data from view
     */
    public int getStockHistoricalView() {
        logger.log(Level.INFO, "Get STOCK_HISTORICAL_VIEW data...");
        int totalRecords = 0;
        
        String sql = "SELECT * FROM STOCK_HISTORICAL_VIEW;";
        connect();
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            logger.log(Level.INFO, "getStockHistoricalView results...");
            while (rs.next()) {
                logger.log(Level.INFO, rs.getString("STK_DATE") + "\t"
                        + rs.getString("STOCK") + "\t"
                        + rs.getBigDecimal("AVG_PRICE"));
                ++totalRecords;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
        return totalRecords;
    }

    /**
     * Delete data
     */
    public void deleteAll() {
        deleteFromStockSource();
        deleteFromStockTicker();
        deleteFromStockDateMap();
        deleteFromStockSummary();
    }
    
    /**
     * Delete data from stock_source
     */
    void deleteFromStockSource() {
        logger.log(Level.INFO, "Delete data from STOCK_SOURCE...");
        
        String sql = "DELETE FROM " + Constants.TABLE_STOCK_SOURCE;
        try {
            connect();
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Delete data from stock_ticker
     */
    void deleteFromStockTicker() {
        logger.log(Level.INFO, "Delete data from STOCK_TICKER...");
        
        String sql = "DELETE FROM " + Constants.TABLE_STOCK_TICKER;
        try {
            connect();
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Delete data from stock_ticker
     */
    void deleteFromStockDateMap() {
        logger.log(Level.INFO, "Delete data from STOCK_DATE_MAP...");
        
        String sql = "DELETE FROM " + Constants.TABLE_STOCK_DATE_MAP;
        try {
            connect();
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Delete data from stock_summary
     */
    void deleteFromStockSummary() {
        logger.log(Level.INFO, "Delete data from STOCK_SUMMARY...");
        
        String sql = "DELETE FROM " + Constants.TABLE_STOCK_SUMMARY;
        try {
            connect();
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Delete data from stock_historical
     */
    void deleteFromStockHiastorical() {
        logger.log(Level.INFO, "Delete data from STOCK_HISTORICAL...");
        
        String sql = "DELETE FROM " + Constants.TABLE_STOCK_HISTORICAL;
        try {
            connect();
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Get latest stock date
     * @return latestDate
     */
    public Date getLatestScrappedDate(String source) {
       logger.log(Level.INFO, "Get latest scrapped date...");
       
        Date latestDate = null;
        String query = "SELECT stock_record_date FROM STOCK_SUMMARY WHERE STOCK_SUMMARY.source == '"+source+"' ORDER BY DATE(stock_record_date) DESC LIMIT 1";
        try {
            connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            String stockDate = rs.getString("stock_record_date");
            //Date inputDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-04-17");
            latestDate = stockDate!=null?new SimpleDateFormat("yyyy-MM-dd").parse(stockDate):null;
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(StockDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {  
            disconnect();
        }
        return latestDate;
    }
    
    public Date getLatestHistoricalScrappedDate() {
       logger.log(Level.INFO, "Get latest Historical scrapped date...");
       
        Date latestDate = null;
        String query = "SELECT historical_date FROM HISTORICAL ORDER BY DATE(historical_date) DESC LIMIT 1";
        try {
            connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            String stockDate = rs.getString("historical_date");
            //Date inputDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-04-17");
            latestDate = stockDate!=null?new SimpleDateFormat("yyyy-MM-dd").parse(stockDate):null;
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(StockDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {  
            disconnect();
        }
        return latestDate;
    }
}
