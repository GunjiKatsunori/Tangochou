package com.example.model;

/**
 * Folderテーブルのデータを格納するモデルクラス
 * @author 郡司克徳
 * @version 1.0.0
 */
public class FolderModel implements IFile {
    /**
     * Folderテーブルのカラム
     */
    private Integer id;
    private String path;
    private String name;
    private String directory;
    private Integer parent_id;

    /**
     * コンストラクタ
     * @param id
     * @param path
     * @param name
     * @param directory
     * @param parent_id
     */
    public FolderModel(Integer id, String path, String name, String directory, Integer parent_id) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.directory = directory;
        this. parent_id = parent_id;
    }

    /**
     * IDを取得する
     * @return ID
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * パスを取得する
     * @return パス
     */
    public String getPath() {
        return path;
    }

    /**
     * フォルダ名を取得する
     * @return フォルダ名
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 所属するディレクトリを取得する
     * @return 所属するディレクトリ
     */
    @Override
    public String getDirectory() {
        return directory;
    }

    /**
     * 所属するフォルダのIDを取得する
     * @return　所属するフォルダのID
     */
    @Override
    public Integer getParentId() {
        return parent_id;
    }

}
