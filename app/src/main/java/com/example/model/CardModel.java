package com.example.model;

public class CardModel {
    private int id;
    private String head;
    private String tail;
    private String directory;
    private int series_id;

    public CardModel(int id, String head, String tail, String directory, int series_id) {
        this.id = id;
        this.head = head;
        this.tail = tail;
        this.directory = directory;
        this.series_id = series_id;
    }

    public int getId() {
        return id;
    }

    public String getHead() {
        return head;
    }

    public String getTail() {
        return tail;
    }

    public String getDirectory() {
        return directory;
    }

    public int getSeriesId() {
        return series_id;
    }
}
