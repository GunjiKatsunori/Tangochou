package com.example.tangochou;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment{
    View view;
    String title;
    String actionName;
    Integer selectedId;
    final Fragment fragment = this;

    /**
     * @param title
     * @param actionName
     * @param selectedId
     */
    InputFragment(String title, String actionName, Integer selectedId) {
        this.title = title;
        this.actionName = actionName;
        this.selectedId = selectedId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.input_fragment, null);

        // 表示の編集
        TextView textView = view.findViewById(R.id.input_text_view);
        textView.setText(title);
        Button button = view.findViewById(R.id.input_button);
        button.setText(actionName);
        EditText textBox = view.findViewById(R.id.input_box);

        // ボタンのクリックリスナー
        button.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }

    public void updateTable(View view) {}

    public void reloadActivity(){}
}