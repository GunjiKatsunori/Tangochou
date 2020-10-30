package com.example.tangochou;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 *
 */
public class FolderListActivity extends AppCompatActivity {
    // 表示するデータのリスト
    private static ArrayList<String> folders = new ArrayList<>();

    FolderListAdapter adapter;
    FolderOpenHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_list);

        // DBからデータを呼び出す
        helper = new FolderOpenHelper(getApplicationContext());
        db = helper.getReadableDatabase();
        Cursor cursor = db.query("folder", new String[]{"name"}, null, null, null, null, null);
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++) {
            folders.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();

        // フォルダリストを表示する
        RecyclerView folderRecyclerView = findViewById(R.id.folder_recycler_view);
        folderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setLayoutManager(layoutManager);
        adapter = new FolderListAdapter(folders);
        folderRecyclerView.setAdapter(adapter);
    }
}