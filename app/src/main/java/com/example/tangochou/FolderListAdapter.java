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
import com.example.model.IFile;

import java.util.ArrayList;

/**
 * リスト表示を制御する
 * @author 郡司克徳
 * @version 1.0.0
 */
public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<IFile> dataset;
    private AdapterView.OnItemClickListener listener;

    /**
     * 繰り返されたビューやその位置を制御するクラス
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout linearLayout;
        TextView folderName;

        ViewHolder(View v) {
            super(v);
            linearLayout = v.findViewById(R.id.folder_list);
            folderName = v.findViewById(R.id.folder_name);
        }
    }


    /**
     * コンストラクタ
     * @param context 表示元のActivity
     * @param dataset 繰り返し表示されるデータ
     */
    FolderListAdapter(Context context, ArrayList<IFile> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    /**
     * リスト表示ビューを生成するときの処理
     * @param parent
     * @param ViewType
     * @return
     */
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        // 項目をクリックしたときに、その下の階層に属するものを一覧表示する
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // クリックした場所のデータ取得
                final int position = holder.getAdapterPosition();
                Integer folder_id = dataset.get(position).getId();
                FolderListPresenter folderListPresenter = new FolderListPresenter(parent.getContext());
                dataset = folderListPresenter.getFileList("folder", folder_id);

                // クリックした場所のリスト表示
                ((FolderListActivity)context).createView(folder_id);
            }

        });

        // 学習セットの場合はボタンをクリックしたときに、カードの個別表示を行うようにする

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

    /**
     * @return リスト表示対象の項目総数
     */
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
