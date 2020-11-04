package com.example.model;

public class HistCardModel {
    private String timestamp;
    private String card_id;
    private int hist_series_id;
    private int correct;

    public HistCardModel(String timestamp, String card_id, int hist_series_id, int correct) {
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

    public int getHistSeriesId() {
        return hist_series_id;
    }

    public int getCorrect() {
        return correct;
    }
}
