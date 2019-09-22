StockReporter will be able to scrape financial data from websites such as Yahoo, Investopedia and store that data, and also conduct data analysis on the data for research or to aid in the decision-making process regarding investing.

Project setup
-------------
Step 1: On local machine, create a folder (e.g. mkdir stockproject).

Step 2: Switch to the new folder and perform "git init".

Step 3: On local, run "git init" and followed by git clone https://github.com/kennylg2/swen_sa_tool.git.

Note: If you using the project zip, ignore the above steps.

Step 4: Add project dependencies. 

  (Netbeans): Right click the project -> properties -> libraries -> Add Jar/Folder and select "jarfiles" folder. 

  (Eclipse): Right click the project -> properties -> Java Build Path -> Add JARs and select the project name and "jarfiles"                folder.

Databases (Auto)
----------------
The application will generate "stockreporter.prod" database with tables, indexes, and views when the application is executed for the first time.

Database (Manual)
-----------------
Use "master_data.sql" to insert initial data into STOCK_TICKER and STOCK_SOURCE tables.

Use "create_tbl_vw_master_summary.sql" to create master, summary tables, indexes, and summary view.

Use "create tbl_vw_historical.sql" to create stock historical and summary table/view.

Running application
-------------------
src/stockreporter/StockReporter is the main class to run the application. The application does not ship with database but "stockreporter.prod" will be auto-created (tables, initial master data for STOCK_SOURCE and STOCK_TICKER, indexes, and views) when the application is executed for the first time. The application scraps the stock summary and historical (Investopedia does not provide the data anymore) data based on STOCK_SOURCE and STOCK_TICKER data. When you run test cases, please drop the database to have clean data.

Note:
----
As of Apr 26, 2019 the application scraps the data based on STOCK_SOURCE. The application may not function if there is a change in the source data format. e.g. The website may be redesigned or change in html format. Recently, Investopedia stopped offering historical data.

Running Test
------------
The application has three test suites under test/* folder for dao, models, and scraper testing. The system will insert some sample data into the default database for StockReporterTestSuite and ScrappersTestSuite (auto-creates if the database does not exist) and truncates the data at the end of the test. Once all testsuites are executed, it is highly recommended to drop the database before running main application as it trunctates data including STOCK_SOURCE and STOCK_TICKER .
