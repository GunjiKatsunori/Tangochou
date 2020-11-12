package com.example.tangochou;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.model.FolderModel;

public class FolderAddFragment extends InputFragment {

    FolderAddFragment(Integer selectedId) {
        super("新規フォルダ作成", "追加", selectedId);
    }

    @Override
    public void updateTable(View view) {
        // idの対応するフォルダのデータを取得
        EditText textBox = view.findViewById(R.id.input_box);
        String newName = textBox.getText().toString();
        FolderListPresenter presenter = new FolderListPresenter(getContext());
        FolderModel selectedFolder = presenter.openFolder(selectedId);
        String directory = null;
        if (selectedFolder != null) {
            directory = selectedFolder.getPath();
        }
        // DBを更新
        presenter.addFolder(directory, newName, selectedId);
    }

    @Override
    public void reloadActivity() {
        ((FolderListActivity)getContext()).createView(selectedId);
    }

}
