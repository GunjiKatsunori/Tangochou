package com.example.model;

public class SeriesModel {
    private int id;
    private String path;
    private String name;
    private String directory;
    private int parent_id;

    public SeriesModel(int id, String path, String name, String directory, int parent_id) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.directory = directory;
        this. parent_id = parent_id;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getDirectory() {
        return directory;
    }

    public int getParentId() {
        return parent_id;
    }
}
