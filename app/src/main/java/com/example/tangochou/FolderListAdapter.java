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
            folderName = v.findViewById(R.id.item_name);
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
                showLowerDirectory(v, holder);
            }
        });

        // リストの名前表示部分をクリックしたとき
        TextView item_name = view.findViewById(R.id.item_name);
        item_name.setClickable(true);
        item_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("itemname CLICKED");
                final int position = holder.getAdapterPosition();
                if (dataset.get(position) instanceof FolderModel) {
                    // フォルダの場合は下の階層を表示
                    showLowerDirectory(v, holder);
                }else if (dataset.get(position) instanceof SeriesModel) {
                    // 学習セットの場合はカードの個別表示
                    showCards(v, holder);
                }else if (dataset.get(position) instanceof CardModel) {
                    // カードの場合は編集画面
                }
            }
        });

        // リストの左端部分をクリックしたとき
        TextView itemLeftSide = view.findViewById(R.id.item_left_side);
        itemLeftSide.setClickable(true);
        itemLeftSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("left side CLICKED");
                final int position = holder.getAdapterPosition();
                if (dataset.get(position) instanceof FolderModel) {
                    // フォルダの場合は下の階層を表示
                    showLowerDirectory(v, holder);
                }else if (dataset.get(position) instanceof SeriesModel) {
                    // 学習セットの場合はカードの個別表示
                    showCards(v, holder);
                }else if (dataset.get(position) instanceof CardModel) {
                    // カードの場合は編集画面
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // 並び順を更新
        holder.folderName.setText(dataset.get(position).getName());

        // 長押しイベントのリスナー
        holder.folderName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                InputFragment fragment = null;
                if (dataset.get(position) instanceof FolderModel) {
                    // フォルダーを長押ししたとき、フォルダ編集フラグメントを表示
                    fragment = new FolderUpdateFragment(dataset.get(position).getId());
                }else if (dataset.get(position) instanceof SeriesModel) {
                    // 学習セットを長押ししたとき、学習セット編集フラグメントを表示
                    fragment = new SeriesUpdateFragment(dataset.get(position).getId());
                }else if (dataset.get(position) instanceof CardModel) {

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
     * クリックした項目の下の階層に属するものを一覧表示する
     * @param v
     * @param holder
     */
    public void showLowerDirectory(View v, ViewHolder holder) {
        // クリックした場所のデータ取得
        final int position = holder.getAdapterPosition();
        Integer folder_id = dataset.get(position).getId();
        //FolderListPresenter folderListPresenter = new FolderListPresenter((FolderListActivity)context);
        //dataset = folderListPresenter.getFileList("folder", folder_id);

        // クリックした場所のリスト表示
        ((FolderListActivity)context).createView(folder_id);

    }

    public void showCards(View v, ViewHolder holder) {

    }
}
