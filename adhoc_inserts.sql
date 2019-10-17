-- INSERT a source into the sources table
INSERT INTO STOCK_SOURCE (source_name) values('MarketWatch');

-- INSERT a ticker into the stock_ticker table
INSERT INTO STOCK_TICKER (symbol,ticker_name) values('AAPL','Apple Inc.');

-- INSERT a stock summary into the stock_summary table
INSERT INTO STOCK_SUMMARY (source,ticker_symbol,ticker_name,stock_record_date,prev_close_price, open_price, bid_price, ask_price, days_range_min, days_range_max, fifty_two_week_min, fifty_two_week_max, volume, avg_volume, market_cap, beta_coefficient, pe_ratio, eps, earning_date, dividend_yield, ex_dividend_date, one_year_target_est) values('Yahoo','AAPL','Apple Inc.','2019-10-12',123.5,126.05,127.5,123.5,129.00,73.0,83.25,122.51,22372062,22236355,1.025,0.28,22.5,4.25,'Oct 22, 2019',1.05,'2019-08-12',123.45);
