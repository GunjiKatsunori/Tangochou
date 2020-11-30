package com.example.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tangochou.DBOpenHelper;

/**
 * hist_seriesテーブルのレコードをモデルインスタンスとして扱う
 * モデルインスタンスとデータベース間のCRUD処理を扱う
 * @author 郡司克徳
 * @version 1.0.0
 */
public class HistSeriesModel {
    private static final String TABLE_NAME = "hist_series";

    private Integer id;
    private String timestamp;
    private Integer series_id;
    private Integer correct;
    private Integer incorrect;

    /**
     * コンストラクタ
     * データからモデルインスタンスを生成する
     * @param id
     * @param timestamp
     * @param series_id
     * @param correct
     * @param incorrect
     */
    public HistSeriesModel(Integer id, String timestamp, Integer series_id, Integer correct, Integer incorrect) {
        this.id = id;
        this.timestamp = timestamp;
        this.series_id = series_id;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    /**
     * コンストラクタ
     * SQLiteにアクセスしてモデルインスタンスを生成する
     * @param helper
     * @param id
     */
    public HistSeriesModel(DBOpenHelper helper, Integer id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM hist_series WHERE id=?;", new String[] {String.valueOf(id)} );

        // 存在する場合にメンバ変数に値を入れる
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            this.id = cursor.getInt(0);
            this.timestamp = cursor.getString(1);
            this.series_id = cursor.getInt(2);
            this.correct = cursor.getInt(3);
            this.incorrect = cursor.getInt(4);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getSeriesId() {
        return series_id;
    }

    public Integer getCorrect() {return correct;}

    public Integer getIncorrect() {return incorrect;}

    public double getRate() {
        return (double)correct/(correct+incorrect);
    }

    /**
     * インスタンスをDBに登録する
     * @param helper
     */
    public void insert(DBOpenHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String statement = "insert into hist_series(timestamp, series_id) values(" +
                "'" + timestamp + "'" + ", " +
                "'" + series_id + "'"        + ")";

        db.execSQL(statement);
    }

    /**
     * 最後に登録したレコードを呼び出す
     * @param helper
     */
    public static HistSeriesModel lastInsertedRow(DBOpenHelper helper) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"id", "timestamp", "series_id", "correct", "incorrect"},
                "id = last_insert_rowid()",
                null, null, null, null
        );

        HistSeriesModel histSeries = null;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            histSeries = new HistSeriesModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            );
        }

        return histSeries;
    }

    /**
     * 正答率のデータを更新する
     * @param helper
     */
    public void update(DBOpenHelper helper) {
        // 更新用データ作成
        ContentValues cv = new ContentValues();
        cv.put("correct", correct);
        cv.put("incorrect", incorrect);

        // DB登録
        SQLiteDatabase db = helper.getReadableDatabase();
        db.update(TABLE_NAME, cv, "id = " +  id, null);
    }

    /**
     * hist_seriesのレコードを削除する
     */
    public void delete(DBOpenHelper helper) {
        SQLiteDatabase db = helper.getReadableDatabase();
        db.delete(TABLE_NAME, "id = " +  id, null);
    }
}