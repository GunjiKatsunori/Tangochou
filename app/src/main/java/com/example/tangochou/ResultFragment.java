package com.example.tangochou;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.model.HistSeriesModel;
import com.example.model.SeriesModel;

/**
 * 結果表示フラグメントの制御
 * @author 郡司克徳
 * @version 1.0.0
 */
public class ResultFragment extends Fragment implements View.OnClickListener {
    View view;

    /**
     * カードを表示するActivity
     */
    CardActivity activity;

    /**
     * 所属する学習セットのID
     */
    Integer seriesId;

    /**
     * コンストラクタ
     * @param activity
     */
    ResultFragment(CardActivity activity){
        this.activity = activity;
        this.seriesId = activity.seriesId;
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
        view = inflater.inflate(R.layout.result_fragment, null);

        // 学習セット名の表示
        SeriesModel parentSeries = (SeriesModel)(new FolderListPresenter(getContext()).getFile("series", seriesId));
        TextView title = view.findViewById(R.id.series_title);
        title.setText(parentSeries.getName());

        // 終了ボタンのクリック処理
        Button end_button = view.findViewById(R.id.end_button);
        end_button.setOnClickListener(this);

        return view;
    }

    /**
     * 終了ボタンクリック時の処理
     * @param v
     */
    public void onClick(View v) {
        // 個別表示アクティビティを終了
        // 学習セットの一覧に戻る
        activity.finish();
    }

    /**
     * 再表示のときに正答率データを反映させる
     */
    @Override
    public void onResume() {
        super.onResume();

        // 正答率記録機能を終了する
        activity.stopLearning();

        // 記録済みの hist_series レコードを取得
        HistSeriesModel h = new HistSeriesModel(new DBOpenHelper(activity), activity.scorePresenter.histSeriesId);

        TextView mainText1 = view.findViewById(R.id.main_text1);
        mainText1.setText(
                "正解数" + h.getCorrect() + " " + "不正解数" + h.getIncorrect()
        );
    }
}
