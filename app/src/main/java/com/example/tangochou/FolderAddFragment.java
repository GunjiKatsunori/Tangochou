package com.example.tangochou;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.model.FolderModel;

/**
 * フォルダー追加フラグメントを実装したクラス
 * @author 郡司克徳
 * @version 1.0.0
 */
public class FolderAddFragment extends InputFragment {

    /**
     * コンストラクタ
     * @param selectedId 追加先フォルダのID
     */
    FolderAddFragment(Integer selectedId) {
        super("新規フォルダ作成", "追加", selectedId);
    }

    @Override
    public void updateTable(View view) {
        // idに対応するフォルダのデータを取得
        EditText textBox = view.findViewById(R.id.input_box);
        String newName = textBox.getText().toString();
        FolderListPresenter presenter = new FolderListPresenter(getContext());
        FolderModel selectedFolder = (FolderModel) presenter.getFile("folder", selectedId);
        String directory = null;
        if (selectedFolder != null) {
            directory = selectedFolder.getPath();
        }
        // DBに登録
        presenter.addFolder(directory, newName, selectedId);
    }

    /**
     * フォルダ一覧画面の再表示
     * 追加後のデータを反映させるため
     */
    @Override
    public void reloadActivity() {
        ((FolderListActivity)getContext()).createView(selectedId);
    }

}
