package com.example.tangochou;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBの定義やオープンを行う
 * @author 郡司克徳
 * @version 1.0.0
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tangochou.db";
    // テーブル名
    public static final String TABLE_NAME_FOLDER = "folder";
    public static final String TABLE_NAME_SERIES = "series";
    public static final String TABLE_NAME_CARD = "card";
    public static final String TABLE_NAME_HISTORY_CARD = "hist_card";
    public static final String TABLE_NAME_HISTORY_SERIES = "hist_series";
    // テーブル定義
    private static String SQL_CREATE_TABLE_folder = "CREATE TABLE " + TABLE_NAME_FOLDER + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "path VARCHAR(100) NOT NULL UNIQUE, " +
            "name VARCHAR(100) NOT NULL, " +
            "directory VARCHAR(100), " +
            "parent_id INTEGER, " +
            "FOREIGN KEY (parent_id) REFERENCES folder(id) ON DELETE CASCADE" +
            ")";
    private static String SQL_CREATE_TABLE_series = "CREATE TABLE " + TABLE_NAME_SERIES + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "path VARCHAR(100) NOT NULL UNIQUE, " +
            "name VARCHAR(100) NOT NULL, " +
            "directory VARCHAR(100) NOT NULL, " +
            "parent_id INTEGER NOT NULL, " +
            "FOREIGN KEY (parent_id) REFERENCES folder(id) ON DELETE CASCADE" +
            ")";
    private static String SQL_CREATE_TABLE_card = "CREATE TABLE " + TABLE_NAME_CARD + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "head VARCHAR(100) NOT NULL, " +
            "tail VARCHAR(100), " +
            "directory VARCHAR(100) NOT NULL, " +
            "parent_id INTEGER NOT NULL, " +
            "FOREIGN KEY (parent_id) REFERENCES series(id) ON DELETE CASCADE" +
            ")";
    private static String SQL_CREATE_TABLE_hist_card = "CREATE TABLE " + TABLE_NAME_HISTORY_CARD + " (" +
            "timestamp VARCHAR(19) NOT NULL, " +
            "card_id VARCHAR(100) NOT NULL, " +
            "hist_series_id VARCHAR(100) NOT NULL, " +
            "correct INTEGER NOT NULL DEFAULT 0 check(correct = -1 or correct = 1), " +
            "FOREIGN KEY (card_id) REFERENCES card(id) ON DELETE CASCADE, " +
            "FOREIGN KEY (hist_series_id) REFERENCES hist_series(id) ON DELETE CASCADE" +
            ")";
    private static String SQL_CREATE_TABLE_hist_series = "CREATE TABLE " + TABLE_NAME_HISTORY_SERIES + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "timestamp VARCHAR(19) NOT NULL, " +
            "series_id INTEGER NOT NULL, " +
            "correct INTEGER DEFAULT 0, " +
            "incorrect INTEGER DEFAULT 0, " +
            "FOREIGN KEY (series_id) REFERENCES series(id) ON DELETE CASCADE" +
            ")";
    // テーブル削除
    private static String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * DBとテーブルを作成する
     * @param db DB名
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_folder);
        db.execSQL(SQL_CREATE_TABLE_series);
        db.execSQL(SQL_CREATE_TABLE_card);
        db.execSQL(SQL_CREATE_TABLE_hist_card);
        db.execSQL(SQL_CREATE_TABLE_hist_series);

        /* ダミーデータを挿入 */
        String SQL_DUMMY = "insert into folder(path, name, parent_id) values('nameAAA', 'nameAAA', 0), ('nameBBB', 'nameBBB', 0), ('nameCCC', 'nameCCC', 0), ('nameDDD', 'nameDDD', 0)";
        String SQL_DUMMY2 = "insert into folder(path, name, directory, parent_id) values('nameAAA/aaa', 'aaa', 'nameAAA', 1), ('nameAAA/bbb', 'bbb', 'nameAAA', 1)";
        String SQL_DUMMY3 = "insert into series(path, name, directory, parent_id) values('nameAAA/aaa/series1', 'series1', 'nameAAA/aaa', 1), ('nameAAA/aaa/series2', 'series2', 'nameAAA/aaa', 1)";
        String SQL_DUMMY4 = "insert into card(head, tail, directory, parent_id) values('text text1', 'text text2', 'nameAAA/aaa/series1', 1), ('text text2', 'text text3', 'nameAAA/aaa/series1', 1)";

        db.execSQL(SQL_DUMMY);
        db.execSQL(SQL_DUMMY2);
        db.execSQL(SQL_DUMMY3);
        db.execSQL(SQL_DUMMY4);

        /* ダミーデータここまで */
    }


    /**
     * バージョン更新時の動作
     * @param db DB名
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES + TABLE_NAME_FOLDER);
        db.execSQL(SQL_DELETE_ENTRIES + TABLE_NAME_SERIES);
        db.execSQL(SQL_DELETE_ENTRIES + TABLE_NAME_CARD);
        db.execSQL(SQL_DELETE_ENTRIES + TABLE_NAME_HISTORY_CARD);
        db.execSQL(SQL_DELETE_ENTRIES + TABLE_NAME_HISTORY_SERIES);
        onCreate(db);
    }


    /**
     * バージョンダウングレード時の動作
     * @param db DB名
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, newVersion, oldVersion);
    }
}
