package com.example.model;

public class CardModel {
    private Integer id;
    private String head;
    private String tail;
    private String directory;
    private Integer series_id;

    public CardModel(Integer id, String head, String tail, String directory, Integer series_id) {
        this.id = id;
        this.head = head;
        this.tail = tail;
        this.directory = directory;
        this.series_id = series_id;
    }

    public Integer getId() {
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

    public Integer getSeriesId() {
        return series_id;
    }
}
