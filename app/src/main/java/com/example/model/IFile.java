package com.example.model;

/**
 * Folder, Series, Card の名前やディレクトリ構造を扱う
 * @author 郡司克徳
 * @version 1.0.0
 */
public interface IFile {
    /**
     * IDを取得する
     * @return ID
     */
    Integer getId();

    /**
     * 名前を取得する
     * Cardの場合は表面のテキストを取得する
     * @return Folder, Sereisの名前及び、Cardの表
     */
    String getName();

    /**
     * ディレクトリを取得する
     * @return ディレクトリ
     */
    String getDirectory();

    /**
     * 所属するフォルダまたは学習セットのIDを取得する
     * @return 所属するフォルダまたは学習セットのID
     */
    Integer getParentId();
}
