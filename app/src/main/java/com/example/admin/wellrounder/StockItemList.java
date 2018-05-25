package com.example.admin.wellrounder;

import java.util.HashMap;

/**
 * Created by admin on 12/24/2017.
 */

public class StockItemList {
    public String ticker;
    public String date;
    public String open;
    public String high;
    public String low;
    public String close;
    public String adjustedClose;
    public String volume;
    public String divAmount;
    public String splitCoe;
    public HashMap hashMap;

    public StockItemList(HashMap<String, String> hashMap, String ticker, String date, String open, String high, String low, String close, String adjustedClose, String volume, String divAmount, String splitCoe) {
        this.hashMap = hashMap;
        this.ticker = ticker;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
        this.divAmount = divAmount;
        this.splitCoe = splitCoe;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public String getTicker() {
        return ticker;
    }

    public String getDate() {
        return date;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getClose() {
        return close;
    }

    public String getAdjustedClose() {
        return adjustedClose;
    }

    public String getVolume() {
        return volume;
    }

    public String getDivAmount() {
        return divAmount;
    }

    public String getSplitCoe() {
        return splitCoe;
    }
}
