-- INSERT a source into the sources table
INSERT INTO sources (source_name) values('MarketWatch');

-- INSERT a ticker into the stock_ticker table
INSERT INTO stock_ticker (symbol,ticker_name) values('AAPL','Apple Inc.');

-- INSERT a stock summary into the stock_summary table
INSERT INTO stock_summary (source,ticker,stock_record_date,prev_close_price, open_price, bid_price, ask_price, days_range_min, days_range_max, fifty_two_week_min, fifty_two_week_max, volume, avg_volume, market_cap, beta_coefficient, pe_ratio, eps, earning_date, dividend_yield, ex_dividend_date, one_year_target_est) values('MarketWatch','Apple Inc.','2019-10-12',134.1,156.14,187.8,154.0,169.57,73.0,82.94,161.25,28372162,22736251,1.072,0.57,28.4,4.36,'Oct 27, 2019',1.08,'2019-08-17',127.46);