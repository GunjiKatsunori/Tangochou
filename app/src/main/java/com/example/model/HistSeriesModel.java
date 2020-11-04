package com.example.model;

public class HistSeriesModel {
    private int id;
    private String timestamp;
    private int series_id;
    private double rate;

    public HistSeriesModel(int id, String timestamp, int series_id, double rate) {
        this.id = id;
        this.timestamp = timestamp;
        this.series_id = series_id;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getSeriesId() {
        return series_id;
    }

    public double getRate() {
        return rate;
    }
}