package com.example.admin.wellrounder;

/**
 * Created by admin on 10/25/2017.
 */


public class TestStockList {

    public String symbol;
    public String stockClose;
    public String stockPrice;
    public String stockLow;
    public String stockHigh;

    //, String stockName, String stockPrice, String stockLow, String stockHigh

    public TestStockList(String symbol, String stockClose) {
        this.symbol = symbol;
        this.stockClose = stockClose;
        //this.stockTicker = stockTicker;
       // this.stockName = stockName;
       // this.stockPrice = stockPrice;
       // this.stockLow = stockLow;
       // this.stockHigh = stockHigh;
    }

    public String getSymbol() {
        return symbol;
    }

//    public String getStockTicker() {
//        return stockTicker;
//    }

    public String getStockClose() {
        return stockClose;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public String getStockLow() {
        return stockLow;
    }

    public String getStockHigh() {
        return stockHigh;
    }



}
