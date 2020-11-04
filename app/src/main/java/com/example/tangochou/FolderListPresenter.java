package com.example.tangochou;

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

    public ArrayList<FolderModel> openFolderList(String directory) {
        ArrayList<FolderModel> folders = new ArrayList<FolderModel>();
        // DBアクセスのヘルパー
        DBOpenHelper helper;
        // DBからデータを取り出して格納
        helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("folder", new String[]{"id", "path", "name", "directory", "parent_id"}, null, null, null, null, null);
        cursor.moveToFirst();

        //Log.d("", cursor.);

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
}
