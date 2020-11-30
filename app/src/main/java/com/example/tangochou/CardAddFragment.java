package com.example.tangochou;

import android.view.View;
import android.widget.EditText;

import com.example.model.CardModel;
import com.example.model.FolderModel;
import com.example.model.SeriesModel;

public class CardAddFragment extends CardInputFragment {

    /**
     * コンストラクタ
     * @param selectedId 追加先の学習セットのID
     */
    CardAddFragment(Integer selectedId) {
        super("新規カード追加", "追加", selectedId);
    }

    /**
     *
     * @param view
     */
    @Override
    public void updateTable(View view) {
        // 入力されたテキストの取得
        EditText headTextBox = view.findViewById(R.id.head_input_box);
        String headText = headTextBox.getText().toString();
        EditText tailTextBox = view.findViewById(R.id.tail_input_box);
        String tailText = tailTextBox.getText().toString();
        // presenterの設定
        FolderListPresenter presenter = new FolderListPresenter(getContext());
        SeriesModel selectedSeries = (SeriesModel) presenter.getFile("series", selectedId);
        String directory = null;
        if (selectedSeries != null) {
            directory = selectedSeries.getPath();
        }
        // DBに登録
        CardModel card = new CardModel(null, headText, tailText, directory, selectedId);
        presenter.addFile(card);
    }

    /**
     * カード一覧画面の再表示
     * 追加後のデータを反映させるため
     */
    @Override
    public void reloadActivity() {
        ((CardListActivity)getContext()).createView(selectedId);
    }
}
