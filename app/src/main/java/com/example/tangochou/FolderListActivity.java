package com.example.tangochou;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

    FolderListAdapter adapter;
    FolderListPresenter folderListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folder_list);

        // リスト表示のためのデータを呼び出す
        folderListPresenter = new FolderListPresenter(this);
        folders = folderListPresenter.openFolderList(null);

        // フォルダリストを表示する
        RecyclerView folderRecyclerView = findViewById(R.id.folder_recycler_view);
        folderRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        folderRecyclerView.setLayoutManager(layoutManager);
        adapter = new FolderListAdapter(this, folders);
        folderRecyclerView.setAdapter(adapter);

        /*
        ItemTouchHelper ith = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
                    // 長押しでフォルダ名変更
                    @Override
                    public boolean onLongClick(RecyclerView recyclerView) {
                        // positionを取得
                        final int position = holder.getAdapterPosition();
                        String selectedText = dataset.get(position);



                        return true;
                    }

                });

        ith.attachToRecyclerView(folderRecyclerView);
        */
    }
}