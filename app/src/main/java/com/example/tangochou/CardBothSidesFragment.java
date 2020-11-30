package com.example.tangochou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.model.CardModel;
import com.example.model.IFile;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;

/**
 * カード両面表示フラグメント
 * @author 郡司克徳
 * @version 1.0.0
 */
public class CardBothSidesFragment extends Fragment {
    /**
     * 学習結果の記録で正解か不正解かを表す定数
     */
    static final int CORRECT = 1;
    static final int INCORRECT = -1;

    /**
     *
     */
    View view;

    /**
     * カードを表示するActivity
     */
    CardActivity activity;

    /**
     * 表示対象のカードのリスト
     */
    private ArrayList<IFile> dataset;

    /**
     * 正答率記録機能のためのpresenter
     */
    private ScorePresenter scorePresenter;

    /**
     * リストの何番目を表示するか
     */
    Integer position;

    /**
     * 表示されるカード
     */
    CardModel card;

    /**
     * コンストラクタ
     * @param activity
     * @param position
     */
    CardBothSidesFragment(CardActivity activity, Integer position){
        this.activity = activity;
        this.dataset = activity.dataset;
        this.scorePresenter = activity.scorePresenter;
        this.position = position;
        this.card = (CardModel)dataset.get(position);
    }

    /**
     * フラグメント生成時の処理
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_both_sides_fragment, null);

        // カードの両面のテキストを表示
        TextView head = view.findViewById(R.id.card_head);
        head.setText(card.getHead());
        TextView tail = view.findViewById(R.id.card_tail);
        tail.setText(card.getTail());

        // 不正解ボタンが押されたときの処理
        TextView incorrectButton = view.findViewById(R.id.incorrect_button);
        incorrectButton.setOnClickListener(new AnswerClickListner(INCORRECT));

        // 正解ボタンが押されたときの処理
        TextView correctButton = view.findViewById(R.id.correct_button);
        correctButton.setOnClickListener(new AnswerClickListner(CORRECT));

        return view;
    }

    /**
     * 正解ボタンおよび不正解ボタンのクリックリスナー
     */
    private class AnswerClickListner implements View.OnClickListener {
        /**
         * 正誤を表す値
         * 1: 正解
         * -1: 不正解
         */
        Integer correct;

        /**
         * コンストラクタ
         * @param correct 正誤を表す値
         */
        AnswerClickListner(Integer correct) {
            this.correct = correct;
        }

        /**
         * 正解ボタンもしくは不正解ボタンをクリックした時の処理の定義
         * @param v
         */
        @Override
        public void onClick(View v) {
            // 正誤の記録
            scorePresenter.recordAnswer(card.getId(), correct);
            // 次のカードに移動
            activity.onSwipe(v, ++position);
        }
    }
}
