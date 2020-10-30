package com.example.tangochou;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class FolderListActivity extends AppCompatActivity {
    // 表示するデータのリスト
    // あとでSQLからのデータに対応
    private static String[] li = {"aaa", "bbb", "ccc"};
    private static ArrayList<String> folders = new ArrayList<>(Arrays.asList(li));

    FolderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_list);

        // リスト関係の設定
        RecyclerView folderRecyclerView = findViewById(R.id.folder_recycler_view);
        folderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setLayoutManager(layoutManager);
        adapter = new FolderListAdapter(folders);
        folderRecyclerView.setAdapter(adapter);
    }
}