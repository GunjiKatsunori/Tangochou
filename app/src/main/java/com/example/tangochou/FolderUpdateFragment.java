package com.example.tangochou;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.model.FolderModel;

public class FolderUpdateFragment extends InputFragment {
    /**
     * 所属フォルダのID
     */
    Integer parentId;

    /**
     * コンストラクター
     * @param selectedId
     */
    FolderUpdateFragment(Integer selectedId) {
        super("フォルダ名変更", "変更", selectedId);
    }

    /**
     * フォルダ名の変更をDBに反映させる処理
     * @param view
     */
    @Override
    public void updateTable(View view) {
        // idの対応するフォルダのデータを取得
        EditText textBox = view.findViewById(R.id.input_box);
        String newName = textBox.getText().toString();
        FolderListPresenter presenter = new FolderListPresenter(getContext());
        FolderModel selectedFolder = (FolderModel) presenter.getFile("folder", selectedId);
        parentId = selectedFolder.getParentId();
        // DBを更新
        presenter.updateFileName(selectedFolder, newName);

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
