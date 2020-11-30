package com.example.tangochou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.model.CardModel;
import com.example.model.IFile;

import java.util.ArrayList;

/**
 * カード個別表示のフラグメントのクラス
 * @author 郡司克徳
 * @version 1.0.0
 */
public class CardFragment extends Fragment implements View.OnClickListener {

    View view;

    /**
     * カードを表示するActivity
     */
    CardActivity activity;

    /**
     * 表示対象のカードのリスト
     */
    ArrayList<IFile> dataset;

    /**
     * リストの何番目を表示するか
     */
    Integer position;

    /**
     * クリックにより両面表示がされているかを表すフラグ
     * 両面表示されている: true
     * されていない     : false
     */
    Boolean clickFlag = false;

    /**
     * コンストラクタ
     * @param activity
     * @param position
     */
    CardFragment(CardActivity activity, Integer position) {
        this.activity = activity;
        this.dataset = activity.dataset;
        this.position = position;
    }

    /**
     * カード表示画面作成時の処理
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_fragment, null);

        // カード表面のテキストを設定
        TextView cardHead = (TextView)view.findViewById(R.id.card_head);
        cardHead.setText(((CardModel)dataset.get(position)).getHead());

        cardHead.setOnClickListener(this);

        return view;
    }

    /**
     * カードのフラグメントをクリックしたときの処理
     * @param v
     */
    @Override
    public void onClick(View v) {
        // 両面表示されていないときだけ両面表示処理を実行する
        if (!clickFlag) {
            // フラグをtrueにする
            clickFlag = true;

            // 両面表示のフラグメントを追加
            CardBothSidesFragment fr = new CardBothSidesFragment(activity, position);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.card_both_side_container, fr)
                    .commit();
        }
    }
}