package com.example.tangochou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.IFile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * カード一覧表示画面(Activity)を制御する
 * @author 郡司克徳
 * @version 1.0.0
 */
public class CardListActivity extends AppCompatActivity {
    /**
     * 学習セットのID
     */
    private Integer id;

    ArrayList<IFile> files;

    CardListAdapter adapter;
    FolderListPresenter folderListPresenter;
    RecyclerView folderRecyclerView;

    /**
     * 追加・編集フラグメントが表示されているかを表すフラグ
     * 両面表示されている: true
     * されていない     : false
     */
    Boolean inputFlag = false;

    /**
     * idを返す
     * @return
     */
    public Integer getId() {return id;}

    /**
     * 一覧画面生成時の処理
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_list);

        // FolderListActivity から intent を受け取る
        Intent intent = getIntent();
        id = intent.getIntExtra("series_id", 0);

        createView(id);
    }

    /**
     * idで指定した学習セット内のカード一覧を表示
     * @param id
     */
    public void createView(Integer id) {
        this.id = id;
        final Integer ID = id;

        // ツールバーのアイコンなどの表示
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 「戻る」ボタンクリック時に CardListActivity を終了させる
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Folderのリスト表示
        createList();

        // フロートボタンクリック時
        // 追加項目を選択するフラグメントを表示
        FloatingActionButton fButton = findViewById(R.id.add_folder);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardInputFragment fragment = new CardAddFragment(ID);
                createCardInputFragment(fragment);
            }
        });
    }

    public void createList() {
        // リスト表示のためのデータを呼び出す
        folderListPresenter = new FolderListPresenter(this);
        files = folderListPresenter.getFileList("card", id);

        // フォルダリストを表示する
        folderRecyclerView = findViewById(R.id.folder_recycler_view);
        folderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setLayoutManager(layoutManager);
        adapter = new CardListAdapter(this, files);
        folderRecyclerView.setAdapter(adapter);

        // ドラッグおよびスワイプイベントの処理
        ItemTouchHelper itHelper = createItemTouchHelper(this);
        itHelper.attachToRecyclerView(folderRecyclerView);
    }

    /**
     * カード追加・編集フラグメントの表示処理
     * @param fragment
     */
    public void createCardInputFragment(CardInputFragment fragment) {
        // 両面表示されていないときだけ両面表示処理を実行する
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
     * ドラッグおよびスワイプイベントの処理の定義
     * @return
     */
    private ItemTouchHelper createItemTouchHelper(final Context context) {
        return new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

                    // ドラッグしたときにカードのオブジェクトを並べ替える
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        // 表示上の並べ替え処理
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        adapter.notifyItemMoved(fromPos, toPos);

                        return true;
                    }

                    // スワイプしたときにカードのオブジェクトを削除する
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        // 表示上の削除処理
                        final int fromPos = viewHolder.getAdapterPosition();
                        files.remove(fromPos);
                        adapter.notifyItemRemoved(fromPos);

                        // DBからの削除処理
                        FolderListPresenter presenter = new FolderListPresenter(context);
                        presenter.delete("card", ((CardListAdapter.ViewHolder)viewHolder).getCard().getId());
                    }
                }
        );
    }
}
