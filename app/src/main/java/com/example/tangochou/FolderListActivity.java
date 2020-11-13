package com.example.tangochou;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MotionEvent;
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
        final Integer ID = id;

        // ツールバーのアイコンなどの表示
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 「戻る」ボタンクリック時のアクション
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FolderListPresenter presenter = new FolderListPresenter(getApplicationContext());
                createView(presenter.getIdToBack(ID));
            }
        });

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

        // フロートボタンクリック時
        // 追加項目を選択するフラグメントを表示
        FloatingActionButton fButton = findViewById(R.id.add_folder);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectionFragment fragment = new SelectionFragment(ID);
                createSelectionFragment(fragment);
            }
        });


        //
        findViewById(R.id.input_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int id = v.getId();

                switch(id) {
                    case R.id.input_container:
                        return true;
                }
                return false;
            }
        });

    }

    public void createSelectionFragment(SelectionFragment fragment) {
        // Activityにフラグメントを追加する
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.selection_container, fragment);
        tr.commit();
    }

    /**
     * 入力フラグメントの表示
     * 追加と編集で共用
     */
    public void createInputFragment(InputFragment fragment) {
        // Activityにフラグメントを追加する
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.input_container, fragment);
        tr.commit();
    }
}