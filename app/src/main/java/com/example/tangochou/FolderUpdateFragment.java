package com.example.tangochou;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.model.FolderModel;

public class FolderUpdateFragment extends InputFragment {
    Integer listViewId;

    /**
     * コンストラクター
     * @param selectedId
     */
    FolderUpdateFragment(Integer selectedId) {
        super("フォルダ名変更", "変更", selectedId);
    }

    @Override
    public void updateTable(View view) {
        // idの対応するフォルダのデータを取得
        EditText textBox = view.findViewById(R.id.input_box);
        String newName = textBox.getText().toString();
        FolderListPresenter presenter = new FolderListPresenter(getContext());
        FolderModel selectedFolder = (FolderModel) presenter.getFile("folder", selectedId);
        String directory = selectedFolder.getDirectory();
        String originalName = selectedFolder.getName();
        listViewId = selectedFolder.getParentId();
        // DBを更新
        presenter.updateFolderName(directory, originalName, newName);

    }


    /**
     * 一覧画面の再表示
     * 更新データを反映させるため
     */
    @Override
    public void reloadActivity() {
        ((FolderListActivity)getContext()).createView(listViewId);
    }

}
