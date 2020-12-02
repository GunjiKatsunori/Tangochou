package com.example.tangochou;

import com.example.model.FolderModel;
import com.example.model.IFile;
import com.example.model.SeriesModel;

public class EditionSelectionFragment extends SelectionFragment {
    /**
     * 操作の対象となるIFileオブジェクト
     */
    IFile file;

    /**
     * コンストラクタ
     */
    public EditionSelectionFragment() {
        setTextButton1("名前の変更");
        setTextButton2("削除");
    }

    /**
     * fileを設定する
     */
    public void setFile(IFile file) {
        this.file = file;
    }

    /**
     * 上のボタンを押した時に編集フラグメントが表示されるようにする
     */
    @Override
    public void onClickButton1() {
        InputFragment fragment = null;
        if (file instanceof FolderModel) {
            // フォルダーを長押ししたとき、フォルダ編集フラグメントを表示
            fragment = new FolderUpdateFragment(file.getId());
        }else if (file instanceof SeriesModel) {
            // 学習セットを長押ししたとき、学習セット編集フラグメントを表示
            fragment = new SeriesUpdateFragment(file.getId());
        }
        ((FolderListActivity)getActivity()).createInputFragment(fragment);
    }

    /**
     * 下のボタンを押した時に削除フラグメントが表示されるようにする
     */
    @Override
    public void onClickButton2() {
        String table = null;
        if (file instanceof FolderModel) {
            table = "folder";
        }else if (file instanceof SeriesModel) {
            table = "series";
        }

        DeleteFragment fragment = new DeleteFragment(table, file.getId());
        ((FolderListActivity)getActivity()).createInputFragment(fragment);
    }
}
