package com.example.model;

public class HistSeriesModel {
    private Integer id;
    private String timestamp;
    private Integer series_id;
    private Double rate;

    public HistSeriesModel(Integer id, String timestamp, Integer series_id, Double rate) {
        this.id = id;
        this.timestamp = timestamp;
        this.series_id = series_id;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getSeriesId() {
        return series_id;
    }

    public Double getRate() {
        return rate;
    }
}