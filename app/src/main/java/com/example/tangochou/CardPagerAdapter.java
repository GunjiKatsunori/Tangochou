package com.example.tangochou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.model.IFile;

import java.util.ArrayList;

/**
 * カードのスクロール表示を制御するクラス
 * @author 郡司克徳
 * @version 1.0.0
 */
public class CardPagerAdapter extends FragmentStateAdapter {
    /**
     * カードを表示するActivity
     */
    CardActivity activity;

    /**
     * 所属学習セットのID
     */
    Integer seriesId;

    /**
     * 表示対象のカードのリスト
     */
    private ArrayList<IFile> dataset;

    /**
     * コンストラクタ
     * @param activity
     */
    public CardPagerAdapter(CardActivity activity) {
        super(activity);
        this.activity = activity;
        this.seriesId = activity.seriesId;
        this.dataset = activity.dataset;
    }

    /**
     * スクロールしたときに出力するフラグメントを指定する
     * @param position 何枚目にスクロールしているかを表す
     * @return
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // datasetのカードを順に表示する
        if (position < dataset.size()) {
            return new CardFragment(activity, position);
        }else {
            // 全てのカードを表示し終わったら結果の画面を表示する
            return new ResultFragment(activity);
        }
    }

    /**
     * スクロールで表示されるページの総数を定める
     * @return
     */
    @Override
    public int getItemCount() {
        return dataset.size() + 1;
    }
}
