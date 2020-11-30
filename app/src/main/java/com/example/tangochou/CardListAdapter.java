package com.example.tangochou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.IFile;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {
    /**
     * 表示元のActivity
     */
    private Context context;

    /**
     * 表示されるデータの配列
     */
    private ArrayList<IFile> dataset;

    /**
     * 繰り返されたビューやその位置を制御するクラス
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout linearLayout;
        TextView cardName;

        ViewHolder(View v) {
            super(v);
            linearLayout = v.findViewById(R.id.folder_list);
            cardName = v.findViewById(R.id.item_name);
        }
    }

    /**
     * コンストラクタ
     * @param context 表示元のActivity
     * @param dataset リスト表示対象データの配列
     */
    CardListAdapter(Context context, ArrayList<IFile> dataset) {
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
    public CardListAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_item, parent, false);
        final CardListAdapter.ViewHolder holder = new CardListAdapter.ViewHolder(view);

        // クリック時の処理をここにかく
        // クリック時は編集フラグメントを表示

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardListAdapter.ViewHolder holder, final int position) {
        // 並び順を更新
        holder.cardName.setText(dataset.get(position).getName());
    }

    /**
     * @return リスト表示対象の項目総数
     */
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
