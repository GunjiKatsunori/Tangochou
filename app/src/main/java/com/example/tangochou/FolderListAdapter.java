package com.example.tangochou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.FolderModel;

import java.util.ArrayList;

public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FolderModel> dataset;

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

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 並び順を更新
        holder.folderName.setText(dataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
