package com.example.tangochou;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FolderOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tangochou.db";
    private static String SQL_CREATE_ENTRIES = "CREATE TABLE folder (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "path VARCHAR(100) NOT NULL UNIQUE, " +
            "name VARCHAR(100) NOT NULL, " +
            "directory VARCHAR(100), " +
            "parent_id INTEGER" +
            ")";
    private static String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS folder";

    FolderOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // DB作成時はSQL_CREATE_ENTRIESを実行
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        // ダミーデータを挿入
        String SQL_DUMMY = "insert into folder(path, name) values('pathaaa', 'nameAAA'), ('pathbbb', 'nameBBB'), ('pathccc', 'nameCCC'), ('pathddd', 'nameDDD')";

        db.execSQL(SQL_DUMMY);

        // ダミーデータここまで
    }

    // バージョン更新時の動作
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // バージョンダウングレード時の動作
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}