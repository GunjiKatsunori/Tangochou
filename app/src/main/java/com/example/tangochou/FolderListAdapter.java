package com.example.tangochou;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.FolderModel;

import java.util.ArrayList;

public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FolderModel> dataset;
    private AdapterView.OnItemClickListener listener;

    // ホルダーの定義
    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout linearLayout;
        TextView folderName;

        ViewHolder(View v) {
            super(v);
            linearLayout = v.findViewById(R.id.folder_list);
            folderName = v.findViewById(R.id.folder_name);
        }
    }

    // コンストラクター
    FolderListAdapter(Context context, ArrayList<FolderModel> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        // クリックイベントのリスナー
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリックした場所のデータ取得
                final int position = holder.getAdapterPosition();
                String directory = dataset.get(position).getPath();
                FolderListPresenter folderListPresenter = new FolderListPresenter(parent.getContext());
                dataset = folderListPresenter.openFolderList(directory);

                // クリックした場所のリスト表示を描画
                ((FolderListActivity)context).createView(directory);
                Log.d("aaaaa", "dddddddd");
            }

        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 並び順を更新
        holder.folderName.setText(dataset.get(position).getName());
    }

    // アイテムの総数を返す
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
