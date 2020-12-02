package com.example.tangochou;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * 削除用フラグメント
 * @author 郡司克徳
 * @version 1.0.0
 */
public class DeleteFragment extends Fragment{
    View view;
    String title = "削除します。よろしいですか？";
    String actionName = "OK";
    String cancellation = "キャンセル";

    /**
     * 削除対象のテーブル名
     */
    String table;

    /**
     * 削除対象のID
     */
    Integer selectedId;

    final Fragment fragment = this;

    /**
     * コンストラクター
     * @param selectedId
     */
    DeleteFragment(String table, Integer selectedId) {
        this.table = table;
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
        view = inflater.inflate(R.layout.delete_fragment, null);

        // 表示の編集
        TextView textView = view.findViewById(R.id.delete_text_view);
        textView.setText(title);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setText(cancellation);
        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setText(actionName);

        // input button のクリックリスナー
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IFile削除
                new FolderListPresenter(getContext()).delete(table, selectedId);

                // フラグメント表示終了
                closeFragment();

                // 画面に変更を反映
                ((FolderListActivity)getContext()).createView(((FolderListActivity) getContext()).getId());
            }
        });

        // cancel button のクリックリスナー
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // フラグメント表示終了
                closeFragment();
            }
        });

        return view;
    }

    /**
     * フラグメント終了処理
     */
    public void closeFragment() {
        // フラグメント表示終了
        getFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();

        // フラグを戻す
        ((FolderListActivity)getActivity()).inputFlag = false;
        ((FolderListActivity)getActivity()).selectionFlag = false;
    }
}