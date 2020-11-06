package com.example.model;

public class FolderModel {
    // Folderのカラム
    private Integer id;
    private String path;
    private String name;
    private String directory;
    private Integer parent_id;

    public FolderModel(Integer id, String path, String name, String directory, Integer parent_id) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.directory = directory;
        this. parent_id = parent_id;
    }

    public Integer getId() {
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

    public Integer getParentId() {
        return parent_id;
    }

}
