package com.example.tangochou;

import android.view.View;
import android.widget.EditText;

import com.example.model.CardModel;

public class CardEditFragment extends CardInputFragment {

    /**
     * コンストラクタ
     * @param selectedId 編集対象のカードのID
     */
    CardEditFragment(Integer selectedId) {
        super("カード編集", "変更", selectedId);
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

        // DBに登録
        CardModel card = new CardModel(selectedId, headText, tailText, null, null);
        card.update(new DBOpenHelper(getContext()));
    }

    /**
     * カード一覧画面の再表示
     * 追加後のデータを反映させるため
     */
    @Override
    public void reloadActivity() {
        ((CardListActivity)getContext()).createView(((CardListActivity)getContext()).getId());
    }
}
