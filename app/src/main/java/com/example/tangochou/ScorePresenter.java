package com.example.tangochou;

import android.app.SharedElementCallback;
import android.content.Context;

import com.example.model.HistCardModel;
import com.example.model.HistSeriesModel;

/**
 * 正答率の記録やデータ処理を扱うクラス
 */
public class ScorePresenter {

    private Context context;

    /**
     * 学習セットのID
     */
    Integer seriesId;

    /**
     * 学習セットの学習履歴のID
     */
    Integer histSeriesId;

    /**
     * 正解数
     */
    Integer correctCount = 0;

    /**
     * 不正解数
     */
    Integer incorrectCount = 0;

    /**
     * コンストラクタ
     * @param seriesId 学習セットのID
     */
    public ScorePresenter(Context context, Integer seriesId) {
        this.context = context;
        this.seriesId = seriesId;
    }

    /**
     * カード個別表示開始時の処理
     * @return HistSeriesのID
     */
    public Integer onStartLearning() {
        // HistSeriesを作る
        HistSeriesModel histSeries = new HistSeriesModel(null, getTimestamp(), seriesId, null, null);

        // DB登録
        DBOpenHelper helper = new DBOpenHelper(context);
        histSeries.insert(helper);

        // HistSeriesID取得
        histSeriesId = HistSeriesModel.lastInsertedRow(helper).getId();

        return histSeriesId;
    }

    /**
     * カード個別表示終了時の処理
     */
    public void onStopLearning() {
        // 正解数、不正解数ともに0の場合は履歴削除


        // 正解数と不正解数を記録
        HistSeriesModel hist = new HistSeriesModel(histSeriesId, null, null, correctCount, incorrectCount);
        DBOpenHelper helper = new DBOpenHelper(context);
        hist.update(helper);
    }

    /**
     * カードの正誤を記録する
     *  1. HistCardテーブルに記録を登録する
     *  2. 学習セットの正解・不正解数をカウントする
     * @param card_id 正誤を記録する対象のカード
     * @param correct 正誤
     *                1 : 正解
     *                -1: 不正解
     */
    public void recordAnswer(Integer card_id, Integer correct) {
        // HistCardModelを生成する
        HistCardModel histCard = new HistCardModel(getTimestamp(), card_id, histSeriesId, correct);

        // histCardをDB登録する
        DBOpenHelper helper = new DBOpenHelper(context);
        histCard.insert(helper);

        // correctが1のとき、correctCountがインクリメントされ、incorrectCountはそのまま
        if (correct.equals(1)) {
            correctCount += 1;
        }else if(correct.equals(-1)) {
            incorrectCount += 1;
        }
    }

    public static String getTimestamp() {
        return "";
    }
}
