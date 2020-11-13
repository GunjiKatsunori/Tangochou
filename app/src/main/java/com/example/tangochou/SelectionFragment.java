package com.example.tangochou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

/**
 * 選択フラグメントの画面制御
 * このフラグメントでは新規作成対象がフォルダか学習セットかを選択する
 * @author 郡司克徳
 * @version 1.0.0
 */
public class SelectionFragment extends Fragment {
    View view;
    Fragment thisFragment = this;
    Integer selectedId;

    /**
     * コンストラクタ
     * @param selectedId 新規追加先のフォルダのID
     */
    public SelectionFragment(Integer selectedId) {
        this.selectedId = selectedId;
    }

    /**
     * 画面生成時の処理
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selection_fragment, null);

        // 表示の編集
        Button selectionButton1 = view.findViewById(R.id.selection1);
        selectionButton1.setText("学習セット");
        Button selectionButton2 = view.findViewById(R.id.selection2);
        selectionButton2.setText("フォルダ");

        // selection button 1 のクリックリスナー
        selectionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFragment inputFragment = new SeriesAddFragment(selectedId);
                ((FolderListActivity)getContext()).createInputFragment(inputFragment);

                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(thisFragment)
                        .commit();

                // 画面に変更を反映
                reloadActivity();
            }
        });

        // selection button 2 のクリックリスナー
        selectionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFragment inputFragment = new FolderAddFragment(selectedId);
                ((FolderListActivity)getContext()).createInputFragment(inputFragment);

                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(thisFragment)
                        .commit();

                // 画面に変更を反映
                reloadActivity();
            }
        });

        return view;
    }

    /**
     * 画面の再表示
     * データ更新を反映するため
     */
    public void reloadActivity() {
        ((FolderListActivity)getContext()).createView(selectedId);
    }
}
