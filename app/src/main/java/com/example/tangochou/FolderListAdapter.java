package com.example.tangochou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.CardModel;
import com.example.model.FolderModel;
import com.example.model.IFile;
import com.example.model.SeriesModel;

import java.util.ArrayList;

/**
 * リスト表示を制御する
 * @author 郡司克徳
 * @version 1.0.0
 */
public class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.ViewHolder> {
    /**
     * 表示元のActivity
     */
    private Context context;

    /**
     * 表示されるデータの配列
     */
    private ArrayList<IFile> dataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout linearLayout;
        TextView itemName;
        TextView rightSide;

        ViewHolder(View v) {
            super(v);
            linearLayout = v.findViewById(R.id.folder_list);
            itemName = v.findViewById(R.id.item_name);
            rightSide = v.findViewById(R.id.item_right_side);
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

        // 項目名をクリックしたとき
        TextView itemName = view.findViewById(R.id.item_name);
        itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                if (dataset.get(position) instanceof FolderModel) {
                    // フォルダの場合は下の階層を表示
                    showLowerDirectory(v, holder);
                }else if (dataset.get(position) instanceof SeriesModel) {
                    // 学習セットの場合は個別表示
                    showEachCard(v, holder);
                }
            }
        });

        // 項目の右端をクリックしたとき
        TextView itemRightSide = view.findViewById(R.id.item_right_side);
        itemRightSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                if (dataset.get(position) instanceof FolderModel) {
                    // フォルダの場合は下の階層を表示
                    showLowerDirectory(v, holder);
                }else if (dataset.get(position) instanceof SeriesModel) {
                    // 学習セットの場合はカードの一覧表示
                    showCardList(v, holder);
                }
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // 並び順を更新
        holder.itemName.setText(dataset.get(position).getName());

        // 項目の右端にテキストを設定
        if (dataset.get(position) instanceof SeriesModel) {
            holder.rightSide.setText("リスト表示");
        }

        // 長押しイベントのリスナー
        holder.itemName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                InputFragment fragment = null;
                if (dataset.get(position) instanceof FolderModel) {
                    // フォルダーを長押ししたとき、フォルダ編集フラグメントを表示
                    fragment = new FolderUpdateFragment(dataset.get(position).getId());
                }else if (dataset.get(position) instanceof SeriesModel) {
                    // 学習セットを長押ししたとき、学習セット編集フラグメントを表示
                    fragment = new SeriesUpdateFragment(dataset.get(position).getId());
                }
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

    /**
     * クリックした項目の下の階層に属するフォルダと学習セットを一覧表示する
     * @param v
     * @param holder
     */
    public void showLowerDirectory(View v, ViewHolder holder) {
        // クリックした場所のデータ取得
        final int position = holder.getAdapterPosition();
        Integer folder_id = dataset.get(position).getId();

        // クリックした場所のリスト表示
        ((FolderListActivity)context).createView(folder_id);

    }

    /**
     * クリックした学習セットに属するカード一覧を表示する
     * intentを渡す
     */
    public void showCardList(View v, ViewHolder holder) {
        // クリックした学習セットのidと所属するカード全てを取得
        final int position = holder.getAdapterPosition();
        Integer series_id = dataset.get(position).getId();

        // カード一覧へのページ遷移
        ((FolderListActivity)context).startCardListActivity(series_id);
    }

    /**
     * クリックした学習セットに属するカードを一枚ずつ表示する
     * intentを渡す
     * @param v
     * @param holder
     */
    public void showEachCard(View v, ViewHolder holder) {
        // クリックした場所のデータ取得
        final int position = holder.getAdapterPosition();
        Integer folder_id = dataset.get(position).getId();

        // クリックした場所のリスト表示
        ((FolderListActivity)context).startCardActivity(folder_id);
    }
}
