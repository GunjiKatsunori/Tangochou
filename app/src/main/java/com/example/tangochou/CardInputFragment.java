package com.example.tangochou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * カード追加・編集フラグメントの共通部分を実装したクラス
 * @author 郡司克徳
 * @version 1.0.0
 */
public class CardInputFragment extends Fragment {
    View view;

    /**
     * フラグメントのタイトル
     */
    String title;

    /**
     * 「追加」「編集」のいずれか
     */
    String actionName;

    /**
     * キャンセルボタンに表示する文字
     */
    String cancellation = "キャンセル";

    /**
     * 追加のときは追加先の学習セットのID
     * 編集の時は編集対象のカードのID
     */
    Integer selectedId;

    /**
     * 無名クラス内で自身を呼び出すために用意する
     */
    final Fragment fragment = this;

    /**
     * コンストラクタ
     * @param title
     * @param actionName
     * @param selectedId
     */
    CardInputFragment(String title, String actionName, Integer selectedId) {
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
        view = inflater.inflate(R.layout.card_input_fragment, null);

        // 表示の編集
        TextView textView = view.findViewById(R.id.card_input_text_view);
        textView.setText(title);
        Button cancelButton = view.findViewById(R.id.card_cancel_button);
        cancelButton.setText(cancellation);
        Button inputButton = view.findViewById(R.id.card_input_button);
        inputButton.setText(actionName);

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
     * テーブルへのデータ追加もしくは更新
     * @param view
     */
    public void updateTable(View view) {
        System.out.println("update table");
    }


    /**
     * 一覧画面の再表示
     * データ変更を反映させるため
     */
    public void reloadActivity(){
        System.out.println("reload activity");}
}
