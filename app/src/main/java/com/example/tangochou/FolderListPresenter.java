package com.example.tangochou;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.CardModel;
import com.example.model.FolderModel;
import com.example.model.IFile;
import com.example.model.SeriesModel;

import java.util.ArrayList;

/**
 * 画面表示とデータベースアクセスの仲介を行う
 * @author 郡司克徳
 * @version 1.0.0
 */
public class FolderListPresenter {
    private Context context;

    public FolderListPresenter(Context context) {
        this.context = context;
    }

    /**
     * 指定したテーブルから指定されたidに対応するレコードを取得する
     * @param table テーブル名
     * @param id ID
     * @return 取得したレコードのデータを格納したモデルクラスのインスタンス
     *         レコードがない場合は null を返す
     */
    public IFile getFile(String table, Integer id) {
        // DBからデータを取り出す
        DBOpenHelper helper= new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table + " WHERE id=?;", new String[] {String.valueOf(id)} );

        // 戻り値の生成
        // テーブルによってクラスを場合分けする
        IFile file = null;
        if(table.equals("folder")) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                file = new FolderModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)

                );
            }
        }
        else if (table.equals("series")) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                file = new SeriesModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)

                );
            }
        }
        else if (table.equals("card")) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                file = new CardModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)

                );
            }
        }

        cursor.close();
        return file;
    }

    /**
     * parent_idからフォルダの一覧を取得する
     * @param table テーブル名
     * @param parent_id 一覧を取得したいフォルダもしくは学習セットのID
     * @return 所属するデータのArrayList
     *         要素は指定したテーブルに対応するモデルクラスのインスタンス
     *         存在しない場合は空のArrayListを返す
     */
    public ArrayList<IFile> getFileList(String table, Integer parent_id) {
        // DBからデータを取り出す
        DBOpenHelper helper= new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectionClause = "parent_id IS NULL";
        if (parent_id != null) {
            selectionClause = "parent_id = " + "'" + parent_id + "'";
        }
        Cursor cursor = db.query(table, new String[]{"id", "path", "name", "directory", "parent_id"}, selectionClause, null, null, null, null);
        cursor.moveToFirst();

        // 戻り値を生成
        ArrayList<IFile> fileList = new ArrayList<IFile>();
        if(table.equals("folder")) {
            for (int i = 0; i < cursor.getCount(); i++) {
                FolderModel folder = new FolderModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                );
                fileList.add(folder);
                cursor.moveToNext();
            }
        }
        else if(table.equals("series")) {
            for (int i = 0; i < cursor.getCount(); i++) {
                SeriesModel series = new SeriesModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                );
                fileList.add(series);
                cursor.moveToNext();
            }
        }
        else if(table.equals("card")) {

        }
        cursor.close();

        return fileList;
    }

    /**
     * folderテーブルもしくはseriesテーブルへのDB登録
     * @param directory
     * @param name
     * @param parent_id
     */
    public void addFile(String table, String directory, String name, Integer parent_id) {
        // DBにデータを登録する
        DBOpenHelper helper= new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String path = name;
        if (directory != null) {
            path = directory + "/" + name;
        }

        String SQLInsert = null;
        if (table.equals("folder")) {
            SQLInsert = "insert into folder(path, name, directory, parent_id) values(" +
                    "'" + path + "'" + ", " +
                    "'" + name + "'" + ", " +
                    "'" + directory + "'" + ", " +
                    "'" + parent_id + "'" +
                    ")";

        }else if (table.equals("series")) {
            SQLInsert = "insert into series(path, name, directory, parent_id) values(" +
                    "'" + path + "'" + ", " +
                    "'" + name + "'" + ", " +
                    "'" + directory + "'" + ", " +
                    "'" + parent_id + "'" +
                    ")";

        }
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

    /**
     * 戻るボタンの操作のためのidを取得
     * @param id
     * @return
     */
    public Integer getIdToBack(Integer id) {
        return getFile("folder", id).getParentId();
    }
}
