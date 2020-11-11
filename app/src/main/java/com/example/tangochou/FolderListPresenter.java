package com.example.tangochou;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.FolderModel;

import java.util.ArrayList;

public class FolderListPresenter {
    private Context context;

    public FolderListPresenter(Context context) {
        this.context = context;
    }

    /**
     * idからフォルダを取得する
     */
    public FolderModel openFolder(Integer id) {
        Log.d("id", String.valueOf(id));
        // DBからデータを取り出して格納
        DBOpenHelper helper= new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM folder WHERE id=?;", new String[] {String.valueOf(id)} );
        FolderModel folder = null;
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            folder = new FolderModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4)

            );
        }

        cursor.close();
        return folder;
    }

    /**
     * parent_idからフォルダの一覧を取得する
     * @param parent_id
     * @return
     */
    public ArrayList<FolderModel> openFolderList(Integer parent_id) {
        ArrayList<FolderModel> folders = new ArrayList<FolderModel>();
        // DBからデータを取り出して格納
        DBOpenHelper helper= new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectionClause = "parent_id IS NULL";
        if (parent_id != null) {
            selectionClause = "parent_id = " + "'" + parent_id + "'";
        }
        Cursor cursor = db.query("folder", new String[]{"id", "path", "name", "directory", "parent_id"}, selectionClause, null, null, null, null);
        cursor.moveToFirst();

        for (int i=0; i<cursor.getCount(); i++) {
            FolderModel folder = new FolderModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            );
            folders.add(folder);
            cursor.moveToNext();
        }
        cursor.close();

        return folders;
    }

    /**
     * レコード追加
     * @param directory
     * @param name
     * @param parent_id
     */
    public void addFolder(String directory, String name, Integer parent_id) {
        DBOpenHelper helper= new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String path = name;
        if (directory != null) {
            path = directory + "/" + name;
        }
        String SQLInsert = "insert into folder(path, name, directory, parent_id) values(" +
                "'" + path + "'" + ", " +
                "'" + name + "'" + ", " +
                "'" + directory + "'" + ", " +
                "'" + parent_id + "'" +
                ")";
        db.execSQL(SQLInsert);
    }

    /**
     * フォルダ名を更新する
     * @param directory
     * @param originalName
     * @param newName
     */
    public void updateFolderName(String directory, String originalName, String newName) {
        String originalPath = originalName;
        String newPath = newName;
        if (directory != null) {
            originalPath = directory + "/" + originalName;
            newPath = directory + "/" + newName;
        }
        // 更新データを用意する
        ContentValues cv = new ContentValues();
        cv.put("path", newPath);
        cv.put("name", newName);
        // DBにアクセスする
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = "path = " + "'" + originalPath + "'";
        Log.d("db", whereClause);
        db.update("folder", cv, whereClause, null);
    }
}
