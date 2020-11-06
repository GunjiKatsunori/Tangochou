package com.example.tangochou;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.FolderModel;

import java.util.ArrayList;

/**
 *
 */
public class FolderListActivity extends AppCompatActivity {
    // 表示するデータのリスト
    private static ArrayList<FolderModel> folders = new ArrayList<>();
    // ディレクトリ
    private String directory;

    FolderListAdapter adapter;
    FolderListPresenter folderListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_list);

        createView(directory);
    }

    /**
     * 与えられたdirectory以下のデータ一覧を取得して表示
     * @param directory
     */
    public void createView(String directory) {
        this.directory = directory;

        // リスト表示のためのデータを呼び出す
        folderListPresenter = new FolderListPresenter(this);
        folders = folderListPresenter.openFolderList(directory);

        // フォルダリストを表示する
        RecyclerView folderRecyclerView = findViewById(R.id.folder_recycler_view);
        folderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setLayoutManager(layoutManager);
        adapter = new FolderListAdapter(this, folders);
        folderRecyclerView.setAdapter(adapter);

    }

    /**
     * 入力フラグメントの表示
     */
    public void createInputFragment(String title, String actionName, FolderModel folder) {
        InputFragment fragment = new InputFragment(title, actionName, folder);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.container, fragment);
        tr.commit();
    }
}