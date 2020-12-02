package com.example.tangochou;

/**
 * 画面右下の追加用フロートボタンを押したときに表示するフラグメントを定義する
 */
public class AdditionSelectionFragment extends SelectionFragment {
    Integer selectedId;

    /**
     * コンストラクタ
     */
    public AdditionSelectionFragment() {
        setTextButton1("学習セット");
        setTextButton2("フォルダ");
    }

    public void setSelectedId(Integer selectedId) {
        this.selectedId = selectedId;
    }

    /**
     * 上のボタンを押した時に学習セット追加フラグメントが表示されるようにする
     */
    @Override
    public void onClickButton1() {
        InputFragment inputFragment = new SeriesAddFragment(selectedId);
        ((FolderListActivity)getContext()).createInputFragment(inputFragment);
    }

    /**
     * 下のボタンを押した時にフォルダ追加フラグメントが表示されるようにする
     */
    @Override
    public void onClickButton2() {
        InputFragment inputFragment = new FolderAddFragment(selectedId);
        ((FolderListActivity)getContext()).createInputFragment(inputFragment);
    }
}
