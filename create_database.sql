-- CREATES a source table for storing all sources that are scrapped
CREATE TABLE "sources" (
  "source_name" varchar(255) NOT NULL UNIQUE
);

-- CREATES a stock ticker table for storing the symbol and name for each stock that is scrapped from various sources
CREATE TABLE "stock_ticker" (
  "ticker_id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
  "symbol" varchar(255),
  "ticker_name" varchar(255) NOT NULL UNIQUE
);

-- CREATES a stock summary table for holding all the records of the data being scrapped from all the various tickers and sources
CREATE TABLE "stock_summary" (
  "source" varchar(255) NOT NULL,
  "ticker" varchar(255) NOT NULL,
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
  PRIMARY KEY (source, ticker, stock_record_date),
  CONSTRAINT fk_sources_column
    FOREIGN KEY (source)
    REFERENCES sources (source_name)
  CONSTRAINT fk_stock_ticker_column
    FOREIGN KEY (ticker)
    REFERENCES stock_ticker (ticker_name)
);