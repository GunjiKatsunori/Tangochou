package com.example.tangochou;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.model.FolderModel;

public class FolderUpdateFragment extends InputFragment {

    FolderUpdateFragment(Integer selectedId) {
        super("フォルダ名変更", "変更", selectedId);
    }

    /**
     *
     */
    @Override
    public void setOnButtonClickListener(View view) {
        Button button = view.findViewById(R.id.input_button);
        final EditText textBox = view.findViewById(R.id.input_box);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // idの対応するフォルダのデータを取得
                FolderListPresenter presenter = new FolderListPresenter(getContext());
                FolderModel selectedFolder = presenter.openFolder(selectedId);
                String directory = selectedFolder.getDirectory();
                String originalName = selectedFolder.getName();
                String newName = textBox.getText().toString();
                Integer id = selectedFolder.getParentId();
                // DBを更新
                presenter.updateFolderName(directory, originalName, newName);
                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
                // 画面に変更を反映
                ((FolderListActivity)getContext()).createView(id);
            }
        });
    }
}
