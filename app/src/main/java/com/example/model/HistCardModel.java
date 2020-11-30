package com.example.model;

import android.database.sqlite.SQLiteDatabase;

import com.example.tangochou.DBOpenHelper;

/**
 * hist_cardテーブルのレコードをモデルインスタンスとして扱う
 * モデルインスタンスとデータベース間のCRUD処理を扱う
 * @author 郡司克徳
 * @version 1.0.0
 */
public class HistCardModel {
    private String timestamp;
    private Integer card_id;
    private Integer hist_series_id;
    private Integer correct;

    /**
     * コンストラクタ
     * @param timestamp
     * @param card_id
     * @param hist_series_id
     * @param correct
     */
    public HistCardModel(String timestamp, Integer card_id, Integer hist_series_id, Integer correct) {
        this.timestamp = timestamp;
        this.card_id = card_id;
        this.hist_series_id = hist_series_id;
        this.correct = correct;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getCardId() {
        return card_id;
    }

    public Integer getHistSeriesId() {
        return hist_series_id;
    }

    public Integer getCorrect() {
        return correct;
    }

    /**
     * インスタンスをDBに登録する
     * @param helper
     */
    public void insert(DBOpenHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String statement = "insert into hist_card(timestamp, card_id, hist_series_id, correct) values(" +
                "'" + timestamp      + "'" + ", " +
                "'" + card_id        + "'" + ", " +
                "'" + hist_series_id + "'" + ", " +
                "'" + correct        + "'"        + ")";

        db.execSQL(statement);
    }
}
