package com.example.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.tangochou.DBOpenHelper;

/**
 * Cardテーブルのデータを格納するモデルクラス
 * @author 郡司克徳
 * @version 1.0.0
 */
public class CardModel implements IFile {
    private Integer id;
    private String head;
    private String tail;
    private String directory;
    private Integer series_id;

    /**
     * コンストラクタ
     * @param id
     * @param head
     * @param tail
     * @param directory
     * @param series_id
     */
    public CardModel(Integer id, String head, String tail, String directory, Integer series_id) {
        this.id = id;
        this.head = head;
        this.tail = tail;
        this.directory = directory;
        this.series_id = series_id;
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
     * Cardの表面のテキストを取得する
     * @return
     */
    public String getHead() {
        return head;
    }

    @Override
    public String getName(){return getHead();}

    /**
     * Cardの裏面のテキストを取得する
     * @return
     */
    public String getTail() {
        return tail;
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
    public Integer getSeriesId() {
        return series_id;
    }

    @Override
    public Integer getParentId() {return getSeriesId();}

    public void update(DBOpenHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();

        // 更新データをcontentValueとして用意する
        ContentValues cv = new ContentValues();
        cv.put("head", head);
        cv.put("tail", tail);

        String whereClause = "id = " + "'" + id + "'";
        db.update("card", cv, whereClause, null);
    }
}
