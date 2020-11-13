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
    public static final int DATABASE_VERSION = 2;
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
            "parent_id INTEGER" +
            ")";
    private static String SQL_CREATE_TABLE_series = "CREATE TABLE " + TABLE_NAME_SERIES + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "path VARCHAR(100) NOT NULL UNIQUE, " +
            "name VARCHAR(100) NOT NULL, " +
            "directory VARCHAR(100) NOT NULL, " +
            "parent_id INTEGER NOT NULL" +
            ")";
    private static String SQL_CREATE_TABLE_card = "CREATE TABLE " + TABLE_NAME_CARD + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "head VARCHAR(100) NOT NULL, " +
            "tail VARCHAR(100), " +
            "directory VARCHAR(100) NOT NULL, " +
            "parent_id INTEGER NOT NULL" +
            ")";
    private static String SQL_CREATE_TABLE_hist_card = "CREATE TABLE " + TABLE_NAME_HISTORY_CARD + " (" +
            "timestamp VARCHAR(19) NOT NULL, " +
            "card_id VARCHAR(100) NOT NULL, " +
            "hist_series_id VARCHAR(100) NOT NULL, " +
            "correct INTEGER NOT NULL DEFAULT 0 check(correct = 0 or correct = 1)" +
            ")";
    private static String SQL_CREATE_TABLE_hist_series = "CREATE TABLE " + TABLE_NAME_HISTORY_SERIES + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "timestamp VARCHAR(19) NOT NULL, " +
            "series_id INTEGER NOT NULL, " +
            "rate real NOT NULL DEFAULT 0" +
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

        // ダミーデータを挿入
        String SQL_DUMMY = "insert into folder(path, name, parent_id) values('nameAAA', 'nameAAA', 0), ('nameBBB', 'nameBBB', 0), ('nameCCC', 'nameCCC', 0), ('nameDDD', 'nameDDD', 0)";
        String SQL_DUMMY2 = "insert into folder(path, name, directory, parent_id) values('nameAAA/aaa', 'aaa', 'nameAAA', 1), ('nameAAA/bbb', 'bbb', 'nameAAA', 1)";

        db.execSQL(SQL_DUMMY);
        db.execSQL(SQL_DUMMY2);

        // ダミーデータここまで
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
