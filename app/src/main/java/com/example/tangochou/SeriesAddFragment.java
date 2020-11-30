package com.example.tangochou;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.model.FolderModel;
import com.example.model.SeriesModel;

/**
 * 学習セット追加フラグメントを実装したクラス
 * @author 郡司克徳
 * @version 1.0.0
 */
public class SeriesAddFragment extends InputFragment {

    /**
     * コンストラクタ
     * @param selectedId 追加先フォルダのID
     */
    public SeriesAddFragment(Integer selectedId) {
        super("新規学習セット作成", "追加", selectedId);
    }

    /**
     * Seriesテーブルにレコードを追加する
     * @param view
     */
    @Override
    public void updateTable(View view) {
        // 入力された学習セット名を取得
        EditText textBox = view.findViewById(R.id.input_box);
        String newName = textBox.getText().toString();
        // 追加先フォルダの情報を取得
        FolderListPresenter presenter = new FolderListPresenter(getContext());
        FolderModel selectedFolder = (FolderModel) presenter.getFile("folder", selectedId);
        String directory = null;
        if (selectedFolder != null) {
            directory = selectedFolder.getPath();
        }
        // 新規学習セットのDB登録
        SeriesModel series = new SeriesModel(null, null, newName, directory, selectedId);
        presenter.addFile(series);
    }

    /**
     * フォルダ一覧画面の再表示
     * 追加後のデータを反映させるための処理
     */
    @Override
    public void reloadActivity() {
        ((FolderListActivity)getContext()).createView(selectedId);
    }
}
