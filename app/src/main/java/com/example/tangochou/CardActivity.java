package com.example.tangochou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.model.IFile;

import java.util.ArrayList;

/**
 * カードを1枚ずつ表示するアクティビティ
 * @author 郡司克徳
 * @version 1.0.0
 */
public class CardActivity extends AppCompatActivity {
    /**
     * 学習セットのid
     */
    public Integer seriesId=0;

    public ArrayList<IFile> dataset;

    /**
     * 正答率記録機能のためのpresenter
     */
    public ScorePresenter scorePresenter;

    ViewPager2 viewPager;

    /**
     * 一覧画面生成時の処理
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        // 画面遷移元の FolderListActivity から intent を受け取る
        Intent intent = getIntent();
        seriesId = intent.getIntExtra("series_id", 0);

        // 正答率の記録開始
        scorePresenter = startLearning();

        // 画面を作成する
        createView(seriesId);
    }

    /**
     * id が seriesId の学習セットを呼び出し、そこに属するカードを一覧表示する
     * @param seriesId
     */
    public void createView(Integer seriesId) {
        this.seriesId = seriesId;

        // 表示するカードのリストを取得
        FolderListPresenter folderListPresenter = new FolderListPresenter(this);
        dataset = folderListPresenter.getFileList("card", seriesId);

        // アクションバーのアイコンなどの表示
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_revert);

        // 「中止」ボタンクリック時のアクション
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 正答率記録機能を終了する
                stopLearning();

                // 本アクティビティを終了
                // 学習セットの一覧に戻る
                finish();
            }
        });

        // viewpager を adapter でコントロールする
        CardPagerAdapter adapter = new CardPagerAdapter(this);
        viewPager = findViewById(R.id.card_pager);
        viewPager.setAdapter(adapter);
    }

    /**
     * 正答率記録機能を開始する
     * @return
     */
    public ScorePresenter startLearning() {
        scorePresenter = new ScorePresenter(this, seriesId);
        scorePresenter.onStartLearning();

        return scorePresenter;
    }

    /**
     * 正答率記録機能を終了する
     */
    public void stopLearning() {
        scorePresenter.onStopLearning();
    }

    /**
     * カードを指定の番号にスワイプ
     * @param v
     */
    public void onSwipe(View v, Integer position) {
        viewPager.setCurrentItem(position);
    }
}