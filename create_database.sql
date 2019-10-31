-- CREATES a source table for storing all sources that are scrapped
CREATE TABLE "STOCK_SOURCE" (
  "source_name" varchar(255) NOT NULL PRIMARY KEY UNIQUE
);

-- CREATES a stock ticker table for storing the symbol and name for each stock that is scrapped from various sources
CREATE TABLE "STOCK_TICKER" (
 "symbol" varchar(255) NOT NULL PRIMARY KEY UNIQUE,
  "ticker_name" varchar(255) NOT NULL UNIQUE
);

-- CREATES a stock summary table for holding all the records of the data being scrapped from all the various tickers and sources
CREATE TABLE "STOCK_SUMMARY" (
  "source" varchar(255) NOT NULL,
  "ticker_symbol" varchar(255) NOT NULL,
  "ticker_name" varchar(255) NOT NULL,
  "stock_record_date" text NOT NULL,
  "prev_close_price" real,
  "open_price" real,
  "bid_price" real,
  "ask_price" real,
  "days_range_min" real,
  "days_range_max" real,
  "fifty_two_week_min" real,
  "fifty_two_week_max" real,
  "volume" bigint,
  "avg_volume" bigint,
  "market_cap" real,
  "beta_coefficient" real,
  "pe_ratio" real,
  "eps" real,
  "earning_date" text,
  "dividend_yield" real,
  "ex_dividend_date" text,
  "one_year_target_est" real,
  PRIMARY KEY (source, ticker_symbol, ticker_name, stock_record_date),
  CONSTRAINT fk_sources_column
    FOREIGN KEY (source)
    REFERENCES STOCK_SOURCE (source_name)
  CONSTRAINT fk_stock_ticker_column
    FOREIGN KEY (ticker_name)
    REFERENCES STOCK_TICKER (ticker_name)
  CONSTRAINT fk_stock_ticker_symbol_column
    FOREIGN KEY (ticker_symbol)
    REFERENCES STOCK_TICKER (symbol)
);

CREATE TABLE "HISTORICAL" (
  "source" varchar(255) NOT NULL,
  "ticker_symbol" varchar(255) NOT NULL,
  "ticker_name" varchar(255) NOT NULL,
  "historical_date" text NOT NULL,
  "open" real,
  "high" real,
  "low" real,
  "close" real,
  "adj_close" real,
  "volume" bigint,
  PRIMARY KEY (source, ticker_symbol, ticker_name, historical_date),
  CONSTRAINT fk_sources_column
    FOREIGN KEY (source)
    REFERENCES STOCK_SOURCE (source_name)
  CONSTRAINT fk_stock_ticker_column
    FOREIGN KEY (ticker_name)
    REFERENCES STOCK_TICKER (ticker_name)
  CONSTRAINT fk_stock_ticker_symbol_column
    FOREIGN KEY (ticker_symbol)
    REFERENCES STOCK_TICKER (symbol)
);

CREATE TABLE "LOGS" (
  "log_id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "source"  varchar(255) NOT NULL,
  "start_date" text NOT NULL,
  "end_date" text,
  "status" varchar(255),
  "log" text,
  CONSTRAINT fk_sources_column
    FOREIGN KEY (source)
    REFERENCES STOCK_SOURCE (source_name)
);