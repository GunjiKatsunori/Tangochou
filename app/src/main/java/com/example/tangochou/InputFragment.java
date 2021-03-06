package com.example.tangochou;

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
 * 編集・追加フラグメントの共通部分を実装したクラス
 * @author 郡司克徳
 * @version 1.0.0
 */
public class InputFragment extends Fragment{
    View view;
    String title;
    String actionName;
    String cancellation = "キャンセル";
    Integer selectedId;
    final Fragment fragment = this;

    /**
     * コンストラクター
     * @param title
     * @param actionName
     * @param selectedId
     */
    InputFragment(String title, String actionName, Integer selectedId) {
        this.title = title;
        this.actionName = actionName;
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
        view = inflater.inflate(R.layout.input_fragment, null);

        // 表示の編集
        TextView textView = view.findViewById(R.id.input_text_view);
        textView.setText(title);
        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setText(cancellation);
        Button inputButton = view.findViewById(R.id.input_button);
        inputButton.setText(actionName);
        EditText textBox = view.findViewById(R.id.input_box);

        // input button のクリックリスナー
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DB更新
                updateTable(view);

                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();

                // 画面に変更を反映
                reloadActivity();
            }
        });

        // cancel button のクリックリスナー
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        });

        return view;
    }

    /**
     * テーブルへのデータ追加や更新
     */
    public void updateTable(View view) {}

    /**
     * 一覧画面の再表示
     * データ変更を反映させるため
     */
    public void reloadActivity(){}
}