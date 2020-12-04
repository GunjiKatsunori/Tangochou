package com.example.tangochou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.model.FolderModel;
import com.example.model.SeriesModel;

/**
 * 学習セット名変更のためのフラグメントを制御する
 */
public class SeriesUpdateFragment extends InputFragment {
    /**
     * 所属フォルダのID
     */
    Integer parentId;

    /**
     * コンストラクター
     * @param selectedId 変更対象の学習セットのID
     */
    SeriesUpdateFragment(Integer selectedId) {
        super("学習セット名変更", "変更", selectedId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // テキストボックスに変更前の名前を表示
        EditText textBox = view.findViewById(R.id.input_box);
        FolderListPresenter presenter = new FolderListPresenter(getContext());
        String fileName = presenter.getFile("series", selectedId).getName();
        textBox.setText(fileName);

        return view;
    }

    /**
     * 学習セット名の変更をDBに反映させる処理
     * @param view
     */
    @Override
    public void updateTable(View view) {
        // 新しい学習セット名を取得
        EditText textBox = view.findViewById(R.id.input_box);
        String newName = textBox.getText().toString();
        // 変更対象の学習セットのレコードを取得
        FolderListPresenter presenter = new FolderListPresenter(getContext());
        SeriesModel selectedSeries = (SeriesModel) presenter.getFile("series", selectedId);
        parentId = selectedSeries.getParentId();
        // DBを更新
        presenter.updateFileName(selectedSeries, newName);
    }


    /**
     * 一覧画面の再表示
     * 更新データを反映させるため
     */
    @Override
    public void reloadActivity() {
        ((FolderListActivity)getContext()).createView(parentId);
    }
}
