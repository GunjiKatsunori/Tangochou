package com.example.tangochou;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.model.FolderModel;

public class FolderAddFragment extends InputFragment {

    FolderAddFragment(Integer selectedId) {
        super("新規フォルダ作成", "追加", selectedId);
    }

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
                String newName = textBox.getText().toString();
                String directory = null;
                if (selectedFolder != null) {
                    directory = selectedFolder.getPath();
                }
                // DBを更新
                presenter.addFolder(directory, newName, selectedId);

                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
                // 画面に変更を反映
                ((FolderListActivity)getContext()).createView(selectedId);
            }
        });
    }
}
