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
public abstract class SelectionFragment extends Fragment {
    View view;
    Fragment thisFragment = this;

    /**
     * 上のボタンに表示する文字列
     */
    String textButton1;

    /**
     * 下のボタンに表示する文字列
     */
    String textButton2;

    /**
     * textButton1を設定
     * @param textButton1
     */
    public void setTextButton1(String textButton1) {
        this.textButton1 = textButton1;
    }

    /**
     * textButton2を設定
     * @param textButton2
     */
    public void setTextButton2(String textButton2) {
        this.textButton2 = textButton2;
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
        selectionButton1.setText(textButton1);
        Button selectionButton2 = view.findViewById(R.id.selection2);
        selectionButton2.setText(textButton2);

        // selection button 1 のクリックリスナー
        selectionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 上のボタンを押したときの処理
                onClickButton1();

                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(thisFragment)
                        .commit();
            }
        });

        // selection button 2 のクリックリスナー
        selectionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 下のボタンを押したときの処理
                onClickButton2();

                // フラグメント表示終了
                getFragmentManager()
                        .beginTransaction()
                        .remove(thisFragment)
                        .commit();
            }
        });

        return view;
    }

    /**
     * 上のボタンを押したときの処理
     */
    public abstract void onClickButton1();

    /**
     * 下のボタンを押したときの処理
     */
    public abstract void onClickButton2();
}
