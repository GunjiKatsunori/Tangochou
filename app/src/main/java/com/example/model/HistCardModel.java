package com.example.model;

public class HistCardModel {
    private String timestamp;
    private String card_id;
    private Integer hist_series_id;
    private Integer correct;

    public HistCardModel(String timestamp, String card_id, Integer hist_series_id, Integer correct) {
        this.timestamp = timestamp;
        this.card_id = card_id;
        this.hist_series_id = hist_series_id;
        this.correct = correct;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCardId() {
        return card_id;
    }

    public Integer getHistSeriesId() {
        return hist_series_id;
    }

    public Integer getCorrect() {
        return correct;
    }
}
