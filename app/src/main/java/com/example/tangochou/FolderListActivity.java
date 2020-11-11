package com.example.tangochou;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.FolderModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 *
 */
public class FolderListActivity extends AppCompatActivity {
    // 表示するデータのリスト
    private static ArrayList<FolderModel> folders = new ArrayList<>();
    // ディレクトリ
    private String directory;
    private Integer id=0;

    FolderListAdapter adapter;
    FolderListPresenter folderListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_list);

        createView(id);
    }

    /**
     * 与えられたdirectory以下のデータ一覧を取得して表示
     * @param id
     */
    public void createView(Integer id) {
        this.id = id;

        // リスト表示のためのデータを呼び出す
        folderListPresenter = new FolderListPresenter(this);
        folders = folderListPresenter.openFolderList(id);

        // フォルダリストを表示する
        RecyclerView folderRecyclerView = findViewById(R.id.folder_recycler_view);
        folderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setLayoutManager(layoutManager);
        adapter = new FolderListAdapter(this, folders);
        folderRecyclerView.setAdapter(adapter);

        // 追加ボタンクリック時の動作
        final Integer selectedId = id;
        FloatingActionButton fButton = findViewById(R.id.add_folder);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFragment fragment = new FolderAddFragment(selectedId);
                createInputFragment(fragment);
            }
        });

    }

    /**
     * 入力フラグメントの表示
     * 追加と編集で共用
     */
    public void createInputFragment(InputFragment fragment) {
        // Activityにフラグメントを追加する
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.container, fragment);
        tr.commit();
    }

    // add_folderをクリックすると追加用のInputFragmentを表示
    // add_folderのクリックリスナ
    // InputFragmentの「追加」を表示するためのパラメータ
    // InputFragmentを呼び出す
}