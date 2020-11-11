package com.example.tangochou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
                Integer folder_id = dataset.get(position).getId();
                FolderListPresenter folderListPresenter = new FolderListPresenter(parent.getContext());
                dataset = folderListPresenter.openFolderList(folder_id);

                // クリックした場所のリスト表示を描画
                ((FolderListActivity)context).createView(folder_id);
            }

        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // 並び順を更新
        holder.folderName.setText(dataset.get(position).getName());

        // 長押しイベントのリスナー
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                InputFragment fragment = new FolderUpdateFragment(dataset.get(position).getId());
                ((FolderListActivity)context).createInputFragment(fragment);
                return true;
            }
        });
    }

    // アイテムの総数を返す
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
