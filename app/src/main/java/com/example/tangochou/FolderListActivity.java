package com.example.tangochou;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.IFile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * フォルダー一覧表示画面(Activity)を制御する
 * @author 郡司克徳
 * @version 1.0.0
 */
public class FolderListActivity extends AppCompatActivity {
    /**
     * 現在のディレクトリがフォルダ内か学習セット内かをあらわす
     */
    private String directoryEnv;

    /**
     * ディレクトリのid
     */
    private Integer id=0;

    FolderListAdapter adapter;
    FolderListPresenter folderListPresenter;

    /**
     * 選択フラグメントが表示されているかを表すフラグ
     * 両面表示されている: true
     * されていない     : false
     */
    Boolean selectionFlag = false;

    /**
     * 追加・編集フラグメントが表示されているかを表すフラグ
     * 両面表示されている: true
     * されていない     : false
     */
    Boolean inputFlag = false;

    /**
     * 一覧画面生成時の処理
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_list);

        createView(id);
    }

    /**
     * 画面全体の表示処理
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
        // IDからディレクトリを検索し、一覧表示画面を再描画
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FolderListPresenter presenter = new FolderListPresenter(getApplicationContext());
                createView(presenter.getIdToBack(ID));
            }
        });

        // Folderのリスト表示
        createList("folder", "series");

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

    }

    /**
     * リスト表示部分
     * @param tables
     */
    public void createList(String... tables) {
        ArrayList<IFile> files = new ArrayList<>();

        // リスト表示のためのデータを呼び出す
        folderListPresenter = new FolderListPresenter(this);
        for (String table : tables) {
            files.addAll(folderListPresenter.getFileList(table, id));
        }

        // フォルダリストを表示する
        RecyclerView folderRecyclerView = findViewById(R.id.folder_recycler_view);
        folderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setLayoutManager(layoutManager);
        adapter = new FolderListAdapter(this, files);
        folderRecyclerView.setAdapter(adapter);
    }

    /**
     * 新規作成対象がフォルダか学習セットかを選択するフラグメントの表示
     * @param fragment
     */
    public void createSelectionFragment(SelectionFragment fragment) {
        // 選択フラグメントが表示されていないときだけ表示処理を実行する
        if (!selectionFlag) {
            // フラグをtrueにする
            selectionFlag = true;

            // Activityにフラグメントを追加する
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            tr.add(R.id.selection_container, fragment);
            tr.commit();
        }
    }

    /**
     * 追加・編集フラグメントの表示
     * @param fragment
     */
    public void createInputFragment(InputFragment fragment) {
        // 追加・編集フラグメントが表示されていないときだけ表示処理を実行する
        if (!inputFlag) {
            // フラグをtrueにする
            inputFlag = true;

            // Activityにフラグメントを追加する
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            tr.add(R.id.folder_list, fragment);
            tr.commit();
        }
    }

    /**
     * CardListActivity に画面遷移する
     * @param series_id
     */
    public void startCardListActivity(Integer series_id) {
        Intent intent = new Intent(this.getApplicationContext(), CardListActivity.class);
        intent.putExtra("series_id", series_id);
        startActivity(intent);
    }

    /**
     * CardActivity に画面遷移する
     * @param series_id
     */
    public void startCardActivity(Integer series_id) {
        Intent intent = new Intent(this.getApplicationContext(), CardActivity.class);
        intent.putExtra("series_id", series_id);
        startActivity(intent);
    }
}